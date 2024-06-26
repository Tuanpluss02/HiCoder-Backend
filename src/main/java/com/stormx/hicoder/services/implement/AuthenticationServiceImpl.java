package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.controllers.helpers.AuthenticationRequest;
import com.stormx.hicoder.controllers.helpers.AuthenticationResponse;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.UserRepository;
import com.stormx.hicoder.services.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final RedisService redisService;

    @Value("${auth.admin-key}")
    private String ADMIN_KEY;
    @Value("${token.reset-password.expiration}")
    private Long RESETPWD_TOKEN_EXPIRATION;
    @Value("${domain.server}")
    private String SERVER_ADDRESS;
    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {
        String email = request.getEmail();
        if (repository.existsByEmail(email)) throw new BadRequestException("Email already exists");
        String role = request.getRole();
        Role userRole;
        if (role == null || role.isEmpty() || role.equals("USER")) userRole = Role.USER;
        else if (role.equals("ADMIN")) {
            if (request.getAdminKey() == null || request.getAdminKey().isEmpty() || !request.getAdminKey().equals(ADMIN_KEY))
                throw new BadRequestException("Invalid admin key");
            userRole = Role.ADMIN;
        } else userRole = Role.USER;
        String username = request.getEmail().split("@")[0];
        if (repository.existsByUsername(username)) {
            String salt = String.valueOf(System.currentTimeMillis());
            username = username + salt;
        }
        User user = User.builder().username(username).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(userRole).build();
        repository.save(user);
        Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
        return getAuthenticationResponse(user, authorities);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("Email or password is incorrect"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
        return getAuthenticationResponse(user, authorities);
    }

    @Override
    public AuthenticationResponse getNewAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Refresh token is missing");
        }
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        if (!tokenService.isValidToken(refreshToken)) throw new BadRequestException("Refresh token is invalid");
        String userId = (String) redisService.getObjByToken(refreshToken);
        User user = userService.getUserById(userId);
        Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
        String jwtToken = tokenService.generateToken(UserDTO.fromUser(user), authorities);
        return AuthenticationResponse.builder().userId(user.getId()).username(user.getUsername()).role(user.getRole().toString()).accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    private AuthenticationResponse getAuthenticationResponse(User user, Collection<SimpleGrantedAuthority> authorities) {
        UserDTO userDTO = UserDTO.fromUser(user);
        String jwtToken = tokenService.generateToken(userDTO, authorities);
        String refreshToken = tokenService.generateRefreshToken(userDTO, authorities);
        return AuthenticationResponse.builder().userId(user.getId()).username(user.getUsername()).role(user.getRole().toString()).accessToken(jwtToken).refreshToken(refreshToken).build();
    }


    @Override
    public void sendEmailResetPassword(String email) throws MessagingException {
        boolean isValid = email.matches("^(.+)@(.+)$");
        if (!isValid) throw new BadRequestException("Invalid email");
        repository.findByEmail(email).orElseThrow(() -> new BadRequestException("This email is not registered"));
        Context context = new Context();
        String token = tokenService.generateResetPasswordToken();
        String resetLink = SERVER_ADDRESS + "/ui/reset-password?token=" + token;
        context.setVariable("resetPasswordLink", resetLink);
        redisService.saveToken(token, RESETPWD_TOKEN_EXPIRATION, email);
        emailService.sendEmailWithHtml(email, "HiCoder | Reset password", "email-template", context);
    }

    @Override
    public void verifyAndChangePwd(String token, String newPassword) {
        String email = redisService.getEmailByToken(token);
        if (email == null) throw new BadRequestException("Token is invalid or expired");
        User user = repository.findByEmail(email).orElseThrow(() -> new BadRequestException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        redisService.deleteToken(token);
        repository.save(user);
    }

    @Override
    public void logout(HttpServletRequest request) {
        User user = userService.getCurrentUser();
        redisService.deleteToken(user.getId());
        tokenService.disableDeviceToken(user, null);
        tokenService.blacklistToken(user, request.getHeader(AUTHORIZATION).substring("Bearer ".length()));
    }
}

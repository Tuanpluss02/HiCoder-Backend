package com.stormx.hicoder.services;

import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.dto.AuthenticationResponse;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Value("${auth.admin-key}")
    private String adminKey;

    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {
        String email = request.getEmail();
        if (repository.existsByEmail(email)) throw new BadRequestException("Email already exists");
        String role = request.getRole();
        Role userRole;
        if (role == null || role.isEmpty() || role.equals("USER")) userRole = Role.USER;
        else if (role.equals("ADMIN")) {
            if (request.getAdminKey() == null || request.getAdminKey().isEmpty() || !request.getAdminKey().equals(adminKey))
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
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
        return getAuthenticationResponse(user, authorities);
    }

    private AuthenticationResponse getAuthenticationResponse(User user, Collection<SimpleGrantedAuthority> authorities) {
        UserDTO userDTO = new UserDTO(user);
        String jwtToken = tokenService.generateToken(userDTO, authorities);
        String refreshToken = tokenService.generateRefreshToken(userDTO, authorities);
        return AuthenticationResponse.builder().userId(user.getId()).username(user.getUsername()).role(user.getRole().toString()).accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public AuthenticationResponse getNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Refresh token is missing");
        }
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        UserDTO user = tokenService.isRefreshTokenValid(refreshToken);
        Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
        String jwtToken = tokenService.generateToken(user, authorities);
        return AuthenticationResponse.builder().userId(user.getId()).username(user.getUsername()).role(user.getRole().toString()).accessToken(jwtToken).refreshToken(refreshToken).build();
    }

}

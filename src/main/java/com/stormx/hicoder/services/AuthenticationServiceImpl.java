package com.stormx.hicoder.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.common.TokenType;
import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.dto.AuthenticationResponse;
import com.stormx.hicoder.entities.Token;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.repositories.TokenRepository;
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

import java.io.IOException;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
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
        revokeAllUserTokens(user);
        return getAuthenticationResponse(user, authorities);
    }

    private AuthenticationResponse getAuthenticationResponse(User user, Collection<SimpleGrantedAuthority> authorities) {
        String jwtToken = jwtService.generateToken(user, authorities);
        String refreshToken = jwtService.generateRefreshToken(user, authorities);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder().userId(user.getId()).username(user.getUsername()).role(user.getRole().toString()).accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return;
        }
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        String username = jwtService.getUsernameFromToken(refreshToken);
        User user = repository.findByUsername(username).orElseThrow(() -> new BadRequestException("User not found"));
        boolean isRefreshTokenValid = jwtService.isRefreshTokenValid(refreshToken, user);
        if (isRefreshTokenValid) {
            revokeAllUserTokens(user);
            Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
            String accessToken = jwtService.generateToken(user, authorities);
            String newRefreshToken = jwtService.generateRefreshToken(user, authorities);
            saveUserToken(user, accessToken);
            var authResponse = AuthenticationResponse.builder().accessToken(accessToken).userId(user.getId()).username(user.getUsername()).refreshToken(newRefreshToken).build();
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}

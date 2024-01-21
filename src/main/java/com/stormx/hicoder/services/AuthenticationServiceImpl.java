package com.stormx.hicoder.services;

import com.stormx.hicoder.common.Role;
import com.stormx.hicoder.common.TokenType;
import com.stormx.hicoder.dto.AuthenticationRequest;
import com.stormx.hicoder.dto.AuthenticationResponse;
import com.stormx.hicoder.entities.Token;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.repositories.TokenRepository;
import com.stormx.hicoder.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(AuthenticationRequest request) {
        String role = request.getRole();
        Role userRole;
        if (role == null || role.isEmpty() || role.equals("USER")) userRole = Role.USER;
        else if (role.equals("ADMIN")) userRole = Role.ADMIN;
        else userRole = Role.USER;
        User user = User.builder().email(request.getEmail()).password(request.getPassword()).role(userRole).build();
        repository.save(user);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
        String jwtToken = jwtService.generateToken(user, authorities);
        String refreshToken = jwtService.generateRefreshToken(user, authorities);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Collection<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
        revokeAllUserTokens(user);
        String jwtToken = jwtService.generateToken(user, authorities);
        String refreshToken = jwtService.generateRefreshToken(user, authorities);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);

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

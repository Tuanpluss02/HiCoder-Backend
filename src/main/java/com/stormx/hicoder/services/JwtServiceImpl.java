package com.stormx.hicoder.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stormx.hicoder.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    @Override
    public String generateToken(User user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration)).withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).sign(algorithm);

    }

    @Override
    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration)).sign(algorithm);
    }

    @Override
    public String getUsernameFromToken(String substring) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.require(algorithm).build().verify(substring).getSubject();
    }

    @Override
    public boolean isRefreshTokenValid(String refreshToken, User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        String username = JWT.require(algorithm).build().verify(refreshToken).getSubject();
        return username.equals(user.getUsername());
    }
}


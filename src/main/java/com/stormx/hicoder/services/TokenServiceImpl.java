package com.stormx.hicoder.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    @Autowired
    private  RedisService redisService;
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
    public void saveRefreshToken(String token, User userDetails) {
        redisService.saveRefreshToken(token, refreshExpiration, userDetails);
    }


    @Override
    public User isRefreshTokenValid(String refreshToken) {
        User user = redisService.getUserFromRefreshToken(refreshToken);
        if (user == null) throw new BadRequestException("Refresh token is not valid or expired");
        return user;
    }
}


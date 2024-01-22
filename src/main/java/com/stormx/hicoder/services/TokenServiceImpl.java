package com.stormx.hicoder.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.exceptions.BadRequestException;
import com.stormx.hicoder.interfaces.RedisService;
import com.stormx.hicoder.interfaces.TokenService;
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
    private RedisService redisService;
    @Override
    public String generateToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration)).withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).sign(algorithm);

    }

    @Override
    public String generateRefreshToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        String refreshToken =  JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration)).sign(algorithm);
        saveRefreshToken(refreshToken, user);
        return refreshToken;
    }

    @Override
    public void saveRefreshToken(String token, UserDTO userDetails) {
        redisService.saveToken(token, refreshExpiration, userDetails);
    }


    @Override
    public UserDTO isRefreshTokenValid(String refreshToken) {
        UserDTO user = redisService.getUserFromRefreshToken(refreshToken);
        if (user == null) throw new BadRequestException("Refresh token is not valid or expired");
        return user;
    }
}


package com.stormx.hicoder.services.implement;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.stormx.hicoder.common.TokenType;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.Token;
import com.stormx.hicoder.entities.User;
import com.stormx.hicoder.repositories.TokenRepository;
import com.stormx.hicoder.services.RedisService;
import com.stormx.hicoder.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    @Value("${jwt.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private Long JWT_EXPIRATION;
    @Value("${jwt.refresh-token.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;
    private final  RedisService redisService;
    private final TokenRepository tokenRepository;
    @Override
    public String generateToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION)).withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).sign(algorithm);

    }

    @Override
    public String generateRefreshToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        String refreshToken = JWT.create().withSubject(user.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION)).sign(algorithm);
        redisService.saveToken(refreshToken, REFRESH_TOKEN_EXPIRATION, user.getId());
        return refreshToken;
    }

    @Override
    public boolean isValidToken(String refreshToken) {
        return redisService.getObjByToken(refreshToken) != null;
    }

    @Override
    public void blacklistToken(User user, String token) {
        tokenRepository.save(new Token(user, token, TokenType.USER_TOKEN));
    }

    @Override
    public boolean isValidAccessToken(User user, String accessToken) {
        return tokenRepository.existsByUserAndTokenAndType(user, accessToken, TokenType.USER_TOKEN);
    }


    @Override
    public String generateResetPasswordToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void saveDeviceToken(User user, String deviceToken) {
        tokenRepository.save(new Token(user, deviceToken, TokenType.DEVICE_TOKEN));
    }

    @Override
    public List<Token> getAllDeviceTokens(User user) {
        return tokenRepository.findAllByUserAndType(user, TokenType.DEVICE_TOKEN);
    }

    @Override
    public void disableDeviceToken(User user, String deviceToken) {
        List<Token> tokens = tokenRepository.findAllByUserAndType(user, TokenType.DEVICE_TOKEN);
        tokens.forEach(token -> {
            if (token.getToken().equals(deviceToken)) {
                token.setActive(false);
                tokenRepository.save(token);
            }
        });
    }
}


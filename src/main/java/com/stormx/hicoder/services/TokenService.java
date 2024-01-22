package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface TokenService {
    String generateToken(User user, Collection<SimpleGrantedAuthority> authorities);

    String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities);

    void saveRefreshToken(String token, User userDetails);

    User isRefreshTokenValid(String refreshToken);
}

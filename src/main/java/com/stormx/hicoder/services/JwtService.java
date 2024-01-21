package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface JwtService {
    String generateToken(User user, Collection<SimpleGrantedAuthority> authorities);

    String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities);

    String getUsernameFromToken(String substring);

    boolean isRefreshTokenValid(String refreshToken, User user);
}

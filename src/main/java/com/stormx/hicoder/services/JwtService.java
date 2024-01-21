package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface JwtService {
    public String generateToken(User user, Collection<SimpleGrantedAuthority> authorities);
    public String generateRefreshToken(User user, Collection<SimpleGrantedAuthority> authorities);

    public String getUsernameFromToken(String substring);

   public boolean isRefreshTokenValid(String refreshToken, User user);
}

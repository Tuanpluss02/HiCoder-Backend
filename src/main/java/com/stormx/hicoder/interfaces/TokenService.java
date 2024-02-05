package com.stormx.hicoder.interfaces;

import com.stormx.hicoder.dto.UserDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public interface TokenService {
    String generateToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities);

    String generateRefreshToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities);

    void saveRefreshToken(String token, UserDTO userDetails);

    UserDTO isRefreshTokenValid(String refreshToken);

    String generateResetPasswordToken();
}

package com.stormx.hicoder.services;

import com.stormx.hicoder.common.TokenType;
import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.Token;
import com.stormx.hicoder.entities.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface TokenService {
    String generateToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities);

    String generateRefreshToken(UserDTO user, Collection<SimpleGrantedAuthority> authorities);

    void saveRefreshToken(String token, UserDTO userDetails);

    UserDTO isRefreshTokenValid(String refreshToken);

    String generateResetPasswordToken();

    void saveDeviceToken(User user, String deviceToken);

    List<Token> getAllDeviceTokens(User user);

    void disableDeviceToken(User user, String deviceToken);

}

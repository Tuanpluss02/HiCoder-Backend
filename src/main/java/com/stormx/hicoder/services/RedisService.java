package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.UserDTO;


public interface RedisService {

    void saveToken(String token, Long expireTime, Object obj);

    UserDTO getUserFromRefreshToken(String token);

    void deleteRefreshToken(String token);

    String getEmailByToken(String token);
}
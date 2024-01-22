package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


public interface RedisService {

    void saveRefreshToken(String token, Long expireTime, User userDetails);

    User getUserFromRefreshToken(String token);

    void deleteRefreshToken(String token);
}
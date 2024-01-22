package com.stormx.hicoder.interfaces;

import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


public interface RedisService {

    void saveToken(String token, Long expireTime, UserDTO userDetails);

    UserDTO getUserFromRefreshToken(String token);

    void deleteRefreshToken(String token);
}
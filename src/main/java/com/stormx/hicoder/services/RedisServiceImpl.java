package com.stormx.hicoder.services;

import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService{

    private final RedisTemplate redisTemplate;
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveToken(String token, Long expireTime, UserDTO userDetails) {
        redisTemplate.opsForValue().set(token, userDetails, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public UserDTO getUserFromRefreshToken(String token) {
        return (UserDTO) redisTemplate.opsForValue().get(token);
    }

    @Override
    public void deleteRefreshToken(String token) {
        redisTemplate.delete(token);
    }
}

package com.stormx.hicoder.services;

import com.stormx.hicoder.entities.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService{
    private final RedisTemplate<String, Object> redisTemplate;
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveRefreshToken(String token, Long expireTime, User userDetails) {
        redisTemplate.opsForValue().set(token, userDetails, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public User getUserFromRefreshToken(String token) {
        return (User) redisTemplate.opsForValue().get(token);
    }

    @Override
    public void deleteRefreshToken(String token) {
        redisTemplate.delete(token);
    }
}

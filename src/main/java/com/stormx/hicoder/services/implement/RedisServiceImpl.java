package com.stormx.hicoder.services.implement;

import com.stormx.hicoder.dto.UserDTO;
import com.stormx.hicoder.services.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate redisTemplate;
    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveToken(String token, Long expireTime, Object obj) {
        redisTemplate.opsForValue().set(token, obj, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public UserDTO getUserFromRefreshToken(String token) {
        return (UserDTO) redisTemplate.opsForValue().get(token);
    }

    @Override
    public void deleteRefreshToken(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public String getEmailByToken(String token) {
        return (String) redisTemplate.opsForValue().get(token);
    }
}

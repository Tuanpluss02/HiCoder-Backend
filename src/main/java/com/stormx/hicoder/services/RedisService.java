package com.stormx.hicoder.services;

public interface RedisService {

    void saveToken(String token, Long expireTime, Object obj);

    Object getObjByToken(String token);

    void deleteToken(String token);

    String getEmailByToken(String token);
}
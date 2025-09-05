package com.example.TelConnect.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BlacklistJwt {

    private final RedisTemplate<String, String> redisTemplate;

    public BlacklistJwt(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token, long expiry){
        redisTemplate.opsForValue().set(token, "blacklisted", expiry, TimeUnit.MILLISECONDS);
    }

    public Boolean isBlacklisted(String token){
        return redisTemplate.hasKey(token);
    }
}

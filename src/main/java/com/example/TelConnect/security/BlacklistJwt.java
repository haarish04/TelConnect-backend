package com.example.TelConnect.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BlacklistJwt {

    private final RedisTemplate<String, String> redisTemplate;

    public BlacklistJwt(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token){
        redisTemplate.opsForValue().set(token, "blacklisted");
    }

    public Boolean isBlacklisted(String token){
        return redisTemplate.hasKey(token);
    }
}

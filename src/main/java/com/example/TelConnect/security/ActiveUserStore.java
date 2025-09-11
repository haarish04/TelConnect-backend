package com.example.TelConnect.security;

import com.example.TelConnect.DTO.UserSessionInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class ActiveUserStore {

    private final RedisTemplate<String, UserSessionInfo> redisTemplate;

    public ActiveUserStore(@Qualifier("userSessionRedisTemplate") RedisTemplate<String, UserSessionInfo> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addUser(String username, UserSessionInfo info) {
        redisTemplate.opsForValue().set(username, info);
    }

    public void removeUser(String username){
        redisTemplate.delete(username);
    }

    public HashMap<String,UserSessionInfo> getActiveUsers() {
        Set<String> keys = redisTemplate.keys("user:*");
        HashMap<String, UserSessionInfo> activeUserList = new HashMap<>();
        for (String key : keys) {
            UserSessionInfo value = redisTemplate.opsForValue().get(key);
            activeUserList.put(key, value);
        }
        return activeUserList;

    }
}

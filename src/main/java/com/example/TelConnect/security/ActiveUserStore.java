package com.example.TelConnect.security;


import com.example.TelConnect.DTO.UserSessionInfo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveUserStore {
    private final ConcurrentHashMap<String, UserSessionInfo> activeUsers = new ConcurrentHashMap<>();

    public void addUser(String username, UserSessionInfo info) {
        activeUsers.put(username, info);
    }

    public void removeUser(String username) {
        activeUsers.remove(username);
    }

    public Map<String, UserSessionInfo> getActiveUsers() {
        return activeUsers;
    }
}

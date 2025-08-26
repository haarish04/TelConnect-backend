package com.example.TelConnect.monitoring;

import com.example.TelConnect.DTO.UserSessionInfo;
import com.example.TelConnect.security.ActiveUserStore;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "activeUsers")
public class ActiveUsersEndpoint {

    private final ActiveUserStore activeUserStore;

    public ActiveUsersEndpoint(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

    @ReadOperation
    public Map<String, UserSessionInfo> activeUsers() {
        return activeUserStore.getActiveUsers();
    }
}

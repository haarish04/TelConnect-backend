package com.example.TelConnect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    private String secret;
    private long expirationMilliseconds;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpirationMilliseconds() {
        return expirationMilliseconds;
    }

    public void setExpirationMilliseconds(long expirationMilliseconds) {
        this.expirationMilliseconds = expirationMilliseconds;
    }
}

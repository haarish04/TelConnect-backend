package com.example.TelConnect.DTO;

import java.util.Collection;
import java.util.Date;

public class UserSessionInfo{

    private String username;
    private Date loginTime;
    private Date expiryTime;

    public UserSessionInfo(String username, Date loginTime, Date expiryTime) {
        this.username = username;
        this.loginTime = loginTime;
        this.expiryTime = expiryTime;
    }

    public UserSessionInfo(){}

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

}

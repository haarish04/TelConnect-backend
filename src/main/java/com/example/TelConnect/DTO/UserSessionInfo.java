package com.example.TelConnect.DTO;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class UserSessionInfo {

    private String username;
    private Date loginTime;
    private Date expiryTime;
    private Collection<? extends GrantedAuthority> roles;

    public UserSessionInfo(String username, Date loginTime, Date expiryTime, Collection<? extends GrantedAuthority> roles) {
        this.username = username;
        this.loginTime = loginTime;
        this.expiryTime = expiryTime;
        this.roles = roles;
    }

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

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }
}

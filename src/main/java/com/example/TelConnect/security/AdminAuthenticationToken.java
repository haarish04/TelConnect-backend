package com.example.TelConnect.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AdminAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public AdminAuthenticationToken(Object principal) {
        super(null); // Pass authorities as null; you can set them later if needed.
        this.principal = principal;
        setAuthenticated(true); // By default, set the token as authenticated
    }

    public AdminAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities); // You can pass the authorities when you need roles or permissions
        this.principal = principal;
        setAuthenticated(true); // Mark as authenticated
    }

    @Override
    public Object getCredentials() {
        return null; // Admin doesn't have credentials in this token
    }

    @Override
    public Object getPrincipal() {
        return principal; // Return the authenticated admin (Customer object)
    }
}

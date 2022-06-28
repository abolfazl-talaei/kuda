package com.kuda.app.security.impl;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticatedUser implements Authentication {

    private String username;

    public AuthenticatedUser(String username) {
        super();
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return null;
    }

    @Override
    public Object getCredentials() {

        return null;
    }

    @Override
    public Object getDetails() {

        return null;
    }

    @Override
    public Object getPrincipal() {
        return new UserPrincipal() {

            @Override
            public String getName() {
                return username;
            }

        };
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        // immutable
    }

}

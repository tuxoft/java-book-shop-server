package ru.tuxoft.secure;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TokenAuthentication implements Authentication {

    private Collection<? extends GrantedAuthority> authorities;

    String userName;

    boolean authenticated = true;

    Principal principal;

    public TokenAuthentication(Collection<? extends GrantedAuthority> authorities, boolean isAuthenticated, String userName, String firstName, String lastName, String email) {
        this.authorities = authorities;
        this.userName = userName;
        this.principal = new Principal(userName, firstName, lastName, email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return this.userName;
    }

    public String toString() {
        return "TokenAuthentication{authorities=" + this.authorities + ", userName=\'" + this.userName + '\'' + '}';
    }
}

package com.payday.accountms.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;


    public CustomUserDetails(String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement logic as required
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement logic as required
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement logic as required
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement logic as required
    }
}

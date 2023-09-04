package com.payday.stockms.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                List<String> roles = new ArrayList<>();
                roles.add("ROLES");
                CustomUserDetails userDetails = new CustomUserDetails(username, "", roles);
                return userDetails;
            }
        };
    }
}

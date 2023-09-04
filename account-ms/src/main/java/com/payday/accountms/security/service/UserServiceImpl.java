package com.payday.accountms.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            List<String> roles = new ArrayList<>();
            roles.add("ROLES");
            return new CustomUserDetails(username, "", roles);
        };
    }
}

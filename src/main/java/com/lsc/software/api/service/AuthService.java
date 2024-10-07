package com.lsc.software.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    @Autowired
    public AuthService(@Lazy UserService userService) {
        this.userService = userService;
    }

    public UserDetails loadUser(String username) {
        return userService.loadUserByUsername(username);
    }
}

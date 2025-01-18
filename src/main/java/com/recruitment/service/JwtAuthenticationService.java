package com.recruitment.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationService {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UserDetails loadUser( String username) {
        return userDetailsService.loadUserByUsername(username);
    }
}

package com.recruitment.service;

import com.recruitment.model.User;
import com.recruitment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user from the database
        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                                                                 .username(user.getEmail()) // Use email as the username
                                                                 .password(user.getPassword())
                                                                 .roles(user.getRole().getName()) // Use role names for Spring Security
                                                                 .disabled(!user.isEnabled())
                                                                 .build();
    }

}

package com.recruitment.service;

import com.recruitment.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;

    public boolean validateToken(String token, String username) {
        return jwtUtil.validateToken(token, username);
    }

    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }
}

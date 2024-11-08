package com.example;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String generateToken(User user) {
    
        return "token";
    }
}

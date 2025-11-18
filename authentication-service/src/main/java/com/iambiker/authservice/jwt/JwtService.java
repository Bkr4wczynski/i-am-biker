package com.iambiker.authservice.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtService {
    private final AuthJwtUtil authJwtUtil;
    private final PasswordEncoder passwordEncoder;


    public boolean matchPasswords(String given, String expected) {
        return passwordEncoder.matches(given, expected);
    }

    public String generateToken(String username, int id) {
        return authJwtUtil.generateToken(username, id);
    }

    public boolean validateToken(String token) {
        return authJwtUtil.validateToken(token);
    }

    public Integer getUserId(String token) {
        return authJwtUtil.getUserIdFromToken(token);
    }

}

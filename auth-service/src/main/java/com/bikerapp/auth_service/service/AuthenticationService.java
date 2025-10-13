package com.bikerapp.auth_service.service;

import com.bikerapp.auth_service.entity.User;
import com.bikerapp.auth_service.repository.UserRepository;
import com.bikerapp.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered";
    }

    public boolean matchPasswords(String given, String expected) {
        return passwordEncoder.matches(given, expected);
    }

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

}

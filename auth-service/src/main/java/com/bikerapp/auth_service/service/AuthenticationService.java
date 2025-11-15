package com.bikerapp.auth_service.service;

import com.bikerapp.auth_service.dto.UserDetailsDTO;
import com.bikerapp.auth_service.entity.User;
import com.bikerapp.auth_service.entity.UserDetails;
import com.bikerapp.auth_service.repository.UserDetailsRepository;
import com.bikerapp.auth_service.repository.UserRepository;
import com.bikerapp.auth_service.security.JwtUtil;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered";
    }

    public UserDetailsDTO getUserDetailsFromToken(String token) {
        Integer userId = jwtUtil.getUserIdFromToken(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException("Could not find user details for user");
        UserDetails userDetails =user.get().getUser_details();
        return new UserDetailsDTO(userDetails.getUser_id(), userDetails.getRegistry_date(), userDetails.getBirthday());
    }

    public boolean matchPasswords(String given, String expected) {
        return passwordEncoder.matches(given, expected);
    }

    public String generateToken(String username, int id) {
        return jwtUtil.generateToken(username, id);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }


}

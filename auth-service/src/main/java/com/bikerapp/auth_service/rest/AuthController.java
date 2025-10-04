package com.bikerapp.auth_service.rest;

import com.bikerapp.auth_service.dto.UserDTO;
import com.bikerapp.auth_service.entity.User;
import com.bikerapp.auth_service.model.AuthRequest;
import com.bikerapp.auth_service.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @GetMapping("/validate-token")
    public String validateToken(@RequestParam String token) {
        if (authenticationService.validateToken(token))
            return "Token is valid";
        return "Token is not valid!";
    }

    @GetMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        String username = authRequest.getUsername();
        return authenticationService.generateToken(username);
    }

    @PostMapping("/register-user")
    public String registerUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return authenticationService.saveUser(user);
    }
}

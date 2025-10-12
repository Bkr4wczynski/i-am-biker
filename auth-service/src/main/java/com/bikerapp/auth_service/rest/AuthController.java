package com.bikerapp.auth_service.rest;

import com.bikerapp.auth_service.dto.UserDTO;
import com.bikerapp.auth_service.entity.User;
import com.bikerapp.auth_service.model.AuthRequest;
import com.bikerapp.auth_service.repository.UserRepository;
import com.bikerapp.auth_service.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @GetMapping("/validate-token")
    public String validateToken(@RequestParam String token) {
        System.out.println(token);
        if (authenticationService.validateToken(token))
            return "Token is valid";
        return "Token is not valid!";
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest authRequest) throws RuntimeException {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException("Bad credentials!");
        }
        if (!Objects.equals(user.get().getPassword(), password)) {
            throw new RuntimeException("Bad credentials!");
        }

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

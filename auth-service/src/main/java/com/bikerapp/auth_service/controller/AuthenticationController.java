package com.bikerapp.auth_service.controller;

import com.bikerapp.auth_service.model.AuthRequest;
import com.bikerapp.auth_service.entity.User;
import com.bikerapp.auth_service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService service;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(AuthenticationService service, AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody User user) {
        return service.saveUser(user);
    }

    @GetMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated())
            return service.generateToken(authRequest.getUsername());
        throw new RuntimeException("Cannot access resource!");
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        if (!service.validateToken(token))
            return "Token is not valid";
        return "Token is valid";
    }
}

package com.bikerapp.auth_service.rest;

import com.bikerapp.auth_service.dto.UserDTO;
import com.bikerapp.auth_service.dto.UserDetailsDTO;
import com.bikerapp.auth_service.entity.User;
import com.bikerapp.auth_service.entity.UserDetails;
import com.bikerapp.auth_service.model.AuthRequest;
import com.bikerapp.auth_service.repository.UserRepository;
import com.bikerapp.auth_service.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @GetMapping("/validate-token")
    public String validateToken(@RequestParam String token) {
        if (authenticationService.validateToken(token))
            return "Token is valid";
        return "Token is not valid!";
    }

    @GetMapping("/user-details")
    public UserDetailsDTO getUserDetails(@RequestParam String token) {
        if (!authenticationService.validateToken(token))
            return null;
        return authenticationService.getUserDetailsFromToken(token);
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest authRequest) throws RuntimeException {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !authenticationService.matchPasswords(password, user.get().getPassword())) {
            log.info("Failed to login reason: Bad Credentials!");
            throw new RuntimeException("Bad credentials!");
        }

        log.info("Token successfully generated!");
        return authenticationService.generateToken(username, user.get().getId());
    }

    @PostMapping("/register-user")
    public String registerUser(@RequestBody UserDTO userDTO) { // test for wrong input like duplicate username/email
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        UserDetails userDetails = new UserDetails();
        userDetails.setUser(user);
        userDetails.setRegistry_date(LocalDate.now());
        userDetails.setBirthday(userDTO.getUserDetailsDTO().getBirthday());
        user.setUser_details(userDetails);
        log.info("User successfully registered!");
        return authenticationService.saveUser(user);
    }
}

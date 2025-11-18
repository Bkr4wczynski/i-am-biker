package com.iambiker.authservice.rest;

import com.iambiker.authservice.jwt.JwtService;
import com.iambiker.authservice.userdata.dto.UserDTO;
import com.iambiker.authservice.userdata.dto.UserDetailsDTO;
import com.iambiker.authservice.userdata.entity.User;
import com.iambiker.authservice.userdata.entity.UserDetails;
import com.iambiker.authservice.model.AuthRequest;
import com.iambiker.authservice.userdata.repository.UserRepository;
import com.iambiker.authservice.userdata.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class AuthenticationRestController {
    private final UserAuthenticationService userAuthenticationService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping("/validate-token")
    public String validateToken(@RequestParam String token) {
        if (jwtService.validateToken(token))
            return "Token is valid";
        return "Token is not valid!";
    }

    @GetMapping("/user-details")
    public UserDetailsDTO getUserDetails(@RequestParam String token) {
        if (!jwtService.validateToken(token))
            return null;
        return userAuthenticationService.getUserDetailsFromToken(token);
    }

    @PostMapping("/generate-token")
    public String generateToken(@RequestBody AuthRequest authRequest) throws RuntimeException {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !jwtService.matchPasswords(password, user.get().getPassword())) {
            log.info("Failed to login reason: Bad Credentials!");
            throw new RuntimeException("Bad credentials!");
        }

        log.info("Token successfully generated!");
        return jwtService.generateToken(username, user.get().getId());
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
        return userAuthenticationService.saveUser(user);
    }
}

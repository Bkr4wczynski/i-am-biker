package com.iambiker.authservice.rest;

import com.iambiker.authservice.jwt.JwtService;
import com.iambiker.authservice.userdata.dto.UserDTO;
import com.iambiker.authservice.userdata.dto.UserDetailsDTO;
import com.iambiker.authservice.userdata.entity.User;
import com.iambiker.authservice.model.AuthRequest;
import com.iambiker.authservice.userdata.repository.UserRepository;
import com.iambiker.authservice.userdata.service.UserAuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/public")
@AllArgsConstructor
public class AuthenticationRestController {
    private final UserAuthenticationService userAuthenticationService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        if (jwtService.validateToken(token))
            return ResponseEntity.ok(Boolean.TRUE);
        return ResponseEntity.ok(Boolean.FALSE);
    }

    @GetMapping("/user-details")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@RequestParam String token) {
        if (!jwtService.validateToken(token))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userAuthenticationService.getUserDetailsFromToken(token));
    }

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) throws RuntimeException {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !jwtService.matchPasswords(password, user.get().getPassword())) {
            log.info("Failed to login reason: Bad Credentials!");
            throw new RuntimeException("Bad credentials!");
        }

        log.info("Token successfully generated!");
        return ResponseEntity.ok(jwtService.generateToken(username, user.get().getId()));
    }

    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        User user = userDTO.convertToUser();
        log.info("User successfully registered!");
        return ResponseEntity.ok(userAuthenticationService.saveUser(user));
    }
}

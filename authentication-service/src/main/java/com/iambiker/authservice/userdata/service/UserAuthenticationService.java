package com.iambiker.authservice.userdata.service;

import com.iambiker.authservice.jwt.JwtService;
import com.iambiker.authservice.userdata.dto.UserDetailsDTO;
import com.iambiker.authservice.userdata.entity.User;
import com.iambiker.authservice.userdata.entity.UserDetails;
import com.iambiker.authservice.userdata.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User successfully registered!");
        return "User registered";
    }

    public UserDetailsDTO getUserDetailsFromToken(String token) {
        Integer userId = jwtService.getUserId(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Could not find user details for user");
        }
        UserDetails userDetails = user.get().getUser_details();
        return new UserDetailsDTO(userDetails.getUser_id(), userDetails.getUser().getUsername(), userDetails.getRegistry_date(), userDetails.getBirthday());
    }

    @Transactional
    public User updateUserDetails(UserDetailsDTO userDetailsDTO) {
        log.info("Updating user details");

        UserDetails userDetails = new UserDetails();
        userDetails.setUser_id(userDetailsDTO.getUser_id());
        userDetails.setBirthday(userDetailsDTO.getBirthday());
        userDetails.setRegistry_date(userDetailsDTO.getRegistry_date());

        User user = userRepository.findById(userDetails.getUser_id())
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setUsername(userDetailsDTO.getUsername());

        user.setUser_details(userDetails);

        return userRepository.save(user);
    }



}

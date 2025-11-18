package com.iambiker.authservice.userdata.service;

import com.iambiker.authservice.jwt.JwtService;
import com.iambiker.authservice.userdata.dto.UserDetailsDTO;
import com.iambiker.authservice.userdata.entity.User;
import com.iambiker.authservice.userdata.entity.UserDetails;
import com.iambiker.authservice.userdata.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered";
    }

    public UserDetailsDTO getUserDetailsFromToken(String token) {
        Integer userId = jwtService.getUserId(token);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty())
            throw new NotFoundException("Could not find user details for user");
        UserDetails userDetails =user.get().getUser_details();
        return new UserDetailsDTO(userDetails.getUser_id(), userDetails.getRegistry_date(), userDetails.getBirthday());
    }



}

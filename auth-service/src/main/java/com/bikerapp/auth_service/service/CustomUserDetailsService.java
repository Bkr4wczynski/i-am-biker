package com.bikerapp.auth_service.service;

import com.bikerapp.auth_service.dao.UserRepository;
import com.bikerapp.auth_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.findByUsername(username);

        if (userResult.isEmpty())
            throw new UsernameNotFoundException("No user found!");
        User user = userResult.get();

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Collections.emptyList()
        );
    }
}

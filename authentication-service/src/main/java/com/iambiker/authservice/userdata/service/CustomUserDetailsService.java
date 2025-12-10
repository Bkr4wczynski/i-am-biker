package com.iambiker.authservice.userdata.service;

import com.iambiker.authservice.model.CustomUserDetails;
import com.iambiker.authservice.userdata.repository.UserRepository;
import com.iambiker.authservice.userdata.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userResult = userRepository.findByUsername(username);

        if (userResult.isEmpty())
            throw new UsernameNotFoundException("No user found with username: "+ username+"!");
        User user = userResult.get();
        return new CustomUserDetails(user.getUsername(), user.getPassword());
    }
}

package com.iambiker.authservice.unit;

import com.iambiker.authservice.jwt.JwtService;
import com.iambiker.authservice.userdata.dto.UserDetailsDTO;
import com.iambiker.authservice.userdata.entity.User;
import com.iambiker.authservice.userdata.entity.UserDetails;
import com.iambiker.authservice.userdata.repository.UserRepository;
import com.iambiker.authservice.userdata.service.UserAuthenticationService;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class UserAuthenticationServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    private User user;
    private UserDetails userDetails;
    private UserDetailsDTO userDetailsDTO;

    @BeforeEach
    void setUp() {
        userDetails = new UserDetails();
        userDetails.setUser_id(1);
        userDetails.setBirthday(LocalDate.of(1990, 1, 1));
        userDetails.setRegistry_date(LocalDate.now());

        user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("rawPassword");
        user.setUser_details(userDetails);
        userDetails.setUser(user);

        userDetailsDTO = new UserDetailsDTO(1, "username", LocalDate.now(), LocalDate.of(1990, 1, 1));
    }

    @Test
    void shouldSaveUserAndEncodePassword() {
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        String result = userAuthenticationService.saveUser(user);

        assertThat(result).isEqualTo("User registered");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        verify(passwordEncoder).encode("rawPassword");
        verify(userRepository).save(user);
    }

    @Test
    void shouldNeverSaveRawPassword() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        userAuthenticationService.saveUser(user);

        verify(userRepository).save(argThat(user -> !user.getPassword().equals("rawPassword")));
    }

    @Test
    void shouldReturnDTOWhenUserExists() {
        when(jwtService.getUserId("valid-token")).thenReturn(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDetailsDTO result = userAuthenticationService.getUserDetailsFromToken("valid-token");

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("username");
        assertThat(result.getUser_id()).isEqualTo(1);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserNotFound() {
        when(jwtService.getUserId("invalid-token")).thenReturn(99);
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userAuthenticationService.getUserDetailsFromToken("invalid-token"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Could not find user details for user");
    }
    @Test
    void shouldUpdateAndReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userAuthenticationService.updateUserDetails(userDetailsDTO);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("username");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userAuthenticationService.updateUserDetails(userDetailsDTO))
                .isInstanceOf(NotFoundException.class);
    }
}

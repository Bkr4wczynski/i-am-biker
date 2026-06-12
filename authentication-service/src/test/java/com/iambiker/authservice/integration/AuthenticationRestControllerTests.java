package com.iambiker.authservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iambiker.authservice.jwt.JwtService;
import com.iambiker.authservice.model.AuthRequest;
import com.iambiker.authservice.rest.AuthenticationRestController;
import com.iambiker.authservice.userdata.dto.UserDTO;
import com.iambiker.authservice.userdata.dto.UserDetailsDTO;
import com.iambiker.authservice.userdata.entity.User;
import com.iambiker.authservice.userdata.repository.UserRepository;
import com.iambiker.authservice.userdata.service.UserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationRestController.class)
public class AuthenticationRestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserAuthenticationService userAuthenticationService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnTrueForValidToken() throws Exception {
        when(jwtService.validateToken("valid.token")).thenReturn(true);

        mockMvc.perform(get("/api/public/validate-token")
                        .param("token", "valid.token"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void shouldReturnFalseForInvalidToken() throws Exception {
        when(jwtService.validateToken("invalid.token")).thenReturn(false);

        mockMvc.perform(get("/api/public/validate-token")
                        .param("token", "invalid.token"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void shouldReturnUsernameWhenTokenValidAndUserExists() throws Exception {
        User user = new User();
        user.setUsername("user");

        when(jwtService.validateToken("valid.token")).thenReturn(true);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/public/get-username/1")
                        .param("token", "valid.token"))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }

    @Test
    void shouldReturn404WhenTokenValidButUserNotFound() throws Exception {
        when(jwtService.validateToken("valid.token")).thenReturn(true);
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/public/get-username/99")
                        .param("token", "valid.token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenTokenInvalidForGetUsername() throws Exception {
        when(jwtService.validateToken("bad.token")).thenReturn(false);

        mockMvc.perform(get("/api/public/get-username/1")
                        .param("token", "bad.token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUserDetailsForValidToken() throws Exception {
        UserDetailsDTO dto = new UserDetailsDTO(1, "username", LocalDate.now(), LocalDate.of(2000, 1, 1));

        when(jwtService.validateToken("valid.token")).thenReturn(true);
        when(userAuthenticationService.getUserDetailsFromToken("valid.token")).thenReturn(dto);

        mockMvc.perform(get("/api/public/user-details")
                        .param("token", "valid.token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void shouldReturn404ForInvalidTokenOnUserDetails() throws Exception {
        when(jwtService.validateToken("bad.token")).thenReturn(false);

        mockMvc.perform(get("/api/public/user-details")
                        .param("token", "bad.token"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGenerateTokenForValidCredentials() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("encodedPassword");

        AuthRequest request = new AuthRequest("username", "rawPassword");

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        when(jwtService.matchPasswords("rawPassword", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken("username", 1)).thenReturn("generated.jwt.token");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/generate-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )

                .andExpect(status().isOk())
                .andExpect(content().string("generated.jwt.token"));
    }

    @Test
    void shouldThrowExceptionForBadCredentials() throws Exception {
        AuthRequest request = new AuthRequest("username", "wrongPassword");

        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());


        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/generate-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200ForValidUserDetails() throws Exception {
        when(jwtService.validateToken(anyString())).thenReturn(true);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        when(userAuthenticationService.updateUserDetails(any())).thenReturn(new User());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/public/user-details")
                .param("token", "valid.token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDetailsDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404ForInvalidToken() throws Exception {
        when(jwtService.validateToken(anyString())).thenReturn(false);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/public/user-details")
                        .param("token", "invalid.token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetailsDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404ForInvalidUserDetails() throws Exception {
        when(jwtService.validateToken(anyString())).thenReturn(true);
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        when(userRepository.findById(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/public/user-details")
                        .param("token", "valid.token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDetailsDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200ForRegisteringUser() throws Exception {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO(1, "username", LocalDate.now(), LocalDate.now());
        UserDTO userDTO = new UserDTO(1, "username", "email@domain.com", "SecurePassword", userDetailsDTO);
        when(userAuthenticationService.saveUser(any())).thenReturn("User registered");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

}

package com.bikerapp.web_service.web_service.controller;

import com.bikerapp.web_service.controller.auth.AuthController;
import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import com.bikerapp.web_service.service.connectionService.AuthConnectionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthConnectionService authConnectionService;

    @Test
    void shouldRenderLoginPage() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(model().attributeExists("loginDTO"));
    }

    @Test
    void shouldRenderRegisterPage() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("userDTO"));
    }

    @Test
    void shouldRedirectToProfileWhenSuccess() throws Exception {
        Mockito.when(authConnectionService.getToken(any(LoginDTO.class)))
                .thenReturn("fake-jwt-token");

        mockMvc.perform(post("/auth/sign-in")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8765/web/my-profile"));
    }

    @Test
    void shouldRedirectToLoginWhenFailed() throws Exception {
        Mockito.when(authConnectionService.getToken(any(LoginDTO.class)))
                .thenThrow(new RuntimeException("Bad Credentials!"));

        mockMvc.perform(post("/auth/sign-in")
                .param("username", "user")
                .param("password", "wrong-password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8765/web/auth/login?error=Bad Credentials!"));
    }

    @Test
    void shouldRedirectToLoginAfterRegistration() throws Exception {
        mockMvc.perform(post("/auth/sign-up")
                        .param("username", "user")
                        .param("password", "password1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8765/web/auth/login"));
    }
}

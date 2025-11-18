package com.iambiker.webservice.web_service.controller;

import com.iambiker.webservice.security.AuthMvcController;
import com.iambiker.webservice.model.dto.authentication.LoginDTO;
import com.iambiker.webservice.security.AuthConnectionService;
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

@WebMvcTest(AuthMvcController.class)
public class AuthMvcControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthConnectionService authConnectionService;

    @Test
    void shouldRenderLoginPage() throws Exception {
        mockMvc.perform(get("/authentication/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("authentication/login"))
                .andExpect(model().attributeExists("loginDTO"));
    }

    @Test
    void shouldRenderRegisterPage() throws Exception {
        mockMvc.perform(get("/authentication/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("authentication/register"))
                .andExpect(model().attributeExists("userDTO"));
    }

    @Test
    void shouldRedirectToProfileWhenSuccess() throws Exception {
        Mockito.when(authConnectionService.getToken(any(LoginDTO.class)))
                .thenReturn("fake-jwt-token");

        mockMvc.perform(post("/authentication/sign-in")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8765/web/my-profile"));
    }

    @Test
    void shouldRedirectToLoginWhenFailed() throws Exception {
        Mockito.when(authConnectionService.getToken(any(LoginDTO.class)))
                .thenThrow(new RuntimeException("Bad Credentials!"));

        mockMvc.perform(post("/authentication/sign-in")
                .param("username", "user")
                .param("password", "wrong-password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8765/web/auth/login?error=Bad Credentials!"));
    }

    @Test
    void shouldRedirectToLoginAfterRegistration() throws Exception {
        mockMvc.perform(post("/authentication/sign-up")
                        .param("username", "user")
                        .param("password", "password1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost:8765/web/auth/login"));
    }
}

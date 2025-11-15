package com.bikerapp.web_service.service.connectionService;

import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import com.bikerapp.web_service.model.dto.auth.UserDTO;
import com.bikerapp.web_service.model.dto.auth.UserDetailsDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.AuthenticationException;

@Service
public class AuthConnectionService {
    private final WebClient webClient = WebClient.create("http://localhost:8765");

    public String getToken(LoginDTO loginDTO) {
        return webClient.post()
                .uri("/auth/generate-token")
                .bodyValue(loginDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public UserDetailsDTO getUserDetails(HttpServletRequest request) throws AuthenticationException {
        String token = getToken(request);
        if (token == null || token.isBlank()) {
            throw new AuthenticationException("Unauthenticated request!");
        }
        return webClient.get()
                .uri("/auth/user-details?token="+token)
                .retrieve()
                .bodyToMono(UserDetailsDTO.class)
                .block();
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void registerUser(UserDTO userDTO) {
        webClient.post()
                .uri("/auth/register-user")
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

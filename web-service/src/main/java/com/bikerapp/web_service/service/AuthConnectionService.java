package com.bikerapp.web_service.service;

import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import com.bikerapp.web_service.model.dto.auth.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
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

    public boolean validateToken(String token) {
        String result = webClient.get()
                .uri("/auth/validate-token?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result != null && result.equals("Token is valid");
    }

    public String registerUser(UserDTO userDTO) {
        return webClient.post()
                .uri("/auth/register-user")
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

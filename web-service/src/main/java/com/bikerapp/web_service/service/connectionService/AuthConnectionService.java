package com.bikerapp.web_service.service.connectionService;

import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import com.bikerapp.web_service.model.dto.auth.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

    public void registerUser(UserDTO userDTO) {
        webClient.post()
                .uri("/auth/register-user")
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

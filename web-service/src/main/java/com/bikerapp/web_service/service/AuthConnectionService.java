package com.bikerapp.web_service.service;

import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import com.bikerapp.web_service.model.dto.auth.UserDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class AuthConnectionService {
    private final WebClient webClient = WebClient.create("http://localhost:8765");

    @RateLimiter(name = "generalLimiter")
    @TimeLimiter(name = "generalLimiter")
    public String getToken(LoginDTO loginDTO) {
        return webClient.post()
                .uri("/auth/generate-token")
                .bodyValue(loginDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @RateLimiter(name = "generalLimiter")
    @TimeLimiter(name = "generalLimiter")
    public void registerUser(UserDTO userDTO) {
        webClient.post()
                .uri("/auth/register-user")
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

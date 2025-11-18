package com.iambiker.webservice.security;

import com.iambiker.webservice.model.dto.authentication.LoginDTO;
import com.iambiker.webservice.model.dto.authentication.RegisterDTO;
import com.iambiker.webservice.model.dto.authentication.UserDetailsDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.AuthenticationException;

@Service
public class AuthConnectionService {
    private final WebClient webClient = WebClient.create("http://localhost:8765");

    public String getToken(LoginDTO loginDTO) {
        return webClient.post()
                .uri("/authentication/generate-token")
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
                .uri("/authentication/user-details?token="+token)
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

    public void registerUser(RegisterDTO registerDTO) {
        webClient.post()
                .uri("/authentication/register-user")
                .bodyValue(registerDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

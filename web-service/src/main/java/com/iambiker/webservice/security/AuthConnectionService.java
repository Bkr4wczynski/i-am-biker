package com.iambiker.webservice.security;

import com.iambiker.webservice.model.dto.authentication.LoginDTO;
import com.iambiker.webservice.model.dto.authentication.RegisterDTO;
import com.iambiker.webservice.model.dto.authentication.UserDetailsDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.AuthenticationException;

@Service
public class AuthConnectionService {
    private final WebClient webClient = WebClient.create("http://localhost:8765/authentication/api/public");

    public String getToken(LoginDTO loginDTO) {
        return webClient.post()
                .uri("/generate-token")
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
        ResponseEntity<UserDetailsDTO> response = webClient.get()
                .uri("/user-details?token="+token)
                .retrieve()
                .toEntity(UserDetailsDTO.class)
                .block();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
            return response.getBody();
        else
            throw new AuthenticationException("Unauthenticated request!");
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

    public Cookie createTokenCookie(String token, int age) {
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(age);
        return cookie;
    }

    public void registerUser(RegisterDTO registerDTO) {
        webClient.post()
                .uri("/register-user")
                .bodyValue(registerDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

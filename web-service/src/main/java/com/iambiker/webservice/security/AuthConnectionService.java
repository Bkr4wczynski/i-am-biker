package com.iambiker.webservice.security;

import com.iambiker.webservice.model.dto.authentication.LoginDTO;
import com.iambiker.webservice.model.dto.authentication.RegisterDTO;
import com.iambiker.webservice.model.dto.authentication.UserDetailsDTO;
import com.iambiker.webservice.webcontent.bike.BikesServiceConnectionService;
import com.iambiker.webservice.webcontent.forum.ForumServiceConnectionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.naming.AuthenticationException;

@Slf4j
@Service
public class AuthConnectionService {
    private final WebClient webClient;

    public AuthConnectionService(@Value("${services.api-gateway-url}") String url) {
        this.webClient = WebClient.create(url+"/authentication/api/public");
    }

    public String getUsernameById(int id, HttpServletRequest request) {
        String token = getToken(request);
        return webClient.get()
                .uri("/get-username/"+id+"?token="+token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

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
            log.info("Unable to fetch user details!");
            throw new AuthenticationException("Unauthenticated request!");
        }
        ResponseEntity<UserDetailsDTO> response = webClient.get()
                .uri("/user-details?token="+token)
                .retrieve()
                .toEntity(UserDetailsDTO.class)
                .block();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            log.info("Successfully fetched user details!");
            return response.getBody();
        }
        else {
            log.info("Unable to fetch user details!");
            throw new AuthenticationException("Unauthenticated request!");
        }
    }

    public void updateUserDetails(UserDetailsDTO userDetailsDTO, HttpServletRequest request) throws AuthenticationException {
        String token = getToken(request);
        if (token == null || token.isBlank()) {
            log.info("Unable to update user details!");
            throw new AuthenticationException("Unauthenticated request!");
        }
        webClient.put()
                .uri("/user-details?token="+token)
                .bodyValue(userDetailsDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    BikesServiceConnectionService.setToken(cookie.getValue());
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
        cookie.setDomain("localhost");
        cookie.setMaxAge(age);
        BikesServiceConnectionService.setToken(token);
        ForumServiceConnectionService.setToken(token);
        return cookie;
    }

    public void registerUser(RegisterDTO registerDTO) {
        log.info("Registering user with username: {}", registerDTO.getUsername());
        webClient.post()
                .uri("/register-user")
                .bodyValue(registerDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}

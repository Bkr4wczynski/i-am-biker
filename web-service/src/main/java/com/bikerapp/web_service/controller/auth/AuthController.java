package com.bikerapp.web_service.controller.auth;

import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@AllArgsConstructor
public class AuthController {
    private final WebClient webClient = WebClient.create("http://localhost:8765");

    @GetMapping("/register")
    public String displayRegisterPage() {
        return "auth/register";
    }

    @GetMapping("/login")
    public String displayLoginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "auth/login";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute LoginDTO loginDTO) {
        String token = getToken(loginDTO);
        System.out.println(token);
        return "myProfile";
    }

    private String getToken(LoginDTO loginDTO) {
        String result = webClient.post()
                .uri("/auth/generate-token")
                .bodyValue(loginDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return result;
    }
}

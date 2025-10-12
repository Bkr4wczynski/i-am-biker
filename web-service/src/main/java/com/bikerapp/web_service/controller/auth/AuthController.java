package com.bikerapp.web_service.controller.auth;

import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import com.bikerapp.web_service.service.AuthConnectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthConnectionService authConnectionService;

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
        String token = authConnectionService.getToken(loginDTO);
        System.out.println(token);
        return "redirect:http://localhost:8765/web/my-profile";
    }
}

package com.bikerapp.web_service.controller.auth;

import com.bikerapp.web_service.model.dto.auth.LoginDTO;
import com.bikerapp.web_service.model.dto.auth.UserDTO;
import com.bikerapp.web_service.service.AuthConnectionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthConnectionService authConnectionService;

    @GetMapping("/register")
    public String displayRegisterPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/register";
    }

    @GetMapping("/login")
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        model.addAttribute("loginDTO", new LoginDTO());
        return "auth/login";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute LoginDTO loginDTO, HttpServletResponse response) {
        String token;
        try {
            token = authConnectionService.getToken(loginDTO);
        } catch (RuntimeException e) {
            return "redirect:http://localhost:8765/web/auth/login?error=Bad Credentials!";
        }
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(15*60);
        response.addCookie(cookie);
        return "redirect:http://localhost:8765/web/my-profile";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserDTO userDTO) {
        authConnectionService.registerUser(userDTO);
        return "redirect:http://localhost:8765/web/auth/login";
    }
}

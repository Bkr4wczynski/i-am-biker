package com.bikerapp.web_service.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String displayLoginPage() {
        return "auth/login";
    }
}

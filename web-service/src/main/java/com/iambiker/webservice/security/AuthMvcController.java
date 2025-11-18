package com.iambiker.webservice.security;

import com.iambiker.webservice.model.dto.authentication.LoginDTO;
import com.iambiker.webservice.model.dto.authentication.RegisterDTO;
import com.iambiker.webservice.model.dto.authentication.UserDetailsDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/authentication")
public class AuthMvcController {
    private final AuthConnectionService authConnectionService;

    @GetMapping("/register")
    public String displayRegisterPage(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        model.addAttribute("userDetailsDTO", new UserDetailsDTO());
        return "authentication/register";
    }

    @GetMapping("/login")
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        model.addAttribute("loginDTO", new LoginDTO());
        return "authentication/login";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute LoginDTO loginDTO, HttpServletResponse response) {
        log.info("User: {} asked to login", loginDTO.getUsername());
        String token;
        try {
            token = authConnectionService.getToken(loginDTO);
        } catch (RuntimeException e) {
            return "redirect:http://localhost:8765/web/authentication/login?error=Bad Credentials!";
        }
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);
        return "redirect:http://localhost:8765/web/my-profile";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute RegisterDTO registerDTO, @ModelAttribute UserDetailsDTO userDetailsDTO) {
        userDetailsDTO.setRegistry_date(LocalDate.now());
        registerDTO.setUserDetailsDTO(userDetailsDTO);
        authConnectionService.registerUser(registerDTO);
        log.info("User: {} asked to register", registerDTO.getUsername());
        return "redirect:http://localhost:8765/web/authentication/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "redirect:http://localhost:8765/web/authentication/login?error=Logout successful";
    }
}

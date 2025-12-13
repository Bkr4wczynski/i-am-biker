package com.iambiker.webservice.webcontent.profile;

import com.iambiker.webservice.model.dto.authentication.UserDetailsDTO;
import com.iambiker.webservice.security.AuthConnectionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

@Slf4j
@Controller
@AllArgsConstructor
public class ProfilePageController {
    private AuthConnectionService authConnectionService;

    @GetMapping("/my-profile")
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallback")
    public String displayProfilePage(Model model, HttpServletRequest request) {
        try {
            UserDetailsDTO userDetails = authConnectionService.getUserDetails(request);
            model.addAttribute("user_details", userDetails);
            boolean isBirthday = isBirthday(userDetails);
            model.addAttribute("isBirthday", isBirthday);
        } catch (AuthenticationException e) {
            log.warn("Could not get user details!");
            throw new RuntimeException(e);
        }
        return "profile/myProfile";
    }

    @GetMapping("/settings")
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallback")
    public String showSettingsPage(Model model, HttpServletRequest request) {
        try {
            UserDetailsDTO userDetailsDTO = authConnectionService.getUserDetails(request);
            model.addAttribute("user_details", userDetailsDTO);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        return "profile/settings";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("user_details") UserDetailsDTO userDetailsDTO, HttpServletRequest request) {
        try {
            authConnectionService.updateUserDetails(userDetailsDTO, request);
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        return "profile/myProfile";
    }

    private boolean isBirthday(UserDetailsDTO userDetails) {
        LocalDate today = LocalDate.now();
        return userDetails.getBirthday() != null &&
                userDetails.getBirthday().getMonth() == today.getMonth() &&
                userDetails.getBirthday().getDayOfMonth() == today.getDayOfMonth();
    }

    public String fallback(Model model, HttpServletRequest request, Throwable throwable) {
        UserDetailsDTO userDetailsDTOStub = new UserDetailsDTO(1, "Not found", LocalDate.now(), LocalDate.of(2000, 1, 1));
        model.addAttribute("user_details", userDetailsDTOStub);
        model.addAttribute("isBirthday", false);
        log.error("Failed to display profile page!");
        return "profile/myProfile";
    }

}

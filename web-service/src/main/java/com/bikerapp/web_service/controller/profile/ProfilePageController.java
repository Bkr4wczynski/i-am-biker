package com.bikerapp.web_service.controller.profile;

import com.bikerapp.web_service.model.dto.auth.UserDetailsDTO;
import com.bikerapp.web_service.service.connectionService.AuthConnectionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

@Slf4j
@Controller
@AllArgsConstructor
public class ProfilePageController {
    private AuthConnectionService authConnectionService;

    @GetMapping("/my-profile")
    public String displayProfilePage(Model model, HttpServletRequest request) {
        try {
            UserDetailsDTO userDetails = authConnectionService.getUserDetails(request);
            model.addAttribute("user_details", userDetails);
            LocalDate today = LocalDate.now();
            boolean isBirthday = userDetails.getBirthday() != null &&
                    userDetails.getBirthday().getMonth() == today.getMonth() &&
                    userDetails.getBirthday().getDayOfMonth() == today.getDayOfMonth();
            model.addAttribute("isBirthday", isBirthday);
        } catch (AuthenticationException e) {
            log.warn("Could not get user details!");
            throw new RuntimeException(e);
        }
        return "myProfile";
    }

}

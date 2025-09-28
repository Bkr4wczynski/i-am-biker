package com.bikerapp.web_service.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class ProfilePageController {

    @GetMapping("/my-profile")
    public String displayProfilePage(Model model) {
        return "myProfile";
    }

}

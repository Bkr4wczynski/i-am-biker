package com.bikerapp.web_service.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilePageController {

    @GetMapping("/my-profile")
    public String displayProfilePage(Model model) {
        return "myProfile";
    }

}

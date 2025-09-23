package com.bikerapp.web_service.controller;

import com.bikerapp.web_service.dao.BikesRepository;
import com.bikerapp.web_service.model.Bike;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
public class ProfilePageController {
    private BikesRepository bikesRepository;

    @Autowired
    public ProfilePageController(BikesRepository bikesRepository) {
        this.bikesRepository = bikesRepository;
    }

    private List<Bike> getBikesData() {
        return bikesRepository.findAll();
    }

    @GetMapping("/my-profile")
    public String displayProfilePage(HttpServletRequest request, Model model) {
        request.getSession().setAttribute("bikes", getBikesData());
        return "myProfile";
    }

}

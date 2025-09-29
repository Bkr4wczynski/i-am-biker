package com.bikerapp.web_service.controller;

import com.bikerapp.web_service.dao.entity.Bike;
import com.bikerapp.web_service.service.BikesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class BikesPageController {
    private final BikesService bikesService;

    @GetMapping("/my-bikes")
    public String displayBikes(Model model) {
        List<Bike> bikes = bikesService.getBikes();
        model.addAttribute("bikes", bikes);
        return "bike/myBikes";
    }
}

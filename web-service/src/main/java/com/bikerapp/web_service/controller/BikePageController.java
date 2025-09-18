package com.bikerapp.web_service.controller;

import com.bikerapp.web_service.model.Bike;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BikePageController {
    @GetMapping("/my-bike")
    public String displayMyBikePage(@RequestParam int id, HttpServletRequest request, Model model) {
        List<Bike> bikes = (List<Bike>) request.getSession().getAttribute("bikes");
        if (bikes == null)
            bikes = new ArrayList<>();
        Bike bike = bikes.stream().filter(b -> b.getId() == id).findFirst().orElseThrow();
        model.addAttribute("bike", bike);
        return "myBike";
    }
}

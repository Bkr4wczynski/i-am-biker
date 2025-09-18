package com.bikerapp.web_service.controller;

import com.bikerapp.web_service.dao.BikesRepository;
import com.bikerapp.web_service.model.Bike;
import com.bikerapp.web_service.model.Engine;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BikePageController {
    private BikesRepository bikesRepository;

    @Autowired
    public BikePageController(BikesRepository bikesRepository) {
        this.bikesRepository = bikesRepository;
    }

    @GetMapping("/my-bike")
    public String displayMyBikePage(@RequestParam int id, HttpServletRequest request, Model model) {
        List<Bike> bikes = getListOfBikes(request.getSession());
        if (bikes == null)
            bikes = new ArrayList<>();
        Bike bike = bikes.stream().filter(b -> b.getId() == id).findFirst().orElseThrow();
        model.addAttribute("bike", bike);
        return "bike/myBike";
    }

    private List<Bike> getListOfBikes(HttpSession session) {
        return (List<Bike>) session.getAttribute("bikes");
    }

    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model) {
        Bike bike = new Bike();
        bike.setEngine(new Engine());
        model.addAttribute("bike", bike);
        return "bike/addBike";
    }

    @PostMapping("/add-bike")
    public String addBike(@ModelAttribute("bike") Bike bike, HttpServletRequest request) {
        int id = getListOfBikes(request.getSession()).size()+1;
        bike.setId(id);
        bikesRepository.addBike(bike);
        return "redirect:/my-profile";
    }
}

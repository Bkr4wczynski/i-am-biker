package com.bikerapp.web_service.controller;

import com.bikerapp.web_service.model.Bike;
import com.bikerapp.web_service.model.Engine;
import com.bikerapp.web_service.service.BikesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class BikePageController {
    private BikesService bikesService;

    @GetMapping("/my-bike")
    public String displayMyBikePage(@RequestParam int id, Model model) {
        Bike bike = bikesService.findBike(id).get();
        model.addAttribute("bike", bike);
        return "bike/myBike";
    }

    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model) {
        Bike bike = new Bike();
        bike.setEngine(new Engine());
        model.addAttribute("bike", bike);
        return "bike/addBike";
    }

    @PostMapping("/add-bike")
    public String addBike(@ModelAttribute("bike") Bike bike) {
        bikesService.saveBike(bike);
        return "redirect:/my-profile";
    }

    @DeleteMapping("/delete-bike")
    public String deleteBike(@RequestParam int id) {
        bikesService.deleteBike(id);
        return "redirect:/my-profile";
    }

}

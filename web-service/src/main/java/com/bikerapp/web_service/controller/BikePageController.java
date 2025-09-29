package com.bikerapp.web_service.controller;

import com.bikerapp.web_service.dao.entity.Bike;
import com.bikerapp.web_service.dao.entity.Engine;
import com.bikerapp.web_service.model.dto.BikeDTO;
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
        BikeDTO bike = bikesService.findBike(id).get();
        model.addAttribute("bike", bike);
        return "bike/myBike";
    }

    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model) {
        BikeDTO bike = new BikeDTO();
        bike.setEngine(new Engine());
        model.addAttribute("bike", bike);
        return "bike/addBike";
    }

    @GetMapping("/update-bike")
    public String showUpdateBikeForm(@RequestParam int id, Model model) {
        BikeDTO bike = bikesService.findBike(id).get();
        model.addAttribute("bike", bike);
        return "bike/updateBike";
    }

    @PostMapping("/add-bike")
    public String addBike(@ModelAttribute("bike") BikeDTO bike) {
        bikesService.saveBike(bike);
        return "redirect:/my-profile";
    }

    @DeleteMapping("/delete-bike")
    public String deleteBike(@RequestParam int id) {
        bikesService.deleteBike(id);
        return "redirect:/my-profile";
    }

    @PutMapping("/update-bike")
    public String updateBike(@ModelAttribute("bike") BikeDTO bike) {
        bikesService.updateBike(bike);
        return "redirect:/my-profile";
    }

}

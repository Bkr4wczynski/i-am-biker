package com.bikerapp.web_service.controller;

import com.bikerapp.web_service.dao.entity.Bike;
import com.bikerapp.web_service.service.BikesService;
import com.bikerapp.web_service.service.MaintenanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@AllArgsConstructor
public class MaintenancePageController {
    private final MaintenanceService maintenanceService;
    private final BikesService bikesService;

    @GetMapping("/maintenance")
    public String showMaintenancePage(@RequestParam int id, Model model) {
        Bike bike = bikesService.findBike(id).get();
        HashMap<String, Integer> maintenanceKm = maintenanceService.generateMaintenanceByMileage(bike.getMileage());
        model.addAttribute("bike", bike);
        model.addAttribute("maintenance", maintenanceKm);
        return "maintenance/maintenance";
    }
}

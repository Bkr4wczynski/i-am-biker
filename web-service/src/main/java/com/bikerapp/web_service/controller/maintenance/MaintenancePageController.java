package com.bikerapp.web_service.controller.maintenance;

import com.bikerapp.web_service.model.dto.BikeDTO;
import com.bikerapp.web_service.service.BikesService;
import com.bikerapp.web_service.service.connectionService.MaintenanceConnectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Slf4j
@Controller
@AllArgsConstructor
public class MaintenancePageController {
    private final MaintenanceConnectionService maintenanceConnectionService;
    private final BikesService bikesService;

    @GetMapping("/maintenance")
    public String showMaintenancePage(@RequestParam int id, Model model) {
        BikeDTO bike = bikesService.findBike(id).get();
        HashMap<String, Integer> maintenanceKm = maintenanceConnectionService.generateMaintenanceData(bike.getMileage());
        model.addAttribute("bike", bike);
        model.addAttribute("maintenance", maintenanceKm);
        log.info("Generated maintenance data for bike: {} ", bike.getModel());
        return "maintenance/maintenance";
    }
}

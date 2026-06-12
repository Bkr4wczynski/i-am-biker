package com.iambiker.webservice.webcontent.maintenance;

import com.iambiker.webservice.model.dto.personal.BikeDTO;
import com.iambiker.webservice.webcontent.bike.BikesServiceConnectionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@AllArgsConstructor
@Slf4j
public class MaintenancePageController {
    private final MaintenanceConnectionService maintenanceConnectionService;
    private final BikesServiceConnectionService bikesService;

    @GetMapping("/maintenance")
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallback")
    public String showMaintenancePage(@RequestParam int id, Model model) {
        BikeDTO bike = bikesService.getBikeById(id);
        HashMap<String, Integer> maintenanceKm = maintenanceConnectionService.generateMaintenanceData(bike.getMileage());
        model.addAttribute("bike", bike);
        model.addAttribute("maintenance", maintenanceKm);
        return "maintenance/maintenance";
    }

    public String fallback(@RequestParam int id, Model model, Throwable throwable) {
        model.addAttribute("bike", null);
        model.addAttribute("maintenance", null);
        log.error("Failed to display maintenance page!");
        return "maintenance/maintenance";
    }
}

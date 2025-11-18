package com.iambiker.maintenanceservice.rest;

import com.iambiker.maintenanceservice.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/maintenance")
@RequiredArgsConstructor
@Slf4j
public class MaintenanceRestController {
    private final MaintenanceService maintenanceService;

    @PostMapping("/generate")
    public HashMap<String, Integer> generateMaintenanceByMileage(@RequestParam int mileage) {
        log.info("User asked for generating data with {} mileage", mileage);
        return maintenanceService.generateMaintenanceByMileage(mileage);
    }
}

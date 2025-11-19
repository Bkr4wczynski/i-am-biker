package com.iambiker.maintenanceservice.rest;

import com.iambiker.maintenanceservice.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/maintenance")
@RequiredArgsConstructor
@Slf4j
public class MaintenanceRestController {
    private final MaintenanceService maintenanceService;

    @GetMapping("/get-maintenance")
    public ResponseEntity<HashMap<String, Integer>> generateMaintenanceByMileage(@RequestParam int mileage) {
        log.info("User asked for generating data with {} mileage", mileage);
        return ResponseEntity.ok(maintenanceService.generateMaintenanceByMileage(mileage));
    }
}

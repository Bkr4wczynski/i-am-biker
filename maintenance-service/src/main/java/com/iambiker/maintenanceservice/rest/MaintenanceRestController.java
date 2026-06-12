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
public class MaintenanceRestController {
    private final MaintenanceService maintenanceService;

    @GetMapping("/get-maintenance")
    public ResponseEntity<HashMap<String, Integer>> generateMaintenanceByMileage(@RequestParam int mileage) {
        HashMap<String, Integer> result = maintenanceService.generateMaintenanceByMileage(mileage);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}

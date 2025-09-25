package com.bikerapp.web_service.service;

import com.bikerapp.web_service.model.MaintenanceElements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.bikerapp.web_service.model.MaintenanceElements.*;

@Service
public class MaintenanceService {
    private static final List<MaintenanceElements> FIRST_INSPECTION_ELEMENTS = List.of(
            OIL_CHANGE
    );

    public HashMap<String, Integer> generateMaintenanceByMileage(int mileage) {
        HashMap<String, Integer> maintenanceMileage = new HashMap<>();
        if (mileage <= 1000) {
            for (MaintenanceElements element: MaintenanceElements.values()) {
                if (FIRST_INSPECTION_ELEMENTS.contains(element))
                    maintenanceMileage.put(element.name(), 1000 - mileage);
                maintenanceMileage.put(element.getDisplayName(), mileage % element.getIntervalKm());
            }
            return maintenanceMileage;
        }

        for (MaintenanceElements element: MaintenanceElements.values()) {
            maintenanceMileage.put(element.getDisplayName(), mileage % element.getIntervalKm());
        }
        return maintenanceMileage;
    }
}

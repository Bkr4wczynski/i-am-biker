package com.bikerapp.web_service.service;

import com.bikerapp.web_service.model.enums.MaintenanceElements;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.bikerapp.web_service.model.enums.MaintenanceElements.*;

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
                    maintenanceMileage.put(element.getDisplayName(), 1000 - mileage);
                else
                    maintenanceMileage.put(element.getDisplayName(), calculateInterval(mileage, element.getIntervalKm()));
            }
            return maintenanceMileage;
        }

        for (MaintenanceElements element: MaintenanceElements.values()) {
            maintenanceMileage.put(element.getDisplayName(), calculateInterval(mileage, element.getIntervalKm()));
        }
        return maintenanceMileage;
    }

    public int calculateInterval(int mil, int interval) {
        if (mil == 0)
            return interval;
        if (mil % interval == 0)
            return 0;
        return interval - (mil % interval);
    }
}

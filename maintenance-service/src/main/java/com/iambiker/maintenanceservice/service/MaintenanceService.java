package com.iambiker.maintenanceservice.service;

import com.iambiker.maintenanceservice.enums.MaintenanceElements;
import com.iambiker.maintenanceservice.exception.InvalidMileageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.iambiker.maintenanceservice.enums.MaintenanceElements.OIL_CHANGE;

@Service
@Slf4j
public class MaintenanceService {
    private static final List<MaintenanceElements> FIRST_INSPECTION_ELEMENTS = List.of(
            OIL_CHANGE
    );

    public HashMap<String, Integer> generateMaintenanceByMileage(int mileage) throws InvalidMileageException {
        log.info("User asked for generating data with {} mileage", mileage);

        if (mileage < 0 || mileage > 999999) {
            log.warn("Mileage: {} is not valid!", mileage);
            throw new InvalidMileageException("Mileage must be between 0 and 999 999!");
        }
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

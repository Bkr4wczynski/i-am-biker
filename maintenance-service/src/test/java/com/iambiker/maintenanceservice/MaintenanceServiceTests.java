package com.iambiker.maintenanceservice;

import com.iambiker.maintenanceservice.service.MaintenanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class MaintenanceServiceTests {
    private final MaintenanceService maintenanceService = new MaintenanceService();
    @ParameterizedTest
    @CsvSource({
            "0, 1000, 1000",
            "24000, 12000, 0",
            "10000, 24000, 14000",
            "11000, 12000, 1000"
    })
    void testCalculateInterval(int mileage, int interval, int expected) {
        Assertions.assertEquals(expected, maintenanceService.calculateInterval(mileage, interval));

    }

    @ParameterizedTest
    @CsvSource({
            "0, 1000",
            "500, 500",
            "1000, 0"
    })
    void testGenerateMaintenanceByMileageBelow1000(int mileage, int expected) {
        HashMap<String, Integer> result = maintenanceService.generateMaintenanceByMileage(mileage);
        assertEquals(expected, result.get("Oil change"));
    }
}

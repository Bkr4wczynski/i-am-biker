package com.iambiker.maintenanceservice.unit.unit;

import com.iambiker.maintenanceservice.exception.InvalidMileageException;
import com.iambiker.maintenanceservice.service.MaintenanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


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
        HashMap<String, Integer> result = null;
        try {
            result = maintenanceService.generateMaintenanceByMileage(mileage);
        } catch (InvalidMileageException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, result.get("Oil change"));
    }

    @Test
    void shouldReturnNullForBadInput() {
        try {
            assertNull(maintenanceService.generateMaintenanceByMileage(-1000));
            assertNull(maintenanceService.generateMaintenanceByMileage(1000000));
        } catch (InvalidMileageException e) {
            throw new RuntimeException(e);
        }
    }
}

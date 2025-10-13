package com.bikerapp.web_service.web_service;

import com.bikerapp.web_service.service.MaintenanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class MaintenanceServiceTests {
    @ParameterizedTest
    @CsvSource({
            "0, 1000, 1000",
            "24000, 12000, 0",
            "10000, 24000, 14000",
            "11000, 12000, 1000"
    })
    void testCalculateInterval(int mileage, int interval, int expected) {
        MaintenanceService maintenanceService = new MaintenanceService();
        assertEquals(expected, maintenanceService.calculateInterval(mileage, interval));

    }
}

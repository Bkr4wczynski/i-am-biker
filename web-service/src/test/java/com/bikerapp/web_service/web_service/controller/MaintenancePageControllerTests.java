package com.bikerapp.web_service.web_service.controller;

import com.bikerapp.web_service.controller.auth.AuthController;
import com.bikerapp.web_service.controller.maintenance.MaintenancePageController;
import com.bikerapp.web_service.dao.entity.Bike;
import com.bikerapp.web_service.dao.entity.Engine;
import com.bikerapp.web_service.model.dto.BikeDTO;
import com.bikerapp.web_service.service.BikesService;
import com.bikerapp.web_service.service.MaintenanceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaintenancePageController.class)
public class MaintenancePageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MaintenanceService maintenanceService;

    @MockitoBean
    private BikesService bikesService;

    @Test
    void testIfMaintenancePageIsRendered() throws Exception {
        Mockito.when(bikesService.findBike(Mockito.anyInt()))
                .thenReturn(Optional.of(new BikeDTO(1, "example", LocalDate.now(), 0, new Engine())));
        Mockito.when(maintenanceService.generateMaintenanceByMileage(Mockito.anyInt()))
                .thenReturn(new HashMap<>());

        mockMvc.perform(get("/maintenance?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("maintenance/maintenance"))
                .andExpect(model().attributeExists("bike"))
                .andExpect(model().attributeExists("maintenance"));

    }
}

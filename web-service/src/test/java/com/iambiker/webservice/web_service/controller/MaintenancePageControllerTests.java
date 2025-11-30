package com.iambiker.webservice.web_service.controller;

import com.iambiker.webservice.model.dto.personal.BikeDTO;
import com.iambiker.webservice.webcontent.bike.BikesService;
import com.iambiker.webservice.webcontent.maintenance.MaintenanceConnectionService;
import com.iambiker.webservice.webcontent.maintenance.MaintenancePageController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(MaintenancePageController.class)
public class MaintenancePageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BikesService bikesService;

    @MockitoBean
    private MaintenanceConnectionService maintenanceConnectionService;

    @Test
    void shouldShowMaintenancePage() throws Exception {
        Mockito.when(bikesService.findBike(anyInt()))
                .thenReturn(Optional.of(new BikeDTO()));
        HashMap<String, Integer> mockMap = new HashMap<>();
        mockMap.put("example", 1000);
        Mockito.when(maintenanceConnectionService.generateMaintenanceData(anyInt()))
                .thenReturn(mockMap);
        mockMvc.perform(get("/maintenance")
                        .param("id", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("maintenance/maintenance"))
                .andExpect(model().attributeExists("bike"))
                .andExpect(model().attributeExists("maintenance"));

    }
}

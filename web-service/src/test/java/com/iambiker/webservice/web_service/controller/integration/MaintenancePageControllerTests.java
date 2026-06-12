package com.iambiker.webservice.web_service.controller.integration;

import com.iambiker.webservice.model.dto.personal.BikeDTO;
import com.iambiker.webservice.webcontent.bike.BikesServiceConnectionService;
import com.iambiker.webservice.webcontent.maintenance.MaintenanceConnectionService;
import com.iambiker.webservice.webcontent.maintenance.MaintenancePageController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(MaintenancePageController.class)
public class MaintenancePageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BikesServiceConnectionService bikesService;

    @MockitoBean
    private MaintenanceConnectionService maintenanceConnectionService;

    @Test
    void shouldShowMaintenancePage() throws Exception {
        Mockito.when(bikesService.getBikeById(anyInt()))
                .thenReturn(new BikeDTO());
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

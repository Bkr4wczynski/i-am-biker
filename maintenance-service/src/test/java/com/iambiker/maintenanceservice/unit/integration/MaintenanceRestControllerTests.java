package com.iambiker.maintenanceservice.unit.integration;

import com.iambiker.maintenanceservice.rest.MaintenanceRestController;
import com.iambiker.maintenanceservice.service.MaintenanceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MaintenanceRestController.class)
public class MaintenanceRestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MaintenanceService maintenanceService;

    @Test
    void shouldReturnOkForValidMaintenanceData() throws Exception {
        mockMvc.perform(get("/maintenance/get-maintenance")
                        .param("mileage", "15000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldReturn400ForUnValidMaintenanceData() throws Exception {
        Mockito.when(maintenanceService.generateMaintenanceByMileage(anyInt())).thenReturn(null);
        mockMvc.perform(get("/maintenance/get-maintenance")
                        .param("mileage", "-15000"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/maintenance/get-maintenance")
                        .param("mileage", "10000000"))
                .andExpect(status().isBadRequest());
    }

}

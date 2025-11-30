package com.iambiker.webservice.web_service.controller;

import com.iambiker.webservice.database.entity.Engine;
import com.iambiker.webservice.model.dto.personal.BikeDTO;
import com.iambiker.webservice.model.enums.EngineType;
import com.iambiker.webservice.security.JwtWebUtil;
import com.iambiker.webservice.webcontent.bike.BikesPageMvcController;
import com.iambiker.webservice.webcontent.bike.BikesService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BikesPageMvcController.class)
public class BikesPageMvcControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtWebUtil jwtWebUtil;

    @MockitoBean
    private BikesService bikesService;

    @Test
    void shouldDisplayBikesPage() throws Exception {
        Mockito.when(jwtWebUtil.getUserIdFromToken(anyString()))
                .thenReturn(0);
        List<BikeDTO> mockList = List.of(new BikeDTO());
        Mockito.when(bikesService.getBikes(anyInt()))
                .thenReturn(mockList);

        mockMvc.perform(get("/my-bikes")
                        .cookie(new Cookie("token", "fake-jwt-token")))
                .andExpect(status().isOk())
                .andExpect(view().name("bike/myBikes"))
                .andExpect(model().attributeExists("bikes"));
    }

    @Test
    void shouldDisplaySpecificBikePage() throws Exception {
        BikeDTO mockBike = new BikeDTO();
        mockBike.setEngine(new Engine());
        mockBike.getEngine().setEngineType(EngineType.FOUR_STROKE);
        Mockito.when(bikesService.findBike(anyInt()))
                .thenReturn(Optional.of(mockBike));

        mockMvc.perform(get("/my-bike")
                .param("id", "0"))
                .andExpect(status().isOk());
    }
}

package com.iambiker.bikesservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iambiker.bikesservice.database.entity.Bike;
import com.iambiker.bikesservice.model.dto.BikeDTO;
import com.iambiker.bikesservice.model.dto.EngineDTO;
import com.iambiker.bikesservice.model.enums.EngineType;
import com.iambiker.bikesservice.rest.BikesRestController;
import com.iambiker.bikesservice.rest.BikesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BikesRestController.class)
public class BikesRestControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BikesService bikesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldDisplayBikes() throws Exception {
        Mockito.when(bikesService.getBikes(anyInt())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/get-bikes")
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void shouldDisplaySpecificBike() throws Exception {
        Mockito.when(bikesService.findBike(anyInt())).thenReturn(Optional.of(new BikeDTO()));
        mockMvc.perform(get("/find-bike")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}

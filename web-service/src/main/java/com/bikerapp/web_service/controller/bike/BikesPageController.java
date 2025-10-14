package com.bikerapp.web_service.controller.bike;

import com.bikerapp.web_service.model.dto.BikeDTO;
import com.bikerapp.web_service.service.BikesService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BikesPageController {
    private final BikesService bikesService;

    @GetMapping("/my-bikes")
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallback")
    public String displayBikes(Model model) {
        List<BikeDTO> bikes = bikesService.getBikes();
        model.addAttribute("bikes", bikes);
        return "bike/myBikes";
    }

    public String fallback(Exception e, Model model) {
        List<BikeDTO> bikes = new ArrayList<>();
        model.addAttribute("bikes", bikes);
        log.warn("Failed to load bikes list!");
        return "bike/myBikes";
    }
}

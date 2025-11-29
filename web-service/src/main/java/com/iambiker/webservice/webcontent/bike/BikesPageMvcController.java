package com.iambiker.webservice.webcontent.bike;

import com.iambiker.webservice.model.dto.personal.BikeDTO;
import com.iambiker.webservice.security.JwtWebUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class BikesPageMvcController {
    private final BikesService bikesService;
    private final JwtWebUtil jwtWebUtil;

    @GetMapping("/my-bikes")
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallback")
    public String displayBikes(@CookieValue(name = "token") String token, Model model) {
        int userId = jwtWebUtil.getUserIdFromToken(token);
        List<BikeDTO> bikes = bikesService.getBikes(userId);
        model.addAttribute("bikes", bikes);
        return "bike/myBikes";
    }

    @GetMapping("/my-bike")
    public String displayMyBikePage(@RequestParam int id, Model model) {
        BikeDTO bike = bikesService.findBike(id).get();
        model.addAttribute("bike", bike);
        return "bike/myBike";
    }

    public String fallback(@CookieValue(name = "token") String token, Model model, Throwable throwable) {
        List<BikeDTO> bikes = new ArrayList<>();
        model.addAttribute("bikes", bikes);
        log.error("Failed to load bikes list!");
        return "bike/myBikes";
    }

    public String fallback(@RequestParam int id, Model model, Throwable throwable) {
        model.addAttribute("bike", null);
        log.error("Failed to load my bike website!");
        return "bike/myBike";
    }
}

package com.bikerapp.web_service.service.connectionService;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Service
public class MaintenanceConnectionService {
    private final WebClient webClient = WebClient.create("http://localhost:8765");

    public HashMap<String, Integer> generateMaintenanceData(int mileage) {
        return webClient.post()
                .uri("/maintenance/generate?mileage="+mileage)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Integer>>() {})
                .block();
    }
}

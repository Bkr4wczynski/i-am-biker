package com.iambiker.webservice.webcontent.maintenance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;

@Service
public class MaintenanceConnectionService {
    private final WebClient webClient;

    public MaintenanceConnectionService(@Value("${services.api-gateway-url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public HashMap<String, Integer> generateMaintenanceData(int mileage) {
        return webClient.get()
                .uri("/maintenance/get-maintenance?mileage="+mileage)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Integer>>() {})
                .block();
    }
}

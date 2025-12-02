package com.iambiker.webservice.webcontent.bike;

import com.iambiker.webservice.model.dto.personal.BikeDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class BikesServiceConnectionService {
    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8765/bikes")
            .filter((request, next) -> {
                ClientRequest newReq = ClientRequest.from(request)
                        .cookie("token", TOKEN)
                        .build();
                return next.exchange(newReq);
            })
            .build();

    private static String TOKEN;

    public static void setToken(String token) {
        TOKEN = token;
    }

    public BikeDTO getBikeById(int id) {
        return webClient.get()
                .uri("/find-bike?id="+id)
                .retrieve()
                .toEntity(BikeDTO.class)
                .block()
                .getBody();
    }

    public List<BikeDTO> getBikes(int userId) {
        return webClient.get()
                .uri("/get-bikes?userId="+userId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BikeDTO>>() {})
                .block();
    }

    public BikeDTO saveBike(BikeDTO dto) {
        return webClient.post()
                .uri("/save-bike")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(BikeDTO.class)
                .block();
    }

    public Void deleteBike(int id) {
        return webClient.delete()
                .uri("/delete-bike?id="+id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public BikeDTO updateBike(BikeDTO dto) {
        return webClient.put()
                .uri("/update-bike?")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(BikeDTO.class)
                .block();
    }
}

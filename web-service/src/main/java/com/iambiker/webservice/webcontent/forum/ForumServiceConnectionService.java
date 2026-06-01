package com.iambiker.webservice.webcontent.forum;

import com.iambiker.webservice.model.dto.personal.PostDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ForumServiceConnectionService {
    private final WebClient webClient;

    public ForumServiceConnectionService(@Value("${services.api-gateway-url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url+"/forum")
                .filter((request, next) -> {
                    ClientRequest newReq = ClientRequest.from(request)
                            .cookie("token", TOKEN)
                            .build();
                    return next.exchange(newReq);
                })
                .build();
    }

    private static String TOKEN;

    public static void setToken(String token) {
        TOKEN = token;
    }

    public PostDTO getPostById(long id) {
        return webClient.get()
                .uri("/read/"+id)
                .retrieve()
                .toEntity(PostDTO.class)
                .block()
                .getBody();
    }

    public List<PostDTO> getAllPosts() {
        return webClient.get()
                .uri("/read")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PostDTO>>() {})
                .block();
    }

    public PostDTO createPost(PostDTO postDTO) {
        return webClient.post()
                .uri("/create")
                .bodyValue(postDTO)
                .retrieve()
                .bodyToMono(PostDTO.class)
                .block();
    }

    public Void deletePost(long id) {
        return webClient.delete()
                .uri("/delete/"+id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public PostDTO updateBike(long id, PostDTO postDTO) {
        return webClient.put()
                .uri("/update/"+id)
                .bodyValue(postDTO)
                .retrieve()
                .bodyToMono(PostDTO.class)
                .block();
    }
}

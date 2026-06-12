package com.iambiker.webservice.webcontent.forum;

import com.iambiker.webservice.model.dto.personal.PostDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
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
        log.info("User requested to get post with id: {}", id);
        return webClient.get()
                .uri("/read/"+id)
                .retrieve()
                .toEntity(PostDTO.class)
                .block()
                .getBody();
    }

    public List<PostDTO> getAllPosts() {
        log.info("User requested to get all posts");
        return webClient.get()
                .uri("/read")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PostDTO>>() {})
                .block();
    }

    public void createPost(PostDTO postDTO) {
        postDTO.setCreatedAt(LocalDateTime.now());
        postDTO.setUpdatedAt(LocalDateTime.now());
        log.info("User requested to create post titled: {}", postDTO.getTitle());
        webClient.post()
                .uri("/create")
                .bodyValue(postDTO)
                .retrieve()
                .bodyToMono(PostDTO.class)
                .block();
    }

    public void deletePost(long id) {
        log.info("User requested to delete post with id: {}", id);
        webClient.delete()
                .uri("/delete/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void updateBike(long id, PostDTO postDTO) {
        log.info("User requested to update post with id: {}", id);
        webClient.put()
                .uri("/update/" + id)
                .bodyValue(postDTO)
                .retrieve()
                .bodyToMono(PostDTO.class)
                .block();
    }
}

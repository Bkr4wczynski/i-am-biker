package com.iambiker.webservice.webcontent.forum;

import com.iambiker.webservice.model.dto.personal.PostDTO;
import com.iambiker.webservice.security.AuthConnectionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ForumPageMvcController {
    private final ForumServiceConnectionService connectionService;
    private final AuthConnectionService authConnectionService;

    @GetMapping("/forum")
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallback")
    public String displayMainForumPage(@CookieValue(name = "token") String token, Model model) {
        List<PostDTO> posts = connectionService.getAllPosts();
        log.info("Posts fetched from api: {}", posts);
        model.addAttribute("posts", posts);
        return "forum/mainForumPage";
    }

    @GetMapping("/display-post")
    public String displayPost(@RequestParam int id, Model model, HttpServletRequest request) {
        PostDTO post = connectionService.getPostById(id);
        int authorId = post.getAuthorId();
        String authorName = authConnectionService.getUsernameById(authorId, request);
        int currentId = -1;
        try {
            currentId = authConnectionService.getUserDetails(request).getUser_id();
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("isUserAnAuthor", currentId==authorId);
        model.addAttribute("post", post);
        model.addAttribute("author", authorName);
        return "forum/post";
    }

    public String fallback(@CookieValue(name = "token") String token, Model model, Throwable throwable) {
        List<PostDTO> postDTOS = new ArrayList<>();
        model.addAttribute("posts", postDTOS);
        log.error("Failed to load forum!");
        return "forum/mainForumPage";
    }
}

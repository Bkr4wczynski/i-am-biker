package com.iambiker.webservice.webcontent.forum;

import com.iambiker.webservice.model.dto.personal.PostDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/forum")
@Slf4j
public class ForumController {

    @GetMapping
    @CircuitBreaker(name = "defaultCircuitBreaker", fallbackMethod = "fallback")
    public String displayMainForumPage(Model model) {
        List<PostDTO> postDTOS = new ArrayList<>();
        postDTOS.add(new PostDTO(1, "author1", "test", "lorem ipsum", "none", null, LocalDateTime.now(), LocalDateTime.now()));
        model.addAttribute("posts", postDTOS);
        return "forum/mainForumPage";
    }

    @GetMapping("/post")
    public String displayPost(@RequestParam int id, Model model) {
        PostDTO post = new PostDTO(1,"author1", "test", "lorem ipsum gsrhiuajgszkiaugheasikugh euihg uiwerhgruieahg kiusehgj reikuahygki", "none", null, LocalDateTime.now(), LocalDateTime.now());
        model.addAttribute("post", post);
        return "forum/post";
    }

    public String fallback(Model model, Throwable throwable) {
        List<PostDTO> postDTOS = new ArrayList<>();
        model.addAttribute("posts", postDTOS);
        log.error("Failed to load forum!");
        return "forum/mainForumPage";
    }
}

package com.iambiker.webservice.webcontent.forum;

import com.iambiker.webservice.model.dto.personal.PostDTO;
import com.iambiker.webservice.security.AuthConnectionService;
import com.iambiker.webservice.util.RedirectManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ForumFormMvcController {
    private final AuthConnectionService authConnectionService;
    private final ForumServiceConnectionService connectionService;
    private final RedirectManager redirectManager;

    @GetMapping("/delete-post")
    public String displayPostDeletePage(Model model, @RequestParam int id) {
        model.addAttribute("id", id);
        return "forum/deleteConfirm";
    }

    @DeleteMapping("/delete-post")
    public String deletePost(@RequestParam int id) {
        connectionService.deletePost(id);
        return redirectManager.redirect("/web/forum");
    }

    @GetMapping("/update-post")
    public String displayPostUpdatePage(Model model, @RequestParam int id) {
        PostDTO postDTO = connectionService.getPostById(id);
        model.addAttribute("post", postDTO);
        model.addAttribute("allTags", Tags.values());
        return "forum/update";
    }

    @PutMapping("/update-post")
    public String updatePost(@ModelAttribute("post") PostDTO postDTO) {
        postDTO.setUpdatedAt(LocalDateTime.now());
        log.info("In update request: {}", postDTO);
        connectionService.updateBike(postDTO.getId(), postDTO);
        return redirectManager.redirect("/web/display-post?id="+postDTO.getId());
    }

    @GetMapping("/create-post")
    public String displayPostCreatingPage(HttpServletRequest request, Model model) {
        PostDTO postDTO = new PostDTO();
        model.addAttribute("post", postDTO);
        model.addAttribute("allTags", Tags.values());
        return "forum/newPost";
    }

    @PostMapping("/create-post")
    public String createPost(HttpServletRequest request, @ModelAttribute("post") PostDTO postDTO) {
        int id;
        try {
            id = authConnectionService.getUserDetails(request).getUser_id();
            log.info("Successfully fetched user id!");
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
        postDTO.setAuthorId(id);
        postDTO.setCreatedAt(LocalDateTime.now());
        postDTO.setUpdatedAt(LocalDateTime.now());
        log.info("User requested to create post: {}", postDTO);
        connectionService.createPost(postDTO);
        return redirectManager.redirect("/web/forum");
    }
}

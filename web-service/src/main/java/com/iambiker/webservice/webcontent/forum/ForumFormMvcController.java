package com.iambiker.webservice.webcontent.forum;

import com.iambiker.webservice.model.dto.personal.PostDTO;
import com.iambiker.webservice.security.AuthConnectionService;
import com.iambiker.webservice.util.RedirectManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ForumFormMvcController {
    private final AuthConnectionService authConnectionService;
    private final ForumServiceConnectionService connectionService;
    private final RedirectManager redirectManager;

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

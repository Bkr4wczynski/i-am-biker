package com.iambiker.webservice.web_service.controller;

import com.iambiker.webservice.model.dto.authentication.UserDetailsDTO;
import com.iambiker.webservice.model.dto.personal.PostDTO;
import com.iambiker.webservice.security.AuthConnectionService;
import com.iambiker.webservice.webcontent.forum.ForumPageMvcController;
import com.iambiker.webservice.webcontent.forum.ForumServiceConnectionService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.iambiker.webservice.model.enums.Tags.NEWS;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(ForumPageMvcController.class)
public class ForumPageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ForumServiceConnectionService connectionService;

    @MockitoBean
    private AuthConnectionService authConnectionService;

    @Test
    void shouldDisplayMainForumPage() throws Exception {
        List<PostDTO> mockList = List.of(new PostDTO());
        Mockito.when(connectionService.getAllPosts())
                .thenReturn(mockList);

        mockMvc.perform(get("/forum").cookie(new Cookie("token", "fake-jwt-token")))
                .andExpect(status().isOk())
                .andExpect(view().name("forum/mainForumPage"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    void shouldDisplayPost() throws Exception {
        PostDTO postStub = new PostDTO(1, 1, "title", "content", "category", List.of(NEWS.name()), LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(connectionService.getPostById(anyLong()))
                .thenReturn(postStub);
        Mockito.when(authConnectionService.getUsernameById(anyInt(), any()))
                .thenReturn("author");
        Mockito.when(authConnectionService.getUserDetails(any()))
                .thenReturn(new UserDetailsDTO(1, "username", LocalDate.now(), LocalDate.now()));


        mockMvc.perform(get("/display-post").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("forum/post"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attributeExists("isUserAnAuthor"));
    }

}

package com.iambiker.webservice.web_service.controller.integration;

import com.iambiker.webservice.webcontent.quiz.QuizController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuizController.class)
public class QuizPageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldDisplayQuizPage() throws Exception {
        mockMvc.perform(get("/quiz"))
                .andExpect(status().isOk())
                .andExpect(view().name("content/quiz"));
    }
}

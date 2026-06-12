package com.iambiker.webservice.web_service.controller.integration;

import com.iambiker.webservice.webcontent.quiz.QuizController;
import com.iambiker.webservice.webcontent.tips.SafetyTipsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(SafetyTipsController.class)
public class TipsPageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldDisplayTipsPage() throws Exception {
        mockMvc.perform(get("/safety-tips"))
                .andExpect(status().isOk())
                .andExpect(view().name("content/safetyTips"));
    }
}

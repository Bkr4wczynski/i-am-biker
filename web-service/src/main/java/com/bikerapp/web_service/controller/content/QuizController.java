package com.bikerapp.web_service.controller.content;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/quiz")
public class QuizController {

    @GetMapping
    public String displayQuiz() {
        return "content/quiz";
    }


}

package com.bikerapp.web_service.controller.content;

import com.bikerapp.web_service.model.content.Answer;
import com.bikerapp.web_service.model.content.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {
    private int[] scores = {0, 0, 0};
    private List<Question> questions = new ArrayList<>();
    private void initializeQuestions() {
        questions.add(new Question("Where do you usually ride?",
                List.of(new Answer("city", 0), new Answer("explore new roads", 1),
                        new Answer("Off-road", 2), new Answer("Can't decide", -1))
                )
        );
    }

    @GetMapping("/quiz")
    public String displayQuiz(Model model) {
        initializeQuestions();
        model.addAttribute("question", questions.get(0));
        return "content/quiz";
    }

    @PostMapping("/process-question")
    public void processQuestion() {
        System.out.println("Question asked");
    }
}

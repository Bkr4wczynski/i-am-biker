package com.bikerapp.web_service.model.content;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Question {
    private String title;
    private List<Answer> answers;
}

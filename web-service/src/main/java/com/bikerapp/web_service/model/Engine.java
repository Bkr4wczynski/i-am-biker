package com.bikerapp.web_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Engine {
    private int capacity;
    private double horsepower;
    private String type;
}

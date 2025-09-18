package com.bikerapp.web_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bike {
    private int id;
    private String model;
    private LocalDate registry_date;
    private int mileage;
    private Engine engine;


}

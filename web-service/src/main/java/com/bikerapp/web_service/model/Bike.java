package com.bikerapp.web_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "bike")
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String model;

    private LocalDate registry_date;

    private int mileage;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @Override
    public String toString() {
        return model + " registered at " + registry_date + ", mileage = " + mileage+", engine - " + engine;
    }
}

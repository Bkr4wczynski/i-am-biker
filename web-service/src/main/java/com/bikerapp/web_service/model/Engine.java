package com.bikerapp.web_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(0)
    private int capacity;

    @Min(0)
    private double horsepower;

    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @OneToOne(mappedBy = "engine", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Bike bike;

    @Override
    public String toString() {
        return "id: [" + id + "] " + capacity + "ccm, " + horsepower + " horsepower " + engineType + " engine";
    }
}

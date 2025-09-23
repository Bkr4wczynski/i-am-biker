package com.bikerapp.web_service.model;

import jakarta.persistence.*;
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

    private int capacity;

    private double horsepower;

    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @OneToMany(mappedBy = "engine", fetch = FetchType.EAGER)
    private List<Bike> bikes;

    @Override
    public String toString() {
        return capacity + " ccm, " + horsepower + " horsepower " + engineType + " engine";
    }
}

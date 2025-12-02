package com.iambiker.bikesservice.database.entity;

import com.iambiker.bikesservice.model.enums.EngineType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(mappedBy = "engine", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Bike bike;

}

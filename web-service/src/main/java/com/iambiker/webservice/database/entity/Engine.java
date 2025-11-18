package com.iambiker.webservice.database.entity;

import com.iambiker.webservice.model.enums.EngineType;
import jakarta.persistence.*;
import lombok.*;

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

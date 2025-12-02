package com.iambiker.webservice.model.dto.personal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BikeDTO {
    private int id;

    private int user_id;

    private String model;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate registry_date;

    @Min(0)
    private int mileage;

    private EngineDTO engine;

    @Override
    public String toString() {
        return "id:" + id + " " + model + " registered at " + registry_date + ", mileage = " + mileage+", engine - " + engine;
    }

}

package com.iambiker.bikesservice.model.dto;

import com.iambiker.bikesservice.model.enums.EngineType;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EngineDTO {
    private int id;

    @Min(0)
    private int capacity;

    @Min(0)
    private double horsepower;

    private EngineType engineType;

    @Override
    public String toString() {
        return "id: [" + id + "] " + capacity + "ccm, " + horsepower + " horsepower " + engineType + " engine";
    }
}

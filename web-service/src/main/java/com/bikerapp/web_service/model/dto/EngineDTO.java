package com.bikerapp.web_service.model.dto;

import com.bikerapp.web_service.dao.entity.Engine;
import com.bikerapp.web_service.model.EngineType;
import jakarta.validation.constraints.Min;
import lombok.*;

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

    public static EngineDTO toDto(Engine engine) {
        EngineDTO dto = new EngineDTO();
        dto.setId(engine.getId());
        dto.setCapacity(engine.getCapacity());
        dto.setHorsepower(engine.getHorsepower());
        dto.setEngineType(engine.getEngineType());
        return dto;
    }

    public static Engine toEntity(EngineDTO dto) {
        Engine engine = new Engine();
        engine.setId(dto.getId());
        engine.setCapacity(dto.getCapacity());
        engine.setHorsepower(dto.getHorsepower());
        engine.setEngineType(dto.getEngineType());
        return engine;
    }
}

package com.bikerapp.web_service.model.dto;

import com.bikerapp.web_service.dao.entity.Bike;
import com.bikerapp.web_service.dao.entity.Engine;
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

    private Engine engine;

    @Override
    public String toString() {
        return "id:" + id + " " + model + " registered at " + registry_date + ", mileage = " + mileage+", engine - " + engine;
    }

    public static BikeDTO toDTO(Bike bike) {
        BikeDTO dto = new BikeDTO();
        dto.setUser_id(bike.getUserId());
        dto.setId(bike.getId());
        dto.setMileage(bike.getMileage());
        dto.setEngine(bike.getEngine());
        dto.setModel(bike.getModel());
        dto.setRegistry_date(bike.getRegistry_date());
        return dto;
    }

    public static Bike toEntity(BikeDTO dto) {
        Bike bike = new Bike();
        bike.setUserId(dto.getUser_id());
        bike.setId(dto.getId());
        bike.setMileage(dto.getMileage());
        bike.setEngine(dto.getEngine());
        bike.setModel(dto.getModel());
        bike.setRegistry_date(dto.getRegistry_date());
        return bike;
    }
}

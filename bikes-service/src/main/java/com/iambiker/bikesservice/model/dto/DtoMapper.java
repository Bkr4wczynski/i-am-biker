package com.iambiker.bikesservice.model.dto;

import com.iambiker.bikesservice.database.entity.Bike;
import com.iambiker.bikesservice.database.entity.Engine;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public BikeDTO bikeToDto(Bike bike) {
        BikeDTO bikeDTO = new BikeDTO();
        bikeDTO.setUser_id(bike.getUserId());
        bikeDTO.setId(bike.getId());
        bikeDTO.setMileage(bike.getMileage());
        bikeDTO.setEngine(engineToDto(bike.getEngine()));
        bikeDTO.setModel(bike.getModel());
        bikeDTO.setRegistry_date(bike.getRegistry_date());
        return bikeDTO;
    }

    public Bike dtoToBike(BikeDTO bikeDTO) {
        Bike bike = new Bike();
        bike.setUserId(bikeDTO.getUser_id());
        bike.setId(bikeDTO.getId());
        bike.setMileage(bikeDTO.getMileage());
        bike.setEngine(dtoToEngine(bikeDTO.getEngine()));
        bike.setModel(bikeDTO.getModel());
        bike.setRegistry_date(bikeDTO.getRegistry_date());
        return bike;
    }

    public EngineDTO engineToDto(Engine engine) {
        EngineDTO engineDTO = new EngineDTO();
        engineDTO.setId(engine.getId());
        engineDTO.setCapacity(engine.getCapacity());
        engineDTO.setHorsepower(engine.getHorsepower());
        engineDTO.setEngineType(engine.getEngineType());
        return engineDTO;
    }

    public Engine dtoToEngine(EngineDTO engineDTO) {
        Engine engine = new Engine();
        engine.setId(engineDTO.getId());
        engine.setCapacity(engineDTO.getCapacity());
        engine.setHorsepower(engineDTO.getHorsepower());
        engine.setEngineType(engineDTO.getEngineType());
        return engine;
    }
}

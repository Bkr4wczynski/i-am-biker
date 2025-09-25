package com.bikerapp.web_service.service;

import com.bikerapp.web_service.dao.BikesRepository;
import com.bikerapp.web_service.dao.EngineRepository;
import com.bikerapp.web_service.model.Bike;
import com.bikerapp.web_service.model.Engine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BikesService {
    private final BikesRepository bikesRepository;
    private final EngineRepository engineRepository;

    public List<Bike> getBikes() {
        return bikesRepository.findAll();
    }

    public void saveBike(Bike bike) {
        bikesRepository.save(bike);
    }

    @Transactional
    public void deleteBike(int id) {
        bikesRepository.deleteById(id);
    }

    public Optional<Bike> findBike(int id) {
        return bikesRepository.findById(id);
    }

    @Transactional
    public void updateBike(Bike updated) {
        Bike existingBike = findBike(updated.getId()).get();
        Engine existingEngine = existingBike.getEngine();
        Engine updatedEngine = updated.getEngine();

        existingEngine.setEngineType(updatedEngine.getEngineType());
        existingEngine.setCapacity(updatedEngine.getCapacity());
        existingEngine.setHorsepower(updatedEngine.getHorsepower());

        existingBike.setModel(updated.getModel());
        existingBike.setMileage(updated.getMileage());
        existingBike.setRegistry_date(updated.getRegistry_date());

        saveBike(existingBike);
    }

}

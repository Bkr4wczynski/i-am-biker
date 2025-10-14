package com.bikerapp.web_service.service;

import com.bikerapp.web_service.dao.repository.BikesRepository;
import com.bikerapp.web_service.dao.repository.EngineRepository;
import com.bikerapp.web_service.dao.entity.Bike;
import com.bikerapp.web_service.dao.entity.Engine;
import com.bikerapp.web_service.model.dto.BikeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BikesService {
    private final BikesRepository bikesRepository;
    private final EngineRepository engineRepository;

    public List<BikeDTO> getBikes() {
        log.info("List of bikes displayed");
        return bikesRepository.findAll().stream()
                .map(BikeDTO::toDTO)
                .collect(Collectors.toList());
    }


    public void saveBike(BikeDTO dto) {
        log.info("User saved bike: {}", dto.getModel());
        Bike bike = BikeDTO.toEntity(dto);
        bikesRepository.save(bike);
    }

    @Transactional

    public void deleteBike(int id) {
        log.info("User deleted bike with id: {}", id);
        bikesRepository.deleteById(id);
    }

    public Optional<BikeDTO> findBike(int id) {
        log.info("Searching for bike with id: {}", id);
        return bikesRepository.findById(id).map(BikeDTO::toDTO);
    }

    @Transactional
    public void updateBike(BikeDTO bikeDTO) {
        log.info("User requests for updating his {}", bikeDTO.getModel());
        Bike updated = BikeDTO.toEntity(bikeDTO);
        Bike existingBike = bikesRepository.findById(updated.getId()).get();
        Engine existingEngine = existingBike.getEngine();
        Engine updatedEngine = updated.getEngine();

        existingEngine.setEngineType(updatedEngine.getEngineType());
        existingEngine.setCapacity(updatedEngine.getCapacity());
        existingEngine.setHorsepower(updatedEngine.getHorsepower());

        existingBike.setModel(updated.getModel());
        existingBike.setMileage(updated.getMileage());
        existingBike.setRegistry_date(updated.getRegistry_date());

        bikesRepository.save(existingBike);
    }

}

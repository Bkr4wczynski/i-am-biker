package com.iambiker.bikesservice.rest;

import com.iambiker.bikesservice.database.entity.Bike;
import com.iambiker.bikesservice.database.entity.Engine;
import com.iambiker.bikesservice.database.repository.BikesRepository;
import com.iambiker.bikesservice.model.dto.BikeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BikesService {
    private final BikesRepository bikesRepository;

    public List<BikeDTO> getBikes(int userId) {
        return bikesRepository.findByUserId(userId).stream()
                .map(BikeDTO::toDTO)
                .collect(Collectors.toList());
    }

    public Bike saveBike(BikeDTO dto) {
        Bike bike = BikeDTO.toEntity(dto);
        return bikesRepository.save(bike);
    }

    @Transactional
    public void deleteBike(int id) {
        bikesRepository.deleteById(id);
    }

    public Optional<BikeDTO> findBike(int id) {
        return bikesRepository.findById(id).map(BikeDTO::toDTO);
    }

    @Transactional
    public Bike updateBike(BikeDTO bikeDTO) {
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

        return bikesRepository.save(existingBike);
    }

}

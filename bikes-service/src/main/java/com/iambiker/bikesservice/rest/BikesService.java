package com.iambiker.bikesservice.rest;

import com.iambiker.bikesservice.database.entity.Bike;
import com.iambiker.bikesservice.database.entity.Engine;
import com.iambiker.bikesservice.database.repository.BikesRepository;
import com.iambiker.bikesservice.model.dto.BikeDTO;
import com.iambiker.bikesservice.model.dto.DtoMapper;
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
    private final DtoMapper dtoMapper;

    public List<BikeDTO> getBikes(int userId) {
        log.info("User fetches list of bikes");
        return bikesRepository.findByUserId(userId).stream()
                .map(dtoMapper::bikeToDto)
                .collect(Collectors.toList());
    }

    public Bike saveBike(BikeDTO dto) {
        log.info("User saved bike: {}", dto.getModel());
        Bike bike = dtoMapper.dtoToBike(dto);
        return bikesRepository.save(bike);
    }

    @Transactional
    public void deleteBike(int id) {
        log.info("User deleted bike with id: {}", id);
        bikesRepository.deleteById(id);
    }

    public Optional<BikeDTO> findBike(int id) {
        log.info("Searching for bike with id: {}", id);
        return bikesRepository.findById(id).map(dtoMapper::bikeToDto);
    }

    @Transactional
    public Bike updateBike(BikeDTO bikeDTO) {
        log.info("User requests for updating his {}", bikeDTO.getModel());

        Bike updated = dtoMapper.dtoToBike(bikeDTO);
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

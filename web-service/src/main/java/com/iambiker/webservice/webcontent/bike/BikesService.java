package com.iambiker.webservice.webcontent.bike;

import com.iambiker.webservice.database.repository.BikesRepository;
import com.iambiker.webservice.database.entity.Bike;
import com.iambiker.webservice.database.entity.Engine;
import com.iambiker.webservice.model.dto.personal.BikeDTO;
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

    public List<BikeDTO> getBikes(int userId) {
        log.info("List of bikes displayed");
        return bikesRepository.findByUserId(userId).stream()
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

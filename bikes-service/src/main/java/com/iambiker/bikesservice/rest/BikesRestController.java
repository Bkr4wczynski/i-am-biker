package com.iambiker.bikesservice.rest;

import com.iambiker.bikesservice.model.dto.BikeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class BikesRestController {
    private final BikesService bikesService;

    @GetMapping("/get-bikes")
    public ResponseEntity<List<BikeDTO>> getBikes(@RequestParam int userId) {
        log.info("User fetches list of bikes");
        return ResponseEntity.ok(bikesService.getBikes(userId));

    }

    @PostMapping("/save-bike")
    public ResponseEntity<BikeDTO> saveBike(@RequestBody BikeDTO dto) {
        log.info("User saved bike: {}", dto.getModel());
        return ResponseEntity.ok(BikeDTO.toDTO(bikesService.saveBike(dto)));
    }

    @Transactional
    @DeleteMapping("/delete-bike")
    public ResponseEntity<Void> deleteBike(@RequestParam int id) {
        log.info("User deleted bike with id: {}", id);
        bikesService.deleteBike(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-bike")
    public ResponseEntity<Optional<BikeDTO>> findBike(@RequestParam int id) {
        log.info("Searching for bike with id: {}", id);
        return ResponseEntity.ok(bikesService.findBike(id));
    }

    @Transactional
    @PutMapping("/update-bike")
    public ResponseEntity<BikeDTO> updateBike(@RequestBody BikeDTO bikeDTO) {
        log.info("User requests for updating his {}", bikeDTO.getModel());
        return ResponseEntity.ok(BikeDTO.toDTO(bikesService.updateBike(bikeDTO)));
    }


}

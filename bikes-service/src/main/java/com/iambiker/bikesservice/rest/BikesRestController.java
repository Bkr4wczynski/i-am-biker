package com.iambiker.bikesservice.rest;

import com.iambiker.bikesservice.model.dto.BikeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BikesRestController {
    private final BikesService bikesService;

    @GetMapping("/get-bikes")
    public ResponseEntity<List<BikeDTO>> getBikes(@RequestParam int userId) {
        return ResponseEntity.ok(bikesService.getBikes(userId));
    }

    @PostMapping("/save-bike")
    public ResponseEntity<BikeDTO> saveBike(@RequestBody BikeDTO dto) {
        return ResponseEntity.ok(BikeDTO.toDTO(bikesService.saveBike(dto)));
    }

    @DeleteMapping("/delete-bike")
    public ResponseEntity<Void> deleteBike(@RequestParam int id) {
        bikesService.deleteBike(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/find-bike")
    public ResponseEntity<Optional<BikeDTO>> findBike(@RequestParam int id) {
        return ResponseEntity.ok(bikesService.findBike(id));
    }

    @PutMapping("/update-bike")
    public ResponseEntity<BikeDTO> updateBike(@RequestBody BikeDTO bikeDTO) {
        return ResponseEntity.ok(BikeDTO.toDTO(bikesService.updateBike(bikeDTO)));
    }


}

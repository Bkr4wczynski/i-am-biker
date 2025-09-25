package com.bikerapp.web_service.service;

import com.bikerapp.web_service.dao.BikesRepository;
import com.bikerapp.web_service.model.Bike;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BikesService {
    final BikesRepository bikesRepository;

    public List<Bike> getBikes() {
        return bikesRepository.findAll();
    }

    public void saveBike(Bike bike) {
        bikesRepository.save(bike);
    }

    public void deleteBike(int id) {
        bikesRepository.deleteById(id);
    }

    public Optional<Bike> findBike(int id) {
        return bikesRepository.findById(id);
    }

}

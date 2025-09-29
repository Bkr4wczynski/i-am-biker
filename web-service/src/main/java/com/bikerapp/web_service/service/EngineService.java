package com.bikerapp.web_service.service;

import com.bikerapp.web_service.dao.repository.EngineRepository;
import com.bikerapp.web_service.dao.entity.Engine;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EngineService {
    private final EngineRepository engineRepository;

    public List<Engine> getEngines() {
        return engineRepository.findAll();
    }
}

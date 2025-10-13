package com.bikerapp.web_service.service;

import com.bikerapp.web_service.dao.repository.EngineRepository;
import com.bikerapp.web_service.dao.entity.Engine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EngineService {
    private final EngineRepository engineRepository;

    public List<Engine> getEngines() {
        log.info("Searching for engines");
        return engineRepository.findAll();
    }
}

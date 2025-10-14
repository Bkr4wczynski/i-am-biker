package com.bikerapp.web_service.service;

import com.bikerapp.web_service.dao.repository.EngineRepository;
import com.bikerapp.web_service.dao.entity.Engine;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class EngineService {
    private final EngineRepository engineRepository;

    @RateLimiter(name = "generalLimiter")
    @TimeLimiter(name = "generalLimiter")
    @Retry(name = "generalRetry")
    public List<Engine> getEngines() {
        log.info("Searching for engines");
        return engineRepository.findAll();
    }
}

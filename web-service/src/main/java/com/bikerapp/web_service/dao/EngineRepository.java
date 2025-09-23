package com.bikerapp.web_service.dao;

import com.bikerapp.web_service.model.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Integer> {

}

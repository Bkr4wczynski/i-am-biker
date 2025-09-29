package com.bikerapp.web_service.dao.repository;

import com.bikerapp.web_service.dao.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Integer> {

}

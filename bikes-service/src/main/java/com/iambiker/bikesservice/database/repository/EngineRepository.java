package com.iambiker.bikesservice.database.repository;

import com.iambiker.bikesservice.database.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Integer> {

}

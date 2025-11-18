package com.iambiker.webservice.database.repository;


import com.iambiker.webservice.database.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BikesRepository extends JpaRepository<Bike, Integer> {
    List<Bike> findByUserId(int userId);
}

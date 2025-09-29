package com.bikerapp.web_service.dao.repository;


import com.bikerapp.web_service.dao.entity.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BikesRepository extends JpaRepository<Bike, Integer> {

}

package com.bikerapp.web_service.dao;

import com.bikerapp.web_service.model.Bike;
import com.bikerapp.web_service.model.Engine;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BikesRepository {
    public List<Bike> getBikes() {
        Bike kawasakiStub = new Bike(1, "Kawasaki Z125", LocalDate.of(2020, 1, 1), 10000,
                new Engine(125, 15, "4-stroke 1-cylinder"));
        Bike hondaStub = new Bike(2, "Honda CBR1000RR-R", LocalDate.of(2020, 1, 1), 10000,
                new Engine(1000, 218, "4-stroke 4-cylinder"));
        Bike ktmStub = new Bike(3, "KTM SXF 150", LocalDate.of(2020, 1, 1), 10000,
                new Engine(150, 30, "2-stroke 1-cylinder"));
        List<Bike> stubList = new ArrayList<>();
        stubList.add(kawasakiStub);
        stubList.add(hondaStub);
        stubList.add(ktmStub);
        return stubList;
    }
}

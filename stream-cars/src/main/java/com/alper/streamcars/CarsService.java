package com.alper.streamcars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarsService {
    @Autowired
    private CarRepository repository;

    public List<Car> getAllCars(){
        return this.repository.findAll();
    }
}

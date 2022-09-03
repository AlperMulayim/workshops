package com.alper.carsnotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private  CarsService carsService;

    @GetMapping
    public List<Car> getAllCars(){
        return  carsService.getAllCars().subList(0,100);
    }
}

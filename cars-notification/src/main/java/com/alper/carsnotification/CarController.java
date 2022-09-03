package com.alper.carsnotification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private  CarsService carsService;

    @Autowired
    private  SimpMessagingTemplate notTemplate;


    @GetMapping
    public List<Car> getAllCars(){
        return  carsService.getAllCars().subList(0,100);
    }

    @PostMapping
    public void addCar(@RequestBody Car car){
        carsService.addCar(car);
        //when new car added sent notifications.
        notTemplate.convertAndSend("/notifications/subscribe",car);
    }
}

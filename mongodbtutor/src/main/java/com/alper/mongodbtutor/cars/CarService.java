package com.alper.mongodbtutor.cars;

import com.alper.mongodbtutor.externalapi.CarDto;
import com.alper.mongodbtutor.mapper.CarMapper;
import com.alper.mongodbtutor.mapper.EntityDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarsRepository repository;

    public List<CarDto> getAllCars(){
        EntityDtoMapper<Car, CarDto> mapper = new CarMapper();

        List<Car> cars = this.repository.findAll();

        List<CarDto> carDtos = cars.stream()
                .map(car -> mapper.toDto(car))
                .collect(Collectors.toList());

        return carDtos;
    }
}
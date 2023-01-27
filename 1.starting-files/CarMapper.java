package com.alper.mongodbtutor.mapper;

import com.alper.mongodbtutor.cars.Car;
import com.alper.mongodbtutor.externalapi.CarDto;

public class CarMapper  implements  EntityDtoMapper<Car, CarDto> {
    @Override
    public Car toEntity(CarDto carDto) {
        return  Car.builder()
                .id(carDto.getId())
                .city(carDto.getCity())
                .country(carDto.getCountry())
                .credittype(carDto.getCredittype())
                .fname(carDto.getFname())
                .manufactorer(carDto.getManufactorer())
                .model(carDto.getModel())
                .price(carDto.getPrice())
                .soldyear(carDto.getSoldyear())
                .build();
    }

    @Override
    public CarDto toDto(Car car) {
      return   CarDto.builder()
                .id(car.getId())
                .city(car.getCity())
                .country(car.getCountry())
                .credittype(car.getCredittype())
                .fname(car.getFname())
                .manufactorer(car.getManufactorer())
                .model(car.getModel())
                .price(car.getPrice())
                .soldyear(car.getSoldyear())
                .build();
    }
}

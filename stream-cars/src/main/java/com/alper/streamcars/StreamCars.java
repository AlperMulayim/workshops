package com.alper.streamcars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StreamCars  {

    @Autowired
    CarsService carsService;

    public List<Car> findByCustomerName(String fname){
        return carsService.getAllCars().stream()
                .filter(car -> car.getFname().equals(fname))
                .collect(Collectors.toList());
    }
    public Map<String,List<Car>> groupByCountry(){
        return  carsService.getAllCars().stream()
                .collect(Collectors.groupingBy(Car::getCountry));

    }

    public List<Car> filterByYears(Integer start , Integer end){
        return  carsService.getAllCars().stream()
                .filter(car -> car.getSoldyear() > start)
                .filter((car->car.getSoldyear() < end))
                .collect(Collectors.toList());
    }

    public Map<String,Integer> getContriesCarTotal(){
        Map<String, List<Car>> carsbycounrty=
          carsService.getAllCars().stream()
                .collect(Collectors.groupingBy(Car::getCountry));

        Map<String, Integer> carsCountsByCountry = new HashMap<>();

        carsbycounrty.keySet().forEach(key->{
            carsCountsByCountry.put(key,carsbycounrty.get(key).size());
        });
        return carsCountsByCountry;
    }

    public Integer totalPriceOfSystem(){
        return  carsService.getAllCars().stream()
                .map(Car::getPrice)
                .reduce(0,Integer::sum);
    }
}

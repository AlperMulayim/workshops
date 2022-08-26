package com.alper.streamcars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public Set<String> findSystemCountries(){

        return carsService.getAllCars().stream()
                .map(Car::getCountry)
                .collect(Collectors.toSet());
    }

    public Map<String, Integer> customersArea(){
        Map<String, Integer> customersArea = new HashMap<>();
        enum CustomersArea { EUROPE, ASIA }

        List<Car> allCars = carsService.getAllCars();

        Map<CustomersArea, List<Car>>  carsByArea = allCars.stream()
                .collect(Collectors.groupingBy(car -> {
                    if(car.getCountry().equals("Turkey") || car.getCountry().equals("Russia")){
                        return  CustomersArea.ASIA;
                    }else{
                        return CustomersArea.EUROPE;
                    }
                }));

        customersArea.put("ASIA",carsByArea.get(CustomersArea.ASIA).size());
        customersArea.put("EUROPE",carsByArea.get(CustomersArea.EUROPE).size());

        return  customersArea;
    }

    public Map<String, String> getLocations(){
        Map<String,List<Car>> cars = new HashMap<>();
        Map<String, String> locations = new HashMap<>();

        cars = carsService.getAllCars().stream()
                .collect(Collectors.groupingBy(Car::getCountry));

        Set<String> keys = cars.keySet();

        Map<String, List<Car>> finalCars = cars;
        keys.forEach(key->{

            String ciites = finalCars.get(key).stream()
                    .map(Car::getCity)
                    .distinct()
                    .collect(Collectors.joining(", "));
            locations.put(key,ciites);

        });
        return locations;

    }

    public Integer maxPriceCar(){
        return carsService.getAllCars().stream()
                .map(Car::getPrice)
                .reduce(Integer::max).get();
    }
    public Set<Car> findMaxPricedCar(){
        Integer maxPrice =  carsService.getAllCars().stream()
                .map(Car::getPrice)
                .reduce(Integer::max).get();

        return  carsService.getAllCars().stream()
                .filter(car -> car.getPrice().equals(maxPrice)).collect(Collectors.toSet());

    }

    public Map<String, Integer> groupByCreditCardCarSelling(){

        Map<String, List<Car>> sellingsByCards;
        Map<String, Integer> creditCardsStat = new HashMap<>();

        sellingsByCards = carsService.getAllCars().stream()
                .collect(Collectors.groupingBy(Car::getCredittype));

        sellingsByCards.keySet().forEach(key->
                {
                       Integer numOfCars = sellingsByCards.get(key).size();
                       creditCardsStat.put(key,numOfCars);
                }
        );

     return creditCardsStat;

    }

    public SystemStatistic getStatus(){

        SystemStatistic statistic = SystemStatistic.builder()
                .countries(findSystemCountries())
                .totalPrice(totalPriceOfSystem())
                .totalCustomers(carsService.getAllCars().size())
                .customerArea(customersArea())
                .locations(getLocations())
                .maxPriceCar(maxPriceCar())
                .maxPricedCars(findMaxPricedCar())
                .creaditCardTypes(groupByCreditCardCarSelling())
                .build();

        return statistic;
    }



}

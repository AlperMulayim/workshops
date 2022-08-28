package com.alper.streamcars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.*;
import java.util.stream.Collector;
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
                .fordModels(getFordModelsWithCommaSeparated())
                .totalCustomers(carsService.getAllCars().size())
                .customerArea(customersArea())
                .locations(getLocations())
                .maxPriceCar(maxPriceCar())
                .maxPricedCars(findMaxPricedCar())
                .creaditCardTypes(groupByCreditCardCarSelling())
                .moreThan5LetterCitiesCount(getTotalNumberOfCitiesOfNamesLongerThanGivenNum())
                .kiaAndFordCars(findListOfCarsOnlyManufactorerKiaAndFord())
                .averageFordIncome(findAverageIncomeForFord())
                .fordSoldModelsYear(findEachCarModelAndSoldYearForMasterCardOperations())
                .groupCarsWithManFirst3Letter(groupTheCarsWithManufactorerFirst3Letters())
                .longestCityInSystem(findTheLongestCityNameInSystem())
                .build();

        return statistic;
    }

    public Integer getTotalNumberOfCitiesOfNamesLongerThanGivenNum(){
        List<Car> cars = carsService.getAllCars();
        return  cars.stream().map(Car::getCity).filter(s -> s.length() > 10).collect(Collectors.toList()).size();
    }

    public List<Car> findListOfCarsOnlyManufactorerKiaAndFord(){
        List<Car> cars = carsService.getAllCars();
        return  cars.stream()
                .filter(man->man.getManufactorer().equals("Kia") || man.getManufactorer().equals("Ford"))
                .collect(Collectors.toList());
    }

    public double findAverageIncomeForFord(){
        List<Car> cars = carsService.getAllCars();
        return  cars.stream().filter(c-> c.getManufactorer().equals("Ford"))
                .collect(Collectors.averagingDouble(Car::getPrice));

    }

    public String getFordModelsWithCommaSeparated(){
        List<Car> cars = carsService.getAllCars();

        return  cars.stream().filter(c-> c.getManufactorer().equals("Ford"))
                .map(Car::getModel)
                .distinct()
                .collect(Collectors.joining(", "));
    }

    public List<String> findEachCarModelAndSoldYearForMasterCardOperations(){
        List<Car> cars = carsService.getAllCars();

        return  cars.stream().filter(c->c.getCredittype().equals("mastercard"))
                .filter(car -> car.getManufactorer().equals("Ford"))
                .map(car -> String.format("%s - %s - %s",car.getCountry(),car.getModel(), car.getSoldyear()))
                .distinct()
                .collect(Collectors.toList());

    }

    public Map<String, List<Car>> groupTheCarsWithManufactorerFirst3Letters(){
        List<Car> cars = carsService.getAllCars();

        return cars.stream()
                .distinct()
                .collect(Collectors.groupingBy(c->
                 c.getCountry().substring(0,3).toUpperCase(Locale.ROOT)));

    }

    public String findTheLongestCityNameInSystem(){
        List<Car> cars = carsService.getAllCars();

        return  cars.stream()
                .map(Car::getCity)
                .sorted(Comparator.comparing(String::length).reversed()).findFirst().get();
    }

}

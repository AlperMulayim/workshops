package com.alper.streamcars;

import org.apache.logging.log4j.util.PropertySource;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarsController {

    @Autowired
    private CarsService service;

    @Autowired
    private StreamCars streamCars;

    @GetMapping
    public List<Car> getAllCars(){
        return  this.service.getAllCars();
    }

    @GetMapping("/filter")
    public List<Car> filter(@RequestParam(name = "by") String fieldName,
                            @RequestParam(name="data", required = false) String data,
                            @RequestParam(name = "start", required = false) Optional<Integer> startDate,
                            @RequestParam(name = "end", required = false) Optional<Integer> endDate
    ){
        List<Car> filteredCars = null;

        if(fieldName.equals("customer")){
            filteredCars=  streamCars.findByCustomerName(data);
        } else if(fieldName.equals("date")){
            if(startDate.isPresent() && endDate.isPresent()){
                Integer start = startDate.get();
                Integer end = endDate.get();
                filteredCars = streamCars.filterByYears(start,end);
            }
        }
        else{
            throw new NotYetImplementedException("Field Filter not implemented yet");
        }

        return  filteredCars;
    }

    @GetMapping("/countries")
    public Map<String,List<Car>> getCountriesCars(){
        return streamCars.groupByCountry();
    }

    @GetMapping("/countries/total")
    public Map<String,Integer> getCountriesCarsTotal(){
        return streamCars.getContriesCarTotal();
    }

    @GetMapping("/system/statistic")
    public SystemStatistic totalPriceOfSystem(){
        return streamCars.getStatus();
    }

    @GetMapping("v2/filter")
    public List<Car> filterCars(@RequestParam(name = "id", required = false) Optional<Integer> id,
                                @RequestParam(name = "startYear", required = false) Optional<Integer> startYear,
                                @RequestParam(name = "endYear", required = false) Optional<Integer> endYear,
                                @RequestParam(name = "price", required = false) Optional<Integer> price,
                                @RequestParam(name = "manufacturer", required = false) Optional<String> manufacturer,
                                @RequestParam(name = "country", required = false) Optional<String> country,
                                @RequestParam(name="orderBy", required = false) Optional<String> orderby,
                                @RequestParam(name = "order",required = false) Optional<String> order
    ) {
        List<Car> cars = service.getAllCars();
        Predicate<Car> countryPredicete = car -> car.getCountry().equals(country.get());

        if(id.isPresent()){
            cars =  cars.stream().filter(car -> car.getId().equals(id.get())).collect(Collectors.toList());
        }
        if(manufacturer.isPresent()){
            cars = cars.stream()
                    .filter(car -> car.getManufactorer().equals(manufacturer.get()))
                    .collect(Collectors.toList());
        }
        if(startYear.isPresent() && endYear.isPresent()){
            Integer start = startYear.get();
            Integer end = endYear.get();

            cars = cars.stream()
                    .filter(car -> car.getSoldyear() >= start)
                    .filter(car -> car.getSoldyear() <= end)
                    .collect(Collectors.toList());

        }
        if(price.isPresent()){
            cars =cars.stream()
                    .filter(car -> car.getPrice() < price.get())
                    .collect(Collectors.toList());
        }
        if(country.isPresent()){
            cars = cars.stream()
                    .filter(countryPredicete)
                    .collect(Collectors.toList());
        }

        if(orderby.isPresent() && order.isPresent()){
            String  orderBy = orderby.get();
            String orderType = order.get();

            if(orderBy.equals("soldyear")){
                if(orderType.equals("DESC")){
                    cars = cars.stream()
                            .sorted(Comparator.comparingInt(Car::getSoldyear))
                            .collect(Collectors.toList());
                }else{
                    cars = cars.stream()
                            .sorted(Comparator.comparingInt(Car::getSoldyear).reversed())
                            .collect(Collectors.toList());
                }
            }
        }
        return  cars;
    }

    @GetMapping("v3/filter")
    public List<Car> filterCarsV3(@RequestParam(name = "id", required = false) Optional<Integer> id,
                                @RequestParam(name = "startYear", required = false) Optional<Integer> startYear,
                                @RequestParam(name = "endYear", required = false) Optional<Integer> endYear,
                                @RequestParam(name = "price", required = false) Optional<Integer> price,
                                @RequestParam(name = "manufacturer", required = false) Optional<String> manufacturer,
                                @RequestParam(name = "country", required = false) Optional<String> country
    ){

        Predicate<Car> countryPred = car -> car.getCountry().equals(country.get());
        Predicate<Car> manufactorerPred = car -> car.getManufactorer().equals(manufacturer.get());

        List<Predicate<Car>> carFilter = Arrays.asList(countryPred,manufactorerPred);
        List<Car> cars = service.getAllCars();

        cars = cars.stream().filter(carFilter.stream()
                        .reduce(predicate-> true, Predicate::and))
                .collect(Collectors.toList());

        return cars;

    }

}


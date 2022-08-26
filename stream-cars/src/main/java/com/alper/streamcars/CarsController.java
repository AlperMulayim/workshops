package com.alper.streamcars;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

}

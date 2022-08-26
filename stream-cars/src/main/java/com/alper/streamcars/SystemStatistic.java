package com.alper.streamcars;

import lombok.Builder;
import lombok.Data;

import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class SystemStatistic {
    private Integer totalPrice;
    private Set<String> countries;
    private Integer totalCustomers;
    private Map<String,Integer> customerArea;
    private Map<String, String> locations;
    private Integer maxPriceCar;
    private Set<Car> maxPricedCars;
    private Map<String, Integer> creaditCardTypes;
}

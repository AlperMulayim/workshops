package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public List<String> getProductsWithEvenCharactersWithFiredMethod(){
        List<String> names = repository.getProductNames();
        List<String> results  = new ArrayList<>();

        for(String  name : names){
            if(name.length() % 2 == 0){
                results.add(name);
                repository.addProductFired(name); //will used to check if called.
            }
        }
        return  results;
    }
    public List<String> getProductsWithEvenChars(){
        List<String> names = repository.getProductNames();
        return names.stream().filter(name-> name.length() % 2 == 0 ).collect(Collectors.toList());
    }
}

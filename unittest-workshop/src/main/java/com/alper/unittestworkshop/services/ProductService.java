package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public List<String> getProductsWithEvenCharacters(){
        List<String> names = repository.getProductNames();
        List<String> results  = new ArrayList<>();

        for(String  name : names){
            if(name.length() % 2 == 0){
                results.add(name);
            }
        }
        return  results;
    }
}

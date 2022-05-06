package com.alper.unittestworkshop.repositories;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepository {
   public List<String> getProductNames(){
        return Arrays.asList("aaaa","baaa","bbbbb","ccccc");
   }
}

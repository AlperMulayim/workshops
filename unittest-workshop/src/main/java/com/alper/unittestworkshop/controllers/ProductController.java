package com.alper.unittestworkshop.controllers;

import com.alper.unittestworkshop.CartDetail;
import com.alper.unittestworkshop.Product;
import com.alper.unittestworkshop.campaing.Total25PriceCampaing;
import com.alper.unittestworkshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class ProductController {


    @GetMapping("product/{name}")
    public Product getProduct(@PathVariable String name){
        Product product = Product.builder().name(name).price(10.0).build();
        return product;
    }

    @GetMapping("products")
    public List<Product> getAllProducts(){
        Product product1 = Product.builder().name("Biscuit").price(10.0).build();
        Product product2 = Product.builder().name("Ice Cream").price(10.0).build();

        List<Product> products = new LinkedList<>();
        products.add(product1);
        products.add(product2);

        return products;

    }
}

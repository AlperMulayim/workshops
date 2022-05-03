package com.alper.jpaworkshop.product.controllers;

import com.alper.jpaworkshop.product.ProductCategory;
import com.alper.jpaworkshop.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
public class CategoriesController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductCategory> getCategories(){
        return productService.getAllCategories();
    }


}

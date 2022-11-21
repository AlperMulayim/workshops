package com.alper.jpaworkshop.product.controllers;

import com.alper.jpaworkshop.product.IProduct;
import com.alper.jpaworkshop.product.Product;
import com.alper.jpaworkshop.product.ProductCategory;
import com.alper.jpaworkshop.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts(@RequestParam(name = "cat") Optional<Integer> cat) {
        if (!cat.isEmpty()) {
            return  productService.findByCategory(cat.get());
        }
        return productService.getAll();
    }

    @GetMapping("/iproducts")
    public List<IProduct> getIProducts(){
        return  productService.getAllIProducts();
    }
}
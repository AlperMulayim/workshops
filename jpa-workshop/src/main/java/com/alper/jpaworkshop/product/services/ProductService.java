package com.alper.jpaworkshop.product.services;

import com.alper.jpaworkshop.product.IProduct;
import com.alper.jpaworkshop.product.Product;
import com.alper.jpaworkshop.product.ProductCategory;
import com.alper.jpaworkshop.product.repositories.CategoryRepository;
import com.alper.jpaworkshop.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAll(){
        return  this.productRepository.findAll();
    }

    public List<ProductCategory> getAllCategories(){
        return  this.categoryRepository.findAll();
    }

    public List<Product> findByCategory(Integer category){
        return productRepository.findByCategory(category);
    }
    public List<IProduct> getAllIProducts(){
        return  this.productRepository.getIProducts();
    }
}

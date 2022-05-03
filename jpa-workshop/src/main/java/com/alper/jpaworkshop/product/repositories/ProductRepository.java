package com.alper.jpaworkshop.product.repositories;

import com.alper.jpaworkshop.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends  JpaRepository<Product,Integer> {
    List<Product> findAll();
    List<Product> findByCategory(Integer category);
}

package com.alper.jpaworkshop.product.repositories;

import com.alper.jpaworkshop.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository   extends JpaRepository<ProductCategory, Integer> {
}

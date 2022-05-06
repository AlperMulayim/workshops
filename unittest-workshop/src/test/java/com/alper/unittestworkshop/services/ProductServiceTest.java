package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private  ProductService productService;

    @Test
    void noProductReturnedTest() {

        given(productRepository.getProductNames()).willReturn(Collections.emptyList());
        List<String> result =  productService.getProductsWithEvenCharacters();
        //there will return empty list as result.
        assertTrue(result.isEmpty());

    }

}
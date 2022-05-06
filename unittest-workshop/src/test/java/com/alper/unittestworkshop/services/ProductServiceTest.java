package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private  ProductService productService;

    @Test
    public void noProductReturnedTest() {
        given(productRepository.getProductNames()).willReturn(Collections.emptyList());
        List<String> result =  productService.getProductsWithEvenCharactersWithFiredMethod();
        //there will return empty list as result.
        assertTrue(result.isEmpty());
    }

    @Test
    public void productFoundsTest(){
        List<String> input = Arrays.asList("aa","bbbb","ccc");
        given(productRepository.getProductNames()).willReturn(input);
        List<String> result = productService.getProductsWithEvenCharactersWithFiredMethod();
        assertEquals(2,result.size());
    }

    //test if is method called
    @Test
    public void testIfAddProductCalled(){
        List<String> input = Arrays.asList("aa","bbbb","ccc");
        given(productRepository.getProductNames()).willReturn(input);
        List<String> result = productService.getProductsWithEvenCharactersWithFiredMethod();
        verify(productRepository,times(2)).addProductFired(any());
    }

}
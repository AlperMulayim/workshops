package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.CartDetail;
import com.alper.unittestworkshop.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    CartService cartService;

    private Product productA;
    private Product productB;
    private List<Product> products;

    @BeforeEach
    public void initializeProducts(){
        productA = Product.builder().name("COLA 1 L ").price(7).build();
        productB = Product.builder().name("MILK 1 L ").price(15).build();
        products = new LinkedList<>();
        products.add(productA);
        products.add(productB);

        System.out.println(productA);
        System.out.println(productB);
    }

    @Test
    @DisplayName("thereWillBeTwoProductInCart - There will be TwoProduct in Cart when 2 product send to cart")
    public void thereWillBeTwoProductInCart(){
        CartDetail cartDetail = null;
        cartDetail = cartService.getCartDetail(products);
        assertEquals(cartDetail.getTotalAmount(), products.size());
    }

    @Test
    @DisplayName("totalPriceEqualsToProductsTotalPrice")
    public void totalPriceEqualsToProductsTotalPrice(){
        CartDetail cartDetail = null;
        cartDetail = cartService.getCartDetail(products);
        assertEquals(cartDetail.getTotalPrice(), productA.getPrice() + productB.getPrice());
    }
}
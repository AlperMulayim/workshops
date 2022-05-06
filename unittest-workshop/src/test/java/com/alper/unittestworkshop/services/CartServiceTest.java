package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.CartDetail;
import com.alper.unittestworkshop.Product;
import com.alper.unittestworkshop.campaing.BasketKingCampaing;
import com.alper.unittestworkshop.campaing.Campaing;
import com.alper.unittestworkshop.campaing.Total25PriceCampaing;
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
        productA = Product.builder().name("COLA 1 L ").price(7.0).build();
        productB = Product.builder().name("MILK 1 L ").price(15.0).build();
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

    @Test
    @DisplayName("cartDiscount25Percent - When discountCart campaing is %25 will discount price p-p*0.25")
    public void cartDiscount25Percent(){
        CartDetail cart = CartDetail.builder()
                .totalPrice(100.0)
                .totalAmount(4).build();

        Campaing campaing = new Total25PriceCampaing();

        CartDetail result = cartService.discountCart(cart,campaing);
        assertEquals(result.getTotalPrice(), cart.getTotalPrice() * 0.75);
        assertEquals(result.getTotalAmount(), cart.getTotalAmount());
    }

    @Test
    @DisplayName("cartDiscountBasketValue - BasketCampaing When disCart have more than or equal 5 items will have %5 discount")
    public void cartDiscountBasketValue(){
        CartDetail cart = CartDetail.builder().totalPrice(50.0).totalAmount(5).build();
        Campaing campaing = new BasketKingCampaing();
        CartDetail result = cartService.discountCart(cart,campaing);
        assertEquals(result.getTotalPrice(), cart.getTotalPrice() * 0.95);
        assertTrue(result.getTotalAmount() >= 5 );
    }
}
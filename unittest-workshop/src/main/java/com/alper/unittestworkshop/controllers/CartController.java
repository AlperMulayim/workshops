package com.alper.unittestworkshop.controllers;

import com.alper.unittestworkshop.CartDetail;
import com.alper.unittestworkshop.Product;
import com.alper.unittestworkshop.campaing.BasketKingCampaing;
import com.alper.unittestworkshop.campaing.Campaing;
import com.alper.unittestworkshop.campaing.MilkCampaing;
import com.alper.unittestworkshop.campaing.Total25PriceCampaing;
import com.alper.unittestworkshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public CartDetail getCart(@RequestBody List<Product> products){

        CartDetail cartDetail = cartService.getCartDetail(products);

        if(cartDetail.getTotalPrice() > 100){
            cartDetail = cartService.discountCart(cartDetail,new Total25PriceCampaing());
        }
        if(cartDetail.getTotalAmount() >= 5){
            cartDetail = cartService.discountCart(cartDetail,new BasketKingCampaing());
        }

        return  cartDetail;
    }
    @GetMapping("/discounts")
    public CartDetail discount(@RequestParam(name = "product_name") String productName){
        Product product = Product.builder().name(productName).price(90.0).build();
        CartDetail cart = cartService.getCartDetail(product);
        return  cartService.discountCart(cart,new Total25PriceCampaing());
    }

}

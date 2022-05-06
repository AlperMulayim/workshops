package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.CartDetail;
import com.alper.unittestworkshop.Product;
import com.alper.unittestworkshop.campaing.Campaing;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    public CartDetail getCartDetail(List<Product> productList){
        Double total = 0.0;
        total = productList.stream().map(Product::getPrice).reduce(0.0, Double::sum);
        return  new CartDetail(productList.size(), total);
    }
    public CartDetail getCartDetail(Product product){
        return  new CartDetail(1, product.getPrice());
    }
    public CartDetail discountCart(CartDetail cartDetail,Campaing campaing ){
        Double price = campaing.apply(cartDetail.getTotalPrice());
        return  CartDetail.builder().totalAmount(cartDetail.getTotalAmount()).totalPrice(price).build();
    }
}

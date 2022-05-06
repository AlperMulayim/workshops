package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.CartDetail;
import com.alper.unittestworkshop.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    public CartDetail getCartDetail(List<Product> productList){
        Integer total = 0;
        total = productList.stream().map(Product::getPrice).reduce(0, Integer::sum);
        return  new CartDetail(productList.size(), total);
    }
}

package com.alper.unittestworkshop.services;

import com.alper.unittestworkshop.CartDetail;
import com.alper.unittestworkshop.Product;
import com.alper.unittestworkshop.campaing.Campaing;
import com.alper.unittestworkshop.campaing.MilkCampaing;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    public CartDetail getCartDetail(List<Product> productList){
        Double total = 0.0;
        String discountName = "";
        Double productDiscountTotal = 0.0;
        total = productList.stream().map(Product::getPrice).reduce(0.0, Double::sum);

        Map<String, Campaing> productCampaings = new HashMap<>();
        productCampaings.put("milk",new MilkCampaing());
        productCampaings.put("yogurt", new MilkCampaing());

        for(Product product : productList){
            if(productCampaings.get(product.getName()) != null) {
                productDiscountTotal += productCampaings.get(product.getName()).apply(product.getPrice());
                discountName += "( " + product.getName() + " ) - " + productCampaings.get(product.getName()).getName() + ".";
            }
        }

        return  CartDetail.builder()
                .totalAmount(productList.size())
                .totalPrice(total- productDiscountTotal)
                .discountName("")
                .productDiscounts(discountName)
                .products(productList)
                .build();

    }
    public CartDetail getCartDetail(Product product){
        List<Product> products = new LinkedList<>();
        products.add(product);
        return new CartDetail(1, product.getPrice(),"",products,"");
    }
    public CartDetail discountCart(CartDetail cartDetail,Campaing campaing ){

        Double price = campaing.apply(cartDetail.getTotalPrice());

        return  CartDetail.builder()
                .totalAmount(cartDetail.getTotalAmount())
                .totalPrice(price)
                .discountName(campaing.getName())
                .productDiscounts(cartDetail.getProductDiscounts())
                .products(cartDetail.getProducts())
                .build();
    }
}

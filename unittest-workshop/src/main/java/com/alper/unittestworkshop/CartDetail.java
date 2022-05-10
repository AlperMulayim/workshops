package com.alper.unittestworkshop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    private Integer totalAmount;
    private Double totalPrice;
    private String discountName;
    private List<Product> products;
    private String productDiscounts;
}

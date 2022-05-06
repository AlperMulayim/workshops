package com.alper.unittestworkshop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetail {
    private Integer totalAmount;
    private  Integer totalPrice;
}

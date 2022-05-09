package com.alper.unittestworkshop;

import com.alper.unittestworkshop.campaing.Campaing;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String name;
    private Double price;
}

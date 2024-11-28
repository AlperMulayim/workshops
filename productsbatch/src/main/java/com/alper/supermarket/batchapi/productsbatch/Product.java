package com.alper.supermarket.batchapi.productsbatch;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

@Data
@Builder
public class Product {
    private Integer id;
    private String name;
    private String category;
    private BigDecimal price;
    private String stockDate;
    private String endDate;
    private String priceUpdateDate;
    private Integer stock;
}
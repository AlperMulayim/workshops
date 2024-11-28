package com.alper.supermarket.batchapi.productsbatch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductPriceUpdateProcessor implements ItemProcessor<Product,Product> {
    private SimpleDateFormat dateFormat =  new SimpleDateFormat ("YYYY-MM-dd");

    @Override
    public Product process(Product item) throws Exception {
        item.setPrice( new BigDecimal(item.getPrice().doubleValue() + 100.0 ).setScale(2,1));
        item.setPriceUpdateDate(dateFormat.format(new Date()));
        System.out.println("ITEM -> " + item.getId() + " -> " + item);
        return item;
    }
}

package com.alper.supermarket.batchapi.productsbatch;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductLineMapper implements LineMapper<Product> {

    @Override
    public Product mapLine(String line, int lineNumber) throws Exception {
        String[] parsedLine = line.split(",");
        //try objectmapper here
        return Product.builder()
                .id(Integer.valueOf(parsedLine[0]))
                .name(parsedLine[1])
                .category(parsedLine[2])
                .price(new BigDecimal(parsedLine[3]).setScale(2))
                .stockDate(parsedLine[4])
                .endDate(parsedLine[5])
                .priceUpdateDate(parsedLine[6])
                .stock(Integer.valueOf(parsedLine[7]))
                .build();
    }
}

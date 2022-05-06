package com.alper.unittestworkshop.campaing;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Total25PriceCampaingTest {
    @Test
    public void percent25DiscountTest(){
        Double input = 300.0;
        Campaing campaing = new Total25PriceCampaing();
        Double output = campaing.apply(input);

        assertEquals(input * 0.75, output);
    }
}
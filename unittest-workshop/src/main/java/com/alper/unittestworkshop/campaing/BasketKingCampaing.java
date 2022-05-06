package com.alper.unittestworkshop.campaing;

public class BasketKingCampaing  implements  Campaing{
    @Override
    public double apply(Double price) {
        return  price - price * 0.05;
    }
}

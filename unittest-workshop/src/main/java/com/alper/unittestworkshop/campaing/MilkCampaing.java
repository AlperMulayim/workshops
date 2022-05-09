package com.alper.unittestworkshop.campaing;

public class MilkCampaing  implements  Campaing{
    @Override
    public double apply(Double price) {
        return  price - price * 0.70;
    }

    @Override
    public String getName() {
        return "MILKCampaing- % 30 Discount";
    }
}

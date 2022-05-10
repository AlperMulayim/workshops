package com.alper.unittestworkshop.campaing;

public class Total25PriceCampaing  implements  Campaing{

    @Override
    public double apply(Double price) {
        return price - price * 0.25;
    }

    @Override
    public String getName() {
        return "DISCOUNT -- Applied %25 Discount... :)";
    }
}

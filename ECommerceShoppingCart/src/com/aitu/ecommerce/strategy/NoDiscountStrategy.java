package com.aitu.ecommerce.strategy;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public String getStrategyName() {
        return "Standard Price (No Discount)";
    }

    @Override
    public double applyDiscount(double subtotal) {
        return subtotal;
    }
}

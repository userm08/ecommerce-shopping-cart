package com.aitu.ecommerce.strategy;

public class ThresholdDiscountStrategy implements DiscountStrategy {
    private final double threshold;
    private final double flatDiscount;

    public ThresholdDiscountStrategy(double threshold, double flatDiscount) {
        this.threshold = threshold;
        this.flatDiscount = flatDiscount;
    }

    @Override
    public String getStrategyName() {
        return String.format("Spend $%.2f+ Save $%.2f", threshold, flatDiscount);
    }

    @Override
    public double applyDiscount(double subtotal) {
        if (subtotal >= threshold) {
            return Math.max(0.0, subtotal - flatDiscount);
        }
        return subtotal;
    }
}

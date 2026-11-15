package com.aitu.ecommerce.strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private final double percentage;
    private final String promoCode;

    public PercentageDiscountStrategy(String promoCode, double percentage) {
        this.promoCode = promoCode;
        this.percentage = percentage;
    }

    @Override
    public String getStrategyName() {
        return String.format("Promo Code '%s' (%.0f%% OFF)", promoCode, percentage);
    }

    @Override
    public double applyDiscount(double subtotal) {
        return subtotal * (1.0 - percentage / 100.0);
    }
}

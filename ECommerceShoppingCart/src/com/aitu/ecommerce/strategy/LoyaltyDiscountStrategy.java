package com.aitu.ecommerce.strategy;

public class LoyaltyDiscountStrategy implements DiscountStrategy {
    private final int loyaltyPoints;

    public LoyaltyDiscountStrategy(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public String getStrategyName() {
        return String.format("VIP Loyalty Redemption (%d pts)", loyaltyPoints);
    }

    @Override
    public double applyDiscount(double subtotal) {
        double discountValue = loyaltyPoints * 0.05;
        return Math.max(0.0, subtotal - discountValue);
    }
}

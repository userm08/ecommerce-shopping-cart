package com.aitu.ecommerce.strategy;

public interface DiscountStrategy {
    String getStrategyName();
    double applyDiscount(double subtotal);
}

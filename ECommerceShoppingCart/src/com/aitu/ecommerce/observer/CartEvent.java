package com.aitu.ecommerce.observer;

public record CartEvent(CartEventType type, String productId, int quantity, double currentTotal,
                        double oldPrice, double newPrice) {

    public static CartEvent of(CartEventType type, String productId, int quantity, double currentTotal) {
        return new CartEvent(type, productId, quantity, currentTotal, 0.0, 0.0);
    }

    public static CartEvent priceUpdated(String productId, double oldPrice, double newPrice, double currentTotal) {
        return new CartEvent(CartEventType.PRICE_UPDATED, productId, 0, currentTotal, oldPrice, newPrice);
    }
}

package com.aitu.ecommerce.observer;

public class CustomerNotificationService implements CartObserver {
    private final String customerEmail;

    public CustomerNotificationService(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    @Override
    public void onCartEvent(CartEvent event) {
        switch (event.type()) {
            case DISCOUNT_CHANGED -> System.out.printf(
                    "  [NotificationObserver -> %s] Discount updated! New cart total: $%.2f%n",
                    customerEmail, event.currentTotal());
            case PRICE_UPDATED -> System.out.printf(
                    "  [NotificationObserver -> %s] Price of %s in your cart changed: $%.2f -> $%.2f. New cart total: $%.2f%n",
                    customerEmail, event.productId(), event.oldPrice(), event.newPrice(), event.currentTotal());
            case CHECKOUT_COMPLETED -> System.out.printf(
                    "  [NotificationObserver -> %s] Order confirmed! Paid amount: $%.2f%n",
                    customerEmail, event.currentTotal());
            default -> {
            }
        }
    }
}

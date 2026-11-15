package com.aitu.ecommerce.observer;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager implements CartObserver {
    private final Map<String, Integer> stock = new HashMap<>();

    public void registerProductStock(String productId, int initialStock) {
        stock.put(productId, initialStock);
    }

    public int getAvailableStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }

    public boolean hasSufficientStock(String productId, int requestedQuantity) {
        return getAvailableStock(productId) >= requestedQuantity;
    }

    @Override
    public void onCartEvent(CartEvent event) {
        switch (event.type()) {
            case ITEM_ADDED -> {
                int updated = Math.max(0, getAvailableStock(event.productId()) - event.quantity());
                stock.put(event.productId(), updated);
                System.out.printf("  [InventoryObserver] Reserved %d unit(s) of %s. Remaining stock: %d%n",
                        event.quantity(), event.productId(), updated);
                if (updated <= 2) {
                    System.out.printf("  [InventoryObserver] LOW STOCK ALERT for product %s!%n", event.productId());
                }
            }
            case ITEM_REMOVED -> {
                int updated = getAvailableStock(event.productId()) + event.quantity();
                stock.put(event.productId(), updated);
                System.out.printf("  [InventoryObserver] Restored %d unit(s) of %s. Remaining stock: %d%n",
                        event.quantity(), event.productId(), updated);
            }
            case CHECKOUT_COMPLETED -> System.out.printf(
                    "  [InventoryObserver] Stock deductions finalized for order total $%.2f%n",
                    event.currentTotal());
            default -> {
            }
        }
    }
}

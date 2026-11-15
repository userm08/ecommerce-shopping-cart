package com.aitu.ecommerce.cart;

import com.aitu.ecommerce.model.OrderItem;
import com.aitu.ecommerce.observer.CartEvent;
import com.aitu.ecommerce.observer.CartEventType;
import com.aitu.ecommerce.observer.CartObserver;
import com.aitu.ecommerce.strategy.DiscountStrategy;
import com.aitu.ecommerce.strategy.NoDiscountStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {
    private final List<OrderItem> items = new ArrayList<>();
    private final List<CartObserver> observers = new ArrayList<>();
    private DiscountStrategy discountStrategy = new NoDiscountStrategy();

    public void addObserver(CartObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(CartObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(CartEventType type, String productId, int quantity) {
        publish(CartEvent.of(type, productId, quantity, calculateFinalTotal()));
    }

    public boolean publishPriceUpdate(String productId, double oldPrice, double newPrice) {
        if (!containsProduct(productId)) {
            return false;
        }
        publish(CartEvent.priceUpdated(productId, oldPrice, newPrice, calculateFinalTotal()));
        return true;
    }

    private void publish(CartEvent event) {
        for (CartObserver observer : observers) {
            observer.onCartEvent(event);
        }
    }

    public boolean containsProduct(String productId) {
        return items.stream().anyMatch(item -> item.getProductId().equals(productId));
    }

    public void addItem(OrderItem item) {
        items.add(item);
        System.out.println("  [Cart] Added: " + item.getDescription() + " ($" + String.format("%.2f", item.getCost()) + ")");
        notifyObservers(CartEventType.ITEM_ADDED, item.getProductId(), item.getQuantity());
    }

    public boolean removeByProductId(String productId) {
        Iterator<OrderItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            OrderItem item = iterator.next();
            if (item.getProductId().equals(productId)) {
                iterator.remove();
                System.out.println("  [Cart] Removed: " + item.getDescription());
                notifyObservers(CartEventType.ITEM_REMOVED, item.getProductId(), item.getQuantity());
                return true;
            }
        }
        return false;
    }

    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
        System.out.println("  [Cart] Discount strategy set to: " + strategy.getStrategyName());
        notifyObservers(CartEventType.DISCOUNT_CHANGED, "CART", 0);
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clearItems() {
        items.clear();
        discountStrategy = new NoDiscountStrategy();
    }

    public double calculateSubtotal() {
        double sum = 0.0;
        for (OrderItem item : items) {
            sum += item.getCost();
        }
        return sum;
    }

    public double calculateFinalTotal() {
        return discountStrategy.applyDiscount(calculateSubtotal());
    }
}

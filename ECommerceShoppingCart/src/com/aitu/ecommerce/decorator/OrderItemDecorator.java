package com.aitu.ecommerce.decorator;

import com.aitu.ecommerce.model.OrderItem;

public abstract class OrderItemDecorator implements OrderItem {
    protected final OrderItem wrappedItem;

    public OrderItemDecorator(OrderItem wrappedItem) {
        this.wrappedItem = wrappedItem;
    }

    @Override
    public String getProductId() {
        return wrappedItem.getProductId();
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription();
    }

    @Override
    public double getCost() {
        return wrappedItem.getCost();
    }

    @Override
    public int getQuantity() {
        return wrappedItem.getQuantity();
    }
}

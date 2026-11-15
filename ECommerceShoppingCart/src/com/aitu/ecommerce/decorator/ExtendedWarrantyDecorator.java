package com.aitu.ecommerce.decorator;

import com.aitu.ecommerce.model.OrderItem;

public class ExtendedWarrantyDecorator extends OrderItemDecorator {
    private static final double WARRANTY_RATE = 0.10;

    public ExtendedWarrantyDecorator(OrderItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + " + 2-Year Extended Warranty";
    }

    @Override
    public double getCost() {
        return wrappedItem.getCost() * (1.0 + WARRANTY_RATE);
    }
}

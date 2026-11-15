package com.aitu.ecommerce.decorator;

import com.aitu.ecommerce.model.OrderItem;

public class ExpressShippingDecorator extends OrderItemDecorator {
    private static final double EXPRESS_FEE = 12.50;

    public ExpressShippingDecorator(OrderItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + " + Same-Day Express Delivery";
    }

    @Override
    public double getCost() {
        return wrappedItem.getCost() + EXPRESS_FEE;
    }
}

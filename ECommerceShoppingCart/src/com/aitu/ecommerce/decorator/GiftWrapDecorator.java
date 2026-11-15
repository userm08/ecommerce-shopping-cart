package com.aitu.ecommerce.decorator;

import com.aitu.ecommerce.model.OrderItem;

public class GiftWrapDecorator extends OrderItemDecorator {
    private static final double GIFT_WRAP_FEE = 5.00;

    public GiftWrapDecorator(OrderItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + " + Premium Gift Wrap";
    }

    @Override
    public double getCost() {
        return wrappedItem.getCost() + (GIFT_WRAP_FEE * wrappedItem.getQuantity());
    }
}

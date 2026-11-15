package com.aitu.ecommerce.model;

public class BaseOrderItem implements OrderItem {
    private final Product product;
    private final int quantity;

    public BaseOrderItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String getProductId() {
        return product.getId();
    }

    @Override
    public String getDescription() {
        return String.format("%s x%d (%s, %s)",
                product.getName(), quantity, product.getColor(), product.getSpecification());
    }

    @Override
    public double getCost() {
        return product.getBasePrice() * quantity;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }
}

package com.aitu.ecommerce.model;

public interface OrderItem {
    String getProductId();
    String getDescription();
    double getCost();
    int getQuantity();
}

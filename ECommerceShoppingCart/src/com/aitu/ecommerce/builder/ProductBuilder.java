package com.aitu.ecommerce.builder;

import com.aitu.ecommerce.model.Product;

public class ProductBuilder {
    private String id;
    private String name;
    private String category = "General";
    private double basePrice;
    private String color = "Standard Black";
    private String specification = "Default Config";
    private int warrantyMonths = 12;

    public ProductBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setCategory(String category) {
        this.category = category;
        return this;
    }

    public ProductBuilder setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public ProductBuilder setColor(String color) {
        this.color = color;
        return this;
    }

    public ProductBuilder setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public ProductBuilder setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
        return this;
    }

    public Product build() {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalStateException("Product ID and Name are required");
        }
        if (basePrice <= 0) {
            throw new IllegalStateException("Base price must be positive");
        }
        return new Product(id, name, category, basePrice, color, specification, warrantyMonths);
    }
}

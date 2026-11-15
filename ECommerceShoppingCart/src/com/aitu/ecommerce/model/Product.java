package com.aitu.ecommerce.model;

public class Product {
    private final String id;
    private final String name;
    private final String category;
    private double basePrice;
    private final String color;
    private final String specification;
    private final int warrantyMonths;

    public Product(String id, String name, String category, double basePrice,
                   String color, String specification, int warrantyMonths) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.basePrice = basePrice;
        this.color = color;
        this.specification = specification;
        this.warrantyMonths = warrantyMonths;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 0) {
            throw new IllegalArgumentException("Base price cannot be negative");
        }
        this.basePrice = basePrice;
    }

    public String getColor() {
        return color;
    }

    public String getSpecification() {
        return specification;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    @Override
    public String toString() {
        return String.format("%s [%s] (%s, %s, %d mo warranty) - $%.2f",
                name, id, color, specification, warrantyMonths, basePrice);
    }
}

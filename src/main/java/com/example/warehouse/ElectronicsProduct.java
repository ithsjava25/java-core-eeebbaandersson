package com.example.warehouse;

import java.math.BigDecimal;

public class ElectronicsProduct extends Product implements Shippable {
    private final int warrantyMonths;
    private final BigDecimal weight;

    //Konstruktor som tar alla nödvändiga fält
    public ElectronicsProduct(String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        //Anrop till basklassens konstruktor/måste ske först!
        super(name, category, price);
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }

    public void validateWarranty() {
        if (warrantyMonths <= 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }

    }

    //Todo: Lägg till korrekt format Electronics: Laptop, Warranty: 24 months
    @Override
    public String productDetails() {
        //Returnerar vår produktinformation
        return "";
    }

    //Todo: Lägg till Shipping rule: base 79, add 49 if weight > 5.0 kg
    @Override
    public void calculateShippingCost() {
        //Addera logik för fraktkostnad här

    }

    @Override
    public Object weight() {
        return this.weight;
    }
}

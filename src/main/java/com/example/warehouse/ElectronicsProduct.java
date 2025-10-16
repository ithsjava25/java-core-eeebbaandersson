package com.example.warehouse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable {
    private final int warrantyMonths;
    private final BigDecimal weight;

    //Konstruktor som tar alla nödvändiga fält
    public ElectronicsProduct(UUID id, String name, Category category,
                              BigDecimal price, int warrantyMonths, BigDecimal weight) {
        //Anrop till basklassens konstruktor/måste ske först!
        super(id, name, category, price);
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
        validateWarranty();
    }

    public void validateWarranty() {
        if (warrantyMonths <= 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }
    }

    @Override
    public String productDetails() {
        String productName = name();
        return "Electronics: " + productName + ", Warranty: " + this.warrantyMonths + " months";
    }

    //Todo: Lägg till Shipping rule: base 79, add 49 if weight > 5.0 kg
    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal shippingCost = new BigDecimal("79");
        BigDecimal additionalCost = new BigDecimal("49");

        if (weight.compareTo(new BigDecimal("5.0"))> 0){
           shippingCost = shippingCost.add(additionalCost);

        }
        return shippingCost.setScale(1, RoundingMode.HALF_UP);

    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }
}

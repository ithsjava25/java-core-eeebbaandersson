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

        //Anrop till basklassens konstruktor
        super(id, name, category, price);

        //Validerar inkommande argument
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }

        if (weight == null) {
            throw new IllegalArgumentException("Weight must be provided.");
        }

        if (weight.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        //Initierar fält
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;

    }


    @Override
    public String productDetails() {
        String productName = name();
        return "Electronics: " + productName + ", Warranty: " + warrantyMonths + " months";
    }

    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal shippingCost = new BigDecimal("79.00");
        BigDecimal additionalCost = new BigDecimal("49.00");

        if (weight.compareTo(new BigDecimal("5.0")) > 0){
           shippingCost = shippingCost.add(additionalCost);

        }
        return shippingCost.setScale(2, RoundingMode.HALF_UP);

    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }
}

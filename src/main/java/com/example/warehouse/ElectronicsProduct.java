package com.example.warehouse;

import java.math.BigDecimal;

public class ElectronicsProduct extends Product implements Shippable {
    private int warrantyMonths;
    private BigDecimal weight;

    public void validateWarranty() {
        if (warrantyMonths <= 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }

    }

    @Override
    public String productDetails() {
        return "";
    }

    @Override
    public void calculateShippingCost() {

    }

    @Override
    public Object weight() {
        return null;
    }


}

package com.example.warehouse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable {
    private final LocalDate expirationDate;
    private final BigDecimal weight;

    //Konstruktor som tar alla nödvändiga fält
    public FoodProduct (UUID id, String name, Category category,
                        BigDecimal price, LocalDate expirationDate, BigDecimal weight){

        //Anrop till basklassens konstruktor
        super(id, name, category, price);


        //Validera inkommande argument
        if (expirationDate == null){
            throw new IllegalArgumentException("Expiration date must be provided.");
        }

        if (weight == null){
            throw new IllegalArgumentException("Weight must be provided.");
        }

        if(weight.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        this.expirationDate = expirationDate;
        this.weight = weight;

    }

    @Override
    public LocalDate expirationDate() {
        return this.expirationDate;
    }

    @Override
    public String productDetails() {
        String productName = name();
        LocalDate  expirationDate = expirationDate();
        return "Food: " + productName + ", Expires: " + expirationDate;
    }


    @Override
    public BigDecimal calculateShippingCost() {
        BigDecimal cost = weight.multiply(new BigDecimal("50"));
        return cost.setScale(2, RoundingMode.HALF_UP);

    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }
}

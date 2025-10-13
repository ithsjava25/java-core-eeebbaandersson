package com.example.warehouse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable {
    private final LocalDate expirationDate;
    private final BigDecimal weight;

    //Konstruktor som tar alla nödvändiga fält
    public FoodProduct (UUID id, String name, Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight){

        //Anrop till basklassens konstruktor/måste ske först!
        super(id, name, category, price);
        this.expirationDate = expirationDate;
        this.weight = weight;
        validateWeight();

    }

    public void validateWeight(){
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        //Todo: Lägg till validering för expirationDate?
    }

    @Override
    public LocalDate expirationDate() {
        return this.expirationDate;
    }

    @Override
    public String productDetails() {
        String productName = name();
        LocalDate  expirationDate = expirationDate();
        return "Food: " + productName + ", Expiration date: " + expirationDate;
    }

    //Todo: Lägg till Shipping rule: cost = weight * 50
    @Override
    public BigDecimal calculateShippingCost() {
        //Se över fraktlogiken här!
        return weight.multiply(BigDecimal.valueOf(50));

    }

    @Override
    public double weight() {
        return weight.doubleValue();
    }
}

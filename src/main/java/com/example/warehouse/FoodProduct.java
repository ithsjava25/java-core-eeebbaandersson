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
        super(name, category, price);
        this.expirationDate = expirationDate;
        this.weight = weight;
        validateWeight();

    }

    public void validateWeight(){
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        //Todo: Lägg till validering för expirationDate!
    }

    @Override
    public LocalDate expirationDate() {
        return this.expirationDate;
    }

    //Todo: Lägg till korrekt format "Food: Milk, Expires: 2025-12-24"
    @Override
    public String productDetails() {
        //Returnerar vår produktinformation
        return "";
    }

    //Todo: Lägg till Shipping rule: cost = weight * 50
    @Override
    public void calculateShippingCost() {
        //Addera logik för fraktkostnad här

    }

    @Override
    public Object weight() {
        return this.weight;
    }
}

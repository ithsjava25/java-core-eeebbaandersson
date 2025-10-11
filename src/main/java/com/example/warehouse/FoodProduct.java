package com.example.warehouse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FoodProduct extends Product implements Perishable, Shippable {
    private final LocalDate expirationDate;
    private final BigDecimal weight;

    //Konstruktor som tar alla nödvändiga fält
    public FoodProduct (String name, Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight ){

        //Anrop till basklassens konstruktor
        super(name, category, price);

        this.expirationDate = expirationDate;
        this.weight = weight;
        validateWeight();

    }

    public void validateWeight(){


        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }


    }

    @Override
    public LocalDate expirationDate() {
        return null;
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

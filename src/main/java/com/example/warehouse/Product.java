package com.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Product {
    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;


     public Product(UUID id, String name, Category category, BigDecimal price) {
         //ID:t genereras internt
         this.id = UUID.randomUUID();

         //Validera inkommande argument först (Fail-Fast).
         if (name == null ){
             throw new IllegalArgumentException("Product name cannot be null.");
         }

         if (name.isBlank()){
             throw new IllegalArgumentException("Product name cannot be blank.");
         }

         if (price.compareTo(BigDecimal.ZERO) < 0){
             throw new IllegalArgumentException("Price cannot be negative.");
         }

         if (category == null){
             throw new IllegalArgumentException("Category must be provided.");
         }

         //Initierar fält
         this.name = name;
         this.category = category;
         this.price = price;
     }

     public UUID uuid() {
         return id;
     }

     public String name() {
         return name;
     }

     public Category category() {
         return category;
     }

     public BigDecimal price() {
         return price;
     }

     //Set-metod
     public void price(BigDecimal price) {
         this.price = price;
     }

     //Abstrakt metod: Saknar kropp och måste implementeras!
     public abstract String productDetails();

 }



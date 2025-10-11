package com.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

 //Sätta  name,category och price som final fält?
public abstract class Product {
    private UUID id;
    private String name;
    private Category category;
    private BigDecimal price;


     public Product(String name, Category category, BigDecimal price) {
         //ID:t genereras internt
         this.id = UUID.randomUUID();

         //Validera inkommande argument först (Fail-Fast).
         if (name == null || name.isEmpty()){
             throw new IllegalArgumentException("Product name cannot be null or empty.");
         }
         if (price == null || price.compareTo(BigDecimal.ZERO) < 0){
             throw new IllegalArgumentException("Price cannot be null or negative.");
         }
         if (category == null){
             throw new IllegalArgumentException("Category must be provided.");
         }

         //Initierar fält
         this.name = name;
         this.category = category;
         this.price = price;
     }

     public UUID getId() {
         return id;
     }

     public String getName() {
         return name;
     }

     public Category getCategory() {
         return category;
     }

     public BigDecimal getPrice() {
         return price;
     }

     public void setPrice(BigDecimal price) {
         this.price = price;
     }

     //Abstrakt metod: Saknar kropp och måste implementeras!
     public abstract String productDetails();
 }



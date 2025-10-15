package com.example.warehouse;

import java.security.Key;
import java.util.*;

public class Warehouse {
    private static final Map<String, Warehouse> warehouse = new HashMap<>();

    private final String name;

    private Warehouse (String name){
        this.name = name;
    }

    // getInstance(String name) returns the same instance per unique name
    public static Warehouse getInstance(String name) {
        synchronized (warehouse) {

            Warehouse instance = warehouse.get(name);
            if (instance == null) {
                instance = new Warehouse(name);
                warehouse.put(name, instance);
            }
            return instance;
        }
    }

    //Todo: Senast skapad, se över!!
    //addProduct(Product): throw IllegalArgumentException("Product cannot be null.") if null
    public void addProduct(Product product) {
        if (product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
    }

    //getProducts(): returns an unmodifiable copy
    public List<Product> getProducts() {
        return List.of();
    }

    //getProductById(UUID): return Optional
    //Todo: Addera korrekt logik
    public Optional<Product> getProductById(UUID uuid) {
        //Addera korrekt logik här
        return Optional.empty();
    }

    //shippableProducts(): return List from stored products
    public List<Shippable> shippableProducts() {
        return List.of();
    }

    //updateProductPrice(UUID, BigDecimal): when not found, throw NoSuchElementException("Product not found with id: ")
    //Also track changed products in getChangedProducts()
    public void updateProductPrice() {

    }

    //expiredProducts(): return List that are expired
    public List<Perishable> expiredProducts() {
        return List.of();
    }

    //remove(UUID): remove the matching product if present
    public void remove(UUID id) {

    }

    //Ensure Warehouse.clearProducts() is called in tests; do not share state between tests
    public void clearProducts() {
    }

    public boolean isEmpty() {
        return true;
    }


    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return  Map.of();
    }
}


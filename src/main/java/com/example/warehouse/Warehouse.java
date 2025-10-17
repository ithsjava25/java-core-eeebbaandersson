package com.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> warehouse = new HashMap<>();

    private final Map<UUID,Product> products = new HashMap<>();
    private final Set<Product> changedProducts = new HashSet<>();

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

    public void addProduct(Product product) {
        if (product == null){
            throw new IllegalArgumentException("Product cannot be null.");
        }
        products.put(product.uuid(),product);
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    //getProductById(UUID): return Optional
    public Optional<Product> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));

    }

    //Todo:Förstå korrekt och bygg om logiken själv!
    //shippableProducts(): return List from stored products
    public List<Shippable> shippableProducts() {
        return products.values().stream()
                .filter(product -> product instanceof Shippable)
                .map(product -> (Shippable) product)
                .collect(Collectors.toList());

    }

    //updateProductPrice(UUID, BigDecimal): when not found, throw NoSuchElementException("Product not found with id: ")
    public void updateProductPrice() {

        //Sök efter produkten
        //Kasta undantag endast om produkten inte hittas
        //Uppdatera priset och spåra produkten

    }


    //Also track changed products in getChangedProducts()
    public List<Product> getChangedProducts() {
        return List.copyOf(changedProducts);
    }

    //Todo: Returnerar just nu en tom lista!
    //expiredProducts(): return List that are expired
    public List<Perishable> expiredProducts() {
        
        return List.of();
    }

    //remove(UUID): remove the matching product if present
    public void remove(UUID id) {
        products.remove(id);
    }

    //Ensure Warehouse.clearProducts() is called in tests; do not share state between tests
    public void clearProducts() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }


    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(Product::category));
    }

}


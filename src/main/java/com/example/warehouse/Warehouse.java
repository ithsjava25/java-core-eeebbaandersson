package com.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> warehouse = new HashMap<>();
    private final List<Product> products = new ArrayList<>();

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
        products.add(product);

    }

    public List<Product> getProducts() {
        return List.copyOf(products);
    }

    //getProductById(UUID): return Optional
    public Optional<Product> getProductById(UUID uuid) {
        List<Product> products = getProducts();

         return products.stream()
                .filter(product -> product.uuid().equals(uuid))
                .findFirst();

    }

    //Todo:Förstå korrekt och bygg om logiken själv!
    //shippableProducts(): return List from stored products
    public List<Shippable> shippableProducts() {
        return products.stream()
                .filter(product -> product instanceof Shippable)
                .map (product ->  (Shippable) product)
                .toList();

    }

    //updateProductPrice(UUID, BigDecimal): when not found, throw NoSuchElementException("Product not found with id: ")
    //Also track changed products in getChangedProducts()
    public Map<UUID, BigDecimal> updateProductPrice() {


        return null;



    }

    //Todo: Returnerar just nu en tom lista!
    //expiredProducts(): return List that are expired
    public List<Perishable> expiredProducts() {
        return List.of();
    }

    //remove(UUID): remove the matching product if present
    public void remove(UUID id) {
        products.removeIf((product ->  product.uuid().equals(id)));


    }

    //Ensure Warehouse.clearProducts() is called in tests; do not share state between tests
    public void clearProducts() {
        products.clear();

    }

    public boolean isEmpty() {
        return true;
    }

    //Todo:Se över och bygg om logiken!
    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        //Hämta vår lista
        //Skapa vår stream
        //Använd Collector, collect(), Collectors.groupingBy()
        //Definiera vår nyckel med metodreferensen
        //Returnera vår map

        return getProducts().stream()
                .collect(Collectors.groupingBy(Product::category));
    }
}


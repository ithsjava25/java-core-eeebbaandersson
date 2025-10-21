package com.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> warehouse = new ConcurrentHashMap<>();

    private final Map<UUID,Product> products = new HashMap<>();
    private final Set<Product> changedProducts = new HashSet<>();

    private final String name;

    private Warehouse (String name){
        this.name = name;
    }


    public static Warehouse getInstance(){
        return warehouse.values().stream().findFirst().get();
    }

    //Returnerar Warehouse-instans(String name), om den saknas skapas en ny
    public static Warehouse getInstance(String name) {
            return warehouse.computeIfAbsent(name, Warehouse::new);
    }

    //Adderar en produkt
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        //Om en produkt med ett redan existerande id försöker läggas till, kasta exception
        if (products.containsKey(product.uuid())) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        products.put(product.uuid(), product);
    }


    //Returnerar en icke-modifierbar List-kopia
    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    //Returnerar Optional
    public Optional<Product> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }

    //shippableProducts(): return List from stored products
    public List<Shippable> shippableProducts() {
        return products.values().stream()
                .filter(product -> product instanceof Shippable)
                .map(product -> (Shippable) product)
                .collect(Collectors.toList());

    }

    public void updateProductPrice(UUID id, BigDecimal newPrice) {

        //Validering av nytt pris
        if (newPrice == null){
            throw new IllegalArgumentException("Price cannot be null.");
        }

        //Söker efter produkten
        Product product = products.get(id);

        //Om produkten inte hittas, kasta undantaget
        if (product == null){
            throw new NoSuchElementException("Product not found with id: "+ id);
        }

        //Jämför gammalt mot nytt pris, har en förändring skett uppdatera priset och produkten spåras i changedProducts
        if (product.price().compareTo(newPrice) != 0){
            product.setPrice(newPrice);
            changedProducts.add(product);
        }
    }

    //Spårar ändrade produkter
    public List<Product> getChangedProducts() {
        return List.copyOf(changedProducts);
    }

    //Todo: Fixad efter AI-feedback
    //Returnerar lista av utgångna produkter
    public List<Perishable> expiredProducts() {
        return products.values()
                .stream()
                .filter(product -> product instanceof Perishable &&
                        ((Perishable) product).isExpired())
                .map(product -> (Perishable) product)
                .collect(Collectors.toList());
    }

    //Todo: Redan fixat men AI-boten klagar ändå?
    //Tar bort en befintligt produkt om den matchar id:et
    public void remove(UUID id) {
        Product removedProduct = products.remove(id);
        if (removedProduct != null){
            changedProducts.remove(removedProduct);
        }
    }

    public void clearProducts() {
        products.clear();
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    //Grupperar produkter utifrån kategorier
    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return products.values().stream()
                .collect(Collectors.groupingBy(Product::category));
    }

}


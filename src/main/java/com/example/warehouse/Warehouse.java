package com.example.warehouse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {
    private static final String DEFAULT_INSTANCE_NAME = "DEFAULT_WAREHOUSE_KEY";
    private static final Map<String, Warehouse> warehouse = new ConcurrentHashMap<>();

    private final Map<UUID,Product> products = new HashMap<>();
    private final Set<Product> changedProducts = new HashSet<>();

    private final String name;

    private Warehouse (String name){
        this.name = name;
    }

    public static Warehouse getInstance(){
        //return warehouse.values().stream().findFirst().get();
        return warehouse.computeIfAbsent("DEFAULT_WAREHOUSE_KEY",Warehouse::new);
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


    //Returnerar lista av utgångna produkter
    public List<Perishable> expiredProducts() {
        return products.values()
                .stream()
                .filter(product -> product instanceof Perishable &&
                        ((Perishable) product).isExpired())
                .map(product -> (Perishable) product)
                .collect(Collectors.toList());
    }

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

    public List<Product> findPriceOutliers(){
        //Hämtar produktpriser, sorteras i stigande ordning och sparas i ny lista sortedPrices
        var sortedPrices = products.values().stream()
               .map(Product::price)
               .sorted()
               .toList();

        //Listans storlek
        int N = sortedPrices.size();

        //Hantera fallet om listan är tom/har för få värden
        if (N == 0){
           System.out.println("No prices found.");
           return  List.of();
        }

        //Q1 -->Medianen nedre halvan
        int N_lower = N/2;
        BigDecimal Q1;

        if (N_lower %2 != 0){
            //Udda storlek på halvan, Q1 = enskilt element
            int indexQ1 = N_lower / 2;
             Q1 = sortedPrices.get(indexQ1);
        } else {
            //Jämn storlek på halvan, Q1 blir medelvärdet av 2 element
            int index1 = N_lower/2-1;
            int index2 = N_lower/2;

            BigDecimal sum = sortedPrices.get(index1).add(sortedPrices.get(index2));
            Q1 = sum.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        }

        //Q3 --> Medianen övre halvan
        int start_uper;
        //Hittar startindex för övre halvan
        if(N % 2!= 0){
            //Udda fall, excludera medianen (N/2), start vid N/2 +1
           start_uper = N/2 +1;
        } else {
            //Jämt fall, Övre halvan börjar efter första N/2 elementen
            start_uper = N/2;
        }

        BigDecimal Q3;
        if (N_lower % 2 != 0){
            //Udda storlek på halvan, Q3 = enskilt element
            int indexQ3 = start_uper + N_lower / 2;
            Q3 = sortedPrices.get(indexQ3);
        } else {
            //Jämn storlek på halvan, Q3 blir medelvärdet av 2 element
            int index1 = start_uper + (N_lower / 2) -1;
            int index2 = start_uper + N_lower / 2;

            BigDecimal sum = sortedPrices.get(index1).add(sortedPrices.get(index2));
            Q3 = sum.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        }

        //Beräknar IQR
        BigDecimal IQR = Q3.subtract(Q1);

        //Outlier Gräns
        BigDecimal Fence = IQR.multiply(BigDecimal.valueOf(1.5));


        BigDecimal lowerFence = Q1.subtract(Fence);
        BigDecimal upperFence = Q3.add(Fence);

        var priceOutliers = products.values().stream()
                .filter(product -> {
                    BigDecimal price = product.price();


                    //Jämför priset med gränserna
                    boolean isLowerOutliers = price.compareTo(lowerFence) < 0;
                    boolean isUpperOutliers = price.compareTo(upperFence) > 0;

                    return isLowerOutliers || isUpperOutliers;
                })
                .toList();

        return priceOutliers;
    }

}


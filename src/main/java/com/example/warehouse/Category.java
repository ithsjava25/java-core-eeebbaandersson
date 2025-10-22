package com.example.warehouse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Category {
    private static final Map<String, Category> category = new ConcurrentHashMap<>();
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }
        final String normalizedKey = name.trim().toLowerCase();
        final String displayableName = normalizedKey.substring(0, 1).toUpperCase() +
                normalizedKey.substring(1);

        return category.computeIfAbsent( normalizedKey, key ->
                new Category(displayableName));
    }

    public String getName() {
        return this.name;
    }
}

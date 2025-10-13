package com.example.warehouse;

import java.util.Objects;

public class Category {
    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isBlank()){
            throw new IllegalArgumentException("Category name can't be blank");
        }
        return new Category(name);
    }

    //Todo: Fix --> Normalize name with initial capital letter (e.g., "fruit" -> "Fruit").
    //Todo: Cache/flyweight: return the same instance for the same normalized name.

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Category: " +
                "name: " + name;
    }
}

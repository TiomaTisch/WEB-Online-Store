package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Product implements Searchable {

    private final UUID id;

    // другие поля класса
    private final  String name;

    // Конструктор с добавлением параметра id
    public Product(UUID id, String name) {
        this.id = id;
        // инициализация других полей
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым или состоять только из пробелов.");
        }
        this.name = name;
    }

    // Геттер для id
    @Override
    public UUID getId() {
        return id;
    }

    // Геттеры и сеттеры для других полей
    public String getName() {
        return name;
    }

//    public abstract int getPrice();

    @Override @JsonIgnore
    public String getSearchTerm() {
        return name;
    }

    @Override @JsonIgnore
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public String getStringRepresentation() {
        return Searchable.super.getStringRepresentation();
    }

    // Другие методы
//    public abstract boolean isSpecial();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;
import org.skypro.skyshop.exception.NoSuchProductException;

import java.util.*;

@Service
public class StorageService {

    private final Map<UUID, Product> productStorage;
    private final Map<UUID, Article> articleStorage;

    public StorageService() {
        this.productStorage = new HashMap<>();
        this.articleStorage = new HashMap<>();
        initializeTestData();
    }

    // Метод для инициализации тестовых данных
    private void initializeTestData() {
        // Создание тестовых продуктов
        Product product1 = new Product(UUID.randomUUID(), "Яблоко", 300);
        Product product2 = new Product(UUID.randomUUID(), "Банан", 400);
        productStorage.put(product1.getId(), product1);
        productStorage.put(product2.getId(), product2);

        // Создание тестовых статей
        Article article1 = new Article(UUID.randomUUID(), "Польза яблок", "Яблоки полезны для здоровья.");
        Article article2 = new Article(UUID.randomUUID(), "Польза Бананов", "Бананы полезны для здоровья.");
        articleStorage.put(article1.getId(), article1);
        articleStorage.put(article2.getId(), article2);
    }

    // Метод для получения всех продуктов
    public Collection<Product> getAllProducts() {
        return productStorage.values();
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(productStorage.get(id));
    }

    // Метод для получения всех статей
    public Collection<Article> getAllArticles() {
        return articleStorage.values();
    }

    // Метод, который возвращает коллекцию Searchable
    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(productStorage.values());
        searchables.addAll(articleStorage.values());
        return searchables;
    }
}

package org.skypro.skyshop.model.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public class Article implements Searchable {

    private final UUID id;

    // другие поля класса
    private final String title;
    private final String text;

    // Конструктор с добавлением параметра id
    public Article(UUID id, String title, String text) {
        this.id = id;
        // инициализация других полей
        this.title = title;
        this.text = text;
    }

    // Геттер для id
    @Override
    public UUID getId() {
        return id;
    }

    // Геттеры и сеттеры для других полей
    @Override @JsonIgnore
    public String getSearchTerm() {
        return toString();
    }

    @Override @JsonIgnore
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return title;
    }


    // Другие методы
    @Override
    public String toString() {
        return title + "\n" + text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;
        return Objects.equals(title, article.title);
    }
}
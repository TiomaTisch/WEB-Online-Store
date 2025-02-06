package org.skypro.skyshop.model.search;

import java.util.UUID;

public interface Searchable {

    UUID getId();

    // другие методы интерфейса
    String getSearchTerm();

    String getContentType();

    String getName();

    default String getStringRepresentation() {
        return getName() + " — тип " + getContentType();
    }
}
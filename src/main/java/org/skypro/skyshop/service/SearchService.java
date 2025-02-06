package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Collection<SearchResult> search(String pattern) {
        Collection<Searchable> searchables = storageService.getAllSearchables();
        return searchables.stream()
                .filter(searchable -> matches(searchable, pattern))
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }

    private boolean matches(Searchable searchable, String pattern) {
        return searchable.getName().toLowerCase().contains(pattern.toLowerCase()) ||
                searchable.getContentType().toLowerCase().contains(pattern.toLowerCase());
    }
}

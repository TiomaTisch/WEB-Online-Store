package org.skypro.skyshop.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_WhenNoSearchables_ReturnsEmptyCollection() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search("any");

        assertTrue(results.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenNoMatches_ReturnsEmptyCollection() {
        Product product = new Product(UUID.randomUUID(), "Apple", 100);
        Article article = new Article(UUID.randomUUID(), "Health", "About health");
        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(product, article));

        Collection<SearchResult> results = searchService.search("orange");

        assertTrue(results.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenProductMatches_ReturnsProductResult() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Apple", 100);
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));

        Collection<SearchResult> results = searchService.search("app");

        assertEquals(1, results.size());
        SearchResult result = results.iterator().next();
        assertEquals(productId.toString(), result.getId());
        assertEquals("Apple", result.getName());
        assertEquals("PRODUCT", result.getContentType());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenArticleMatches_ReturnsArticleResult() {
        UUID articleId = UUID.randomUUID();
        Article article = new Article(articleId, "Health", "About health");
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(article));

        Collection<SearchResult> results = searchService.search("health");

        assertEquals(1, results.size());
        SearchResult result = results.iterator().next();
        assertEquals(articleId.toString(), result.getId());
        assertEquals("Health", result.getName());
        assertEquals("ARTICLE", result.getContentType());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenMatchesContentType_ReturnsResults() {
        Product product = new Product(UUID.randomUUID(), "Banana", 150);
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));

        Collection<SearchResult> results = searchService.search("prod");

        assertEquals(1, results.size());
        SearchResult result = results.iterator().next();
        assertEquals("PRODUCT", result.getContentType());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_IsCaseInsensitive() {
        Product product = new Product(UUID.randomUUID(), "Apple", 100);
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));

        Collection<SearchResult> results = searchService.search("aPp");

        assertEquals(1, results.size());
        verify(storageService).getAllSearchables();
    }
}


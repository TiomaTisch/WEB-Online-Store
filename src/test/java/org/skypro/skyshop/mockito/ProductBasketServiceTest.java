package org.skypro.skyshop.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProductToBasket_WhenProductNotExists_ThrowsException() {
        UUID productId = UUID.randomUUID();
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.addProductToBasket(productId));
        verify(storageService).getProductById(productId);
        verifyNoInteractions(productBasket);
    }

    @Test
    void addProductToBasket_WhenProductExists_AddsToBasket() {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Apple", 100);
        when(storageService.getProductById(productId)).thenReturn(Optional.of(product));

        basketService.addProductToBasket(productId);

        verify(storageService).getProductById(productId);
        verify(productBasket).addProduct(productId);
    }

    @Test
    void getUserBasket_WhenBasketEmpty_ReturnsEmptyBasket() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket userBasket = basketService.getUserBasket();

        assertTrue(userBasket.getItems().isEmpty());
        assertEquals(0.0, userBasket.getTotal());
        verify(productBasket).getProducts();
        verifyNoInteractions(storageService);
    }

    @Test
    void getUserBasket_WhenBasketHasProducts_ReturnsCorrectBasket() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        Product product1 = new Product(productId1, "Apple", 100);
        Product product2 = new Product(productId2, "Banana", 150);

        Map<UUID, Integer> basketProducts = new HashMap<>();
        basketProducts.put(productId1, 2);
        basketProducts.put(productId2, 1);

        when(productBasket.getProducts()).thenReturn(basketProducts);
        when(storageService.getProductById(productId1)).thenReturn(Optional.of(product1));
        when(storageService.getProductById(productId2)).thenReturn(Optional.of(product2));

        UserBasket userBasket = basketService.getUserBasket();

        assertEquals(2, userBasket.getItems().size());
        assertEquals(350.0, userBasket.getTotal());

        // Verify items
        List<BasketItem> items = userBasket.getItems();
        assertTrue(items.stream().anyMatch(item ->
                item.getProduct().equals(product1) && item.getQuantity() == 2));
        assertTrue(items.stream().anyMatch(item ->
                item.getProduct().equals(product2) && item.getQuantity() == 1));

        verify(productBasket).getProducts();
        verify(storageService, times(2)).getProductById(any());
    }

    @Test
    void getUserBasket_WhenProductNotFound_ThrowsException() {
        UUID productId = UUID.randomUUID();
        Map<UUID, Integer> basketProducts = Collections.singletonMap(productId, 1);

        when(productBasket.getProducts()).thenReturn(basketProducts);
        when(storageService.getProductById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class, () -> basketService.getUserBasket());
        verify(productBasket).getProducts();
        verify(storageService).getProductById(productId);
    }
}

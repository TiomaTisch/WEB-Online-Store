package org.skypro.skyshop.model.basket;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public final class UserBasket {
    private final List<BasketItem> items;
    private final double total;

    public UserBasket(List<BasketItem> items, double total) {
        this.items = Collections.unmodifiableList(new ArrayList<>(items));
        this.total = total;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }
}
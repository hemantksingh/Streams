package com.example.functional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by hkumar on 06/04/2015.
 */
public class Sale {
    public final String store;
    public final Date soldOn;
    public final Optional<String> soldBy;
    public final List<Item> itemsSold;

    public Sale(String store, Date soldOn, Optional<String> soldBy, List<Item> itemsSold) {

        this.store = store;
        this.soldOn = soldOn;
        this.soldBy = soldBy;
        this.itemsSold = itemsSold;
    }

    public double total() {
        return itemsSold.stream()
                .mapToDouble(item -> item.price)
                .sum();
    }
}

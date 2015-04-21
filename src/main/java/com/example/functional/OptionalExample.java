package com.example.functional;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by hkumar on 12/04/2015.
 */
public class OptionalExample {

    static Stream<Sale> saleStream() {
        return RandomSale.streamOf(3);
    }

    public Optional<Sale> findSaleOf(String itemName){
        return saleStream().filter(sale ->
                sale.itemsSold.stream()
                        .anyMatch(item -> item.name.equals(itemName)))
                .findFirst();
    }

    public Optional<String> cookiesSoldBy(){
        return findSaleOf("cookies").flatMap(sale -> sale.soldBy);
    }
}

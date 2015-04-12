package com.example.functional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by hkumar on 12/04/2015.
 */
public class FixedSale {

    private static final List<Sale> sales = Arrays.asList(
            new Sale("Manchester", new Date(), Optional.of("Sarah"),
                    Arrays.asList(new Item("apple", 12.00))
            ),
            new Sale("Birmingham", new Date(), Optional.empty(),
                    Arrays.asList(
                            new Item("apple", 12.00),
                            new Item("cake", 99.99),
                            new Item("cookie", 1.99))
            ),
            new Sale("London", new Date(), Optional.of("Jamie"),
                    Arrays.asList(
                            new Item("banana", 3.49),
                            new Item("cookie", 1.49))
            ));

    public static Stream<Sale> stream() {
        return sales.stream();
    }
}

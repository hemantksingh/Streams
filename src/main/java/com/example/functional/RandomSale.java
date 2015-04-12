package com.example.functional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.functional.RandomUtil.randomElement;

/**
 * Created by hkumar on 10/04/2015.
 */
public class RandomSale {

    private static final int MAX_ITEMS = 6;
    private static final String[] CUSTOMERS = new String[]{
            "Baba", "Nikhil", "Sandy", "Dak", "Kasani"
    };
    private static final String[] STORES = new String[] {
            "Manchester", "London", "Birmingham"
    };
    private static final double PERCENT_NO_CUSTOMER = 0.25;
    private static Random random = new Random();

    public static Stream<Sale> streamOf(long qty) {
        return Stream.generate(supplier).limit(qty);
    }

    public static Supplier<Sale> supplier = () -> new Sale(
            pickAStore(),
            new Date(),
            pickACustomer(),
            randomListOfItems());

    private static List<Item> randomListOfItems() {

        int howMany = random.nextInt(MAX_ITEMS - 1) + 1;
        return RandomItems.infiniteStream()
                .limit(howMany)
                .collect(Collectors.toList());
    }

    private static Optional<String> pickACustomer() {

        if(random.nextDouble() < PERCENT_NO_CUSTOMER)
            return Optional.empty();

        return Optional.of(randomElement(CUSTOMERS));
    }

    private static String pickAStore() {
        return randomElement(STORES);
    }
}

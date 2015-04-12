package com.example.functional;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.example.functional.RandomUtil.randomElement;

/**
 * Created by hkumar on 12/04/2015.
 */
public class RandomItems {

    private Random random = new Random();
    private Supplier<Item> itemSupplier= () ->
            new Item(pickAName(), pickAPrice());
    public static Stream<Item> infiniteStream() {
        return Stream.generate(new RandomItems().itemSupplier);
    }

    private static final double MAX_PRICE = 150.00;
    private  final String pickAName() {

        return randomElement(AVAILABLE_ITEMS);
    }

    private static final String[] AVAILABLE_ITEMS = new String[] {
            "carrot", "eggs", "cake", "popcorn", "cookies", "nachos"
    };
    private double pickAPrice() {
        return Math.round(random.nextDouble() * MAX_PRICE * 100)/100.0;
    }
}

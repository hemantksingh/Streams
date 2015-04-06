package com.example.functional;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hkumar on 06/04/2015.
 */
public class TodaysSales {
    static final List<Sale> sales = Arrays.asList(
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
    static Stream<Sale> saleStream(){
        return sales.stream();
    }

    public static void figures(){
        System.out.println("Count of sales: " + saleStream().count());

        // Sales over $100 ?
        System.out.println("Big sale day? " + saleStream().anyMatch(sale -> sale.total() > 100.00));

        DoubleSummaryStatistics stats = saleStream().mapToDouble(Sale::total).summaryStatistics();
        System.out.println("Max sale amount: " + stats.getMax());
        System.out.println("Stats on total: " + stats);

        // How many items sold today ?

        // A stream instance is terminated once a terminal operation (count, sum) is invoked on the stream.
        // Filter, map, filter map are intermediate operations that allow continuously using the stream.
        // A Supplier is used for creating  a stream instance every time the stream is used. This prevents
        // accidental use of a Stream variable that has already been used once and therefore terminated.
        Supplier<Stream<Item>> itemStream =
                () -> saleStream().flatMap(sale -> sale.itemsSold.stream()); // SelectMany operation in C#
        System.out.println("Total items sold today: " + itemStream.get().count());

        // How many distinct items were sold ?
        System.out.println("Distinct items sold: " + itemStream.get()
                .map(item -> item.name)
                .distinct()
                .count());

        // List of distinct items sold
        List<String> distinctItemsSold = itemStream.get()
                .map(item -> item.name)
                .distinct()
                .collect(Collectors.toList()); //Stream of String to a list
        System.out.println("List of distinct items sold: " + distinctItemsSold);

        // Distinct items sold
        String distinctItems = itemStream.get()
                .map(item -> item.name)
                .distinct()
                .collect(Collectors.joining(" , ")); //Stream of String to a list
        System.out.println("Distinct items sold: " + distinctItems);

        // Summarise sales by store
        Map<String, DoubleSummaryStatistics> summary = saleStream().collect(Collectors.groupingBy(
                sale -> sale.store,
                Collectors.summarizingDouble(value -> value.total())));

        summary.keySet().stream().forEach(
                store -> System.out.println(store + " stats: " + summary.get(store)));
    }
}

class Item {
    public final String name;
    public final double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
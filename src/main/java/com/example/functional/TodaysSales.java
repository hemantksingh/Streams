package com.example.functional;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by hkumar on 06/04/2015.
 */
public class TodaysSales {

    static Stream<Sale> saleStream(){
        return RandomSale.streamOf(10000);
    }

    public static void figures(){
        System.out.println("Count of sales: " + saleStream().count());

        // Sales over $100 ?
        System.out.println("Big sale day? " + saleStream()
                .anyMatch(sale -> sale.total() > 100.00));

        DoubleSummaryStatistics stats = saleStream()
                .mapToDouble(Sale::total).summaryStatistics();
        System.out.println("Max sale amount: " + stats.getMax());
        System.out.println("Stats on total: " + stats);

        // How many items sold today ?

        // A stream instance is terminated once a terminal operation
        // (count, sum) is invoked on the stream. Filter, map, filter
        // map are intermediate operations that allow continuously using the stream.
        // A Supplier is used for creating  a stream instance every
        // time the stream is used. This prevents accidental use of
        // a Stream variable that has already been used once and therefore terminated.
        Supplier<Stream<Item>> itemStream = () ->
                // SelectMany operation in C#
                saleStream().flatMap(sale -> sale.itemsSold.stream());
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
                .collect(Collectors.joining(" , ")); //Stream of String to a single joined String
        System.out.println("Distinct items sold: " + distinctItems);

        // Summarise sales by store
        Map<String, DoubleSummaryStatistics> summary = saleStream()
                .parallel()
                .collect(Collectors.groupingBy(
                        sale -> Thread.currentThread().getName(),
                        Collectors.summarizingDouble(value -> value.total())));

        System.out.println("Summary by thread: " + summary);
        summary.keySet().stream().sorted().forEach(
                store -> System.out.println(store + " stats: " + summary.get(store)));

        System.out.println("Cookies sold by: " + new OptionalExample()
                .cookiesSoldBy().orElse("Can't say"));

        canCreateItemWithFuncs();
    }

    private static void canCreateItemWithFuncs() {

        Supplier<Item> supplier = () -> new Item("pastry", 20.20);
        Item pastry = supplier.get();

        Function<String, Item> createItem = s -> new Item(s, 20.20);
        Item popcorn = createItem.apply("popcorn");

        ItemFactory factory = (name, price) -> new Item(name, price);
        Item nachos = factory.create("nachos", 20.20);

        Consumer<Item> consumer = item -> System.out.println(
                String.format("Price for item: %s is \"%s\"", item.name , item.price));
        consumer.accept(nachos);

        Function<Item, Function<Item, Double>> totalPrice = x -> y -> x.price + y.price;
        System.out.print(totalPrice.apply(pastry).apply(popcorn).equals(40.40));
    }

    @FunctionalInterface
    interface ItemFactory{
        Item create(String name, double price);
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
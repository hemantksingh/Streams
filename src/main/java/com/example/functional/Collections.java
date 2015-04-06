package com.example.functional;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by hkumar on 06/04/2015.
 */
public class Collections {
    public static void summary () {
        final String summary = summary (food);
        final String desiredSummary = "carrots & bananas & pumpkins & broccoli & meat";

        System.out.println(summary);

        if(summary.equals(desiredSummary))
            System.out.print("Good job!");
    }

    final static String[] food = new String[] {
            "Crunchy carrots",
            "Golden-hued bananas",
            "",
            "Bright orange pumpkins",
            "Little trees of broccoli",
            "meat"
    };

    private static final Predicate<String> NON_EMPTY = s -> !s.isEmpty();

    private static final Function<String, String> lastWord = s ->
            Arrays.asList(s.split(" ")).stream()
                    .reduce((allSoFar, next) -> next)
                    .orElse("");

    private static String summary(String[] food) {
        return Arrays.asList(food).stream()
                .filter(NON_EMPTY)
                .map(lastWord)
                .reduce(joinOn(" & "))
                .orElse("");
    }

    private static BinaryOperator<String> joinOn(String connector) {
        return (allSoFar, next) -> allSoFar + connector + next;
    }
}

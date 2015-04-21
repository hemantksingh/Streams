package com.example.functional;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Timing {

    public static <T> T timed(String description,
                              Supplier<T> action) {
        return timed(description,
                System.out::print,
                action);
    }

    public static <T> T timed(String description,
                              Consumer<String> output,
                              Supplier<T> action) {
        final Date before = new Date();

        T result = action.get();

        final Long duration = new Date().getTime() - before.getTime();
        output.accept(description + " took " + duration + " milliseconds.");

        return result;
    }
}

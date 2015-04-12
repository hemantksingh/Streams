package com.example.functional;

import java.util.Random;

/**
 * Created by hkumar on 10/04/2015.
 */
public class RandomUtil {
    private static Random random = new Random();

    static <A> A randomElement(A[] as) {
        int randomStore = random.nextInt(as.length);
        return as[randomStore];
    }
}

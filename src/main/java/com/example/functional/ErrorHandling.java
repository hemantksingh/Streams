package com.example.functional;

import java.util.concurrent.Callable;

public class ErrorHandling {

    static <T> T uncheckCall(Callable<T> callable) {
        try { return callable.call(); }
        catch (Exception e) { return sneakyThrow(e); }
    }

    private static <T> T sneakyThrow(Exception e) {
        throw new RuntimeException(e);
    }

    static void uncheckRun(RunnableExc r) {
        try { r.run(); } catch (Exception e) { sneakyThrow(e); }
    }

    interface RunnableExc { void run() throws Exception; }
}

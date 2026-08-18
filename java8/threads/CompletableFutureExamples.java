package com.learning.java8.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Java 8 asynchronous pipelines with CompletableFuture. */
public final class CompletableFutureExamples {
    private CompletableFutureExamples() { }

    static void run() {
        System.out.println("\n--- CompletableFuture ---");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<String> customerFuture = CompletableFuture
                    .supplyAsync(() -> "ada", executor)
                    .thenApply(String::toUpperCase)
                    .thenApply(name -> "Customer: " + name);
            System.out.println(customerFuture.join());

            CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> 100, executor);
            CompletableFuture<Integer> quantity = CompletableFuture.supplyAsync(() -> 3, executor);
            int total = price.thenCombine(quantity, (p, q) -> p * q).join();
            System.out.println("Combined total = " + total);

            String fallback = CompletableFuture.<String>supplyAsync(
                    () -> { throw new IllegalStateException("Service unavailable"); }, executor)
                    .exceptionally(error -> "Fallback response")
                    .join();
            System.out.println("Error recovery = " + fallback);
        } finally {
            ExecutorAndFutureExamples.shutdownAndAwait(executor);
        }
    }
}

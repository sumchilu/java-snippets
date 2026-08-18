package com.learning.java8.threads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/** More Java 8 CompletableFuture patterns commonly used in service code. */
public final class AdvancedCompletableFutureExamples {
    private AdvancedCompletableFutureExamples() { }

    static void run() {
        System.out.println("\n--- Advanced CompletableFuture ---");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            thenComposeExample(executor);
            allOfExample(executor);
            anyOfExample(executor);
            errorHandlingExample(executor);
            manualCompletionExample();
        } finally {
            ExecutorAndFutureExamples.shutdownAndAwait(executor);
        }
    }

    /**
     * Use thenCompose when the next asynchronous action needs the previous
     * result. It flattens CompletableFuture<CompletableFuture<T>> into
     * CompletableFuture<T>.
     */
    private static void thenComposeExample(ExecutorService executor) {
        CompletableFuture<String> receipt = findCustomerId("ada", executor)
                .thenCompose(id -> fetchReceipt(id, executor));
        System.out.println("thenCompose receipt = " + receipt.join());
    }

    /**
     * Use allOf to wait for independent requests. allOf returns Void, so retain
     * the original futures if their values are needed afterwards.
     */
    private static void allOfExample(ExecutorService executor) {
        List<CompletableFuture<String>> nameFutures = Arrays.asList(
                CompletableFuture.supplyAsync(() -> "Ada", executor),
                CompletableFuture.supplyAsync(() -> "Grace", executor),
                CompletableFuture.supplyAsync(() -> "Linus", executor));

        CompletableFuture<List<String>> allNames = CompletableFuture
                .allOf(nameFutures.toArray(new CompletableFuture[0]))
                .thenApply(ignored -> nameFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
        System.out.println("allOf names = " + allNames.join());
    }

    /** Use anyOf when any acceptable response can be used, such as replica reads. */
    private static void anyOfExample(ExecutorService executor) {
        CompletableFuture<String> primary = CompletableFuture.supplyAsync(
                () -> {
                    pauseBriefly(); // simulate a slower remote replica
                    return "primary response";
                }, executor);
        CompletableFuture<String> replica = CompletableFuture.completedFuture("replica response");

        // anyOf has type CompletableFuture<Object>; cast only once its contract is known.
        String firstResponse = CompletableFuture.anyOf(primary, replica)
                .thenApply(result -> (String) result)
                .join();
        System.out.println("anyOf first response = " + firstResponse);
    }

    /** Compare exceptionally (fallback), handle (success or failure), and whenComplete (observation). */
    private static void errorHandlingExample(ExecutorService executor) {
        String fallback = failedRequest(executor)
                .exceptionally(error -> "default value")
                .join();
        System.out.println("exceptionally fallback = " + fallback);

        String handled = failedRequest(executor)
                .handle((value, error) -> error == null ? value : "recovered by handle")
                .join();
        System.out.println("handle result = " + handled);

        String observed = CompletableFuture.supplyAsync(() -> "successful value", executor)
                .whenComplete((value, error) ->
                        System.out.println("whenComplete observed value = " + value))
                .join();
        System.out.println("whenComplete preserves result = " + observed);
    }

    /** Useful when an external callback, listener, or test owns the completion. */
    private static void manualCompletionExample() {
        CompletableFuture<String> externalCallbackResult = new CompletableFuture<>();
        boolean completed = externalCallbackResult.complete("callback payload");
        System.out.println("manual complete = " + completed
                + ", result = " + externalCallbackResult.join());
    }

    private static CompletableFuture<Integer> findCustomerId(String username, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> 101, executor);
    }

    private static CompletableFuture<String> fetchReceipt(int customerId, ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> "receipt for customer " + customerId, executor);
    }

    private static CompletableFuture<String> failedRequest(ExecutorService executor) {
        return CompletableFuture.supplyAsync(() -> {
            throw new IllegalStateException("Remote service failed");
        }, executor);
    }

    private static void pauseBriefly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Simulated request was interrupted", e);
        }
    }
}

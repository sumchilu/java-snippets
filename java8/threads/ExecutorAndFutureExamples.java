package com.learning.java8.threads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/** ExecutorService, Runnable, Callable, Future, and proper executor shutdown. */
public final class ExecutorAndFutureExamples {
    private ExecutorAndFutureExamples() { }

    static void run() {
        System.out.println("\n--- ExecutorService and Future ---");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            // Runnable performs work but has no return value.
            Future<?> logTask = executor.submit(() ->
                    System.out.println("Runnable executed by " + Thread.currentThread().getName()));

            // Callable returns a result and can throw a checked exception.
            Callable<Integer> sumTask = () -> 20 + 22;
            Future<Integer> answer = executor.submit(sumTask);

            logTask.get(); // Wait for completion; the result is null for a Runnable.
            System.out.println("Callable result = " + answer.get());

            List<Callable<String>> tasks = Arrays.asList(
                    () -> "first result",
                    () -> "second result");
            for (Future<String> future : executor.invokeAll(tasks)) {
                System.out.println("invokeAll result = " + future.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // preserve the interruption request
            throw new RuntimeException("Task was interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("A task failed", e.getCause());
        } finally {
            shutdownAndAwait(executor);
        }
    }

    static void shutdownAndAwait(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

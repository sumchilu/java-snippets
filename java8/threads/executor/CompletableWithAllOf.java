package com.learning.java8.threads.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CompletableWithAllOf {
    static class Task implements Supplier<String> {

        public String get() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello World!"+ Thread.currentThread().getName();

        }

    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Task task = new Task();

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync( task, executor);
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync( task, executor);

        CompletableFuture<String>[] features =  new CompletableFuture[]{task1, task2};

        CompletableFuture.allOf(features).thenAccept(System.out::println);

        for (CompletableFuture<String> future : features) {
            String join = future.join();
            System.out.println("join = " + join);
        }
     executor.shutdown();
    }
}

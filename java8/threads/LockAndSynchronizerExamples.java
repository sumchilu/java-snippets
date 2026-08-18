package com.learning.java8.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Explicit locks and coordination with CountDownLatch. */
public final class LockAndSynchronizerExamples {
    private LockAndSynchronizerExamples() { }

    static void run() {
        System.out.println("\n--- Locks and synchronizers ---");
        Lock lock = new ReentrantLock();
        int[] protectedCounter = {0};

        lock.lock();
        try {
            protectedCounter[0]++;
        } finally {
            lock.unlock(); // Always release in finally.
        }
        System.out.println("lock-protected counter = " + protectedCounter[0]);

        CountDownLatch ready = new CountDownLatch(2);
        Thread first = new Thread(() -> {
            ready.countDown();
            System.out.println("first setup complete");
        });
        Thread second = new Thread(() -> {
            ready.countDown();
            System.out.println("second setup complete");
        });
        first.start();
        second.start();

        try {
            boolean completed = ready.await(1, TimeUnit.SECONDS);
            System.out.println("both setup tasks complete = " + completed);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Latch wait was interrupted", e);
        }
    }
}

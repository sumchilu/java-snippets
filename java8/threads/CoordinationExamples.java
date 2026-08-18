package com.learning.java8.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Synchronization aids for coordinating threads. These examples start short-lived
 * threads so their behavior is easy to see from the console.
 */
public final class CoordinationExamples {
    private CoordinationExamples() { }

    static void run() {
        System.out.println("\n--- Coordination aids ---");
        countDownLatchExample();
        cyclicBarrierExample();
        semaphoreExample();
        phaserExample();
        exchangerExample();
    }

    /**
     * Use CountDownLatch for a one-time gate: wait until a fixed number of jobs
     * finish (for example, application startup checks). It cannot be reset.
     */
    private static void countDownLatchExample() {
        CountDownLatch servicesReady = new CountDownLatch(2);
        Thread cache = start("cache-check", () -> {
            System.out.println("cache is ready");
            servicesReady.countDown();
        });
        Thread database = start("database-check", () -> {
            System.out.println("database is ready");
            servicesReady.countDown();
        });

        await(servicesReady, "startup checks");
        join(cache);
        join(database);
        System.out.println("application can now accept requests");
    }

    /**
     * Use CyclicBarrier when the same group must repeatedly meet at a phase
     * boundary, such as a multi-step simulation. It resets after every trip.
     */
    private static void cyclicBarrierExample() {
        CyclicBarrier barrier = new CyclicBarrier(2,
                () -> System.out.println("both workers reached the barrier"));

        Thread first = start("barrier-worker-1", () -> await(barrier, "worker 1"));
        Thread second = start("barrier-worker-2", () -> await(barrier, "worker 2"));
        join(first);
        join(second);
    }

    /**
     * Use Semaphore to cap concurrent access to a limited resource: database
     * licenses, third-party API slots, or a bounded pool. Unlike a lock, it has
     * multiple permits.
     */
    private static void semaphoreExample() {
        Semaphore apiSlots = new Semaphore(2);
        CountDownLatch allFinished = new CountDownLatch(3);

        for (int task = 1; task <= 3; task++) {
            final int taskNumber = task;
            start("api-task-" + taskNumber, () -> {
                boolean acquired = false;
                try {
                    apiSlots.acquire();
                    acquired = true;
                    System.out.println("API task " + taskNumber + " acquired a slot");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    if (acquired) {
                        apiSlots.release();
                    }
                    allFinished.countDown();
                }
            });
        }
        await(allFinished, "API tasks");
        System.out.println("semaphore permits available = " + apiSlots.availablePermits());
    }

    /**
     * Use Phaser when participant count can change between phases. It is useful
     * for staged workflows where tasks can join or leave dynamically.
     */
    private static void phaserExample() {
        Phaser phaser = new Phaser(2); // main and worker are registered before either can advance
        Thread worker = start("phaser-worker", () -> {
            System.out.println("phaser worker completed phase 0 work");
            phaser.arriveAndAwaitAdvance();
            System.out.println("phaser worker completed phase 1 work");
            phaser.arriveAndDeregister();
        });

        // Advance phase 0 with the worker, then unregister the main thread.
        phaser.arriveAndAwaitAdvance();
        System.out.println("main advanced to phase " + phaser.getPhase());
        phaser.arriveAndDeregister();
        join(worker);
    }

    /**
     * Use Exchanger when exactly two threads must swap data at a rendezvous
     * point, for example a producer and consumer swapping full/empty buffers.
     */
    private static void exchangerExample() {
        Exchanger<String> exchanger = new Exchanger<>();
        Thread producer = start("producer", () -> {
            try {
                String emptyBuffer = exchanger.exchange("filled buffer");
                System.out.println("producer received " + emptyBuffer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = start("consumer", () -> {
            try {
                String fullBuffer = exchanger.exchange("empty buffer");
                System.out.println("consumer received " + fullBuffer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        join(producer);
        join(consumer);
    }

    private static Thread start(String name, Runnable action) {
        Thread thread = new Thread(action, name);
        thread.start();
        return thread;
    }

    private static void await(CountDownLatch latch, String operation) {
        try {
            if (!latch.await(2, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Timed out while waiting for " + operation);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for " + operation, e);
        }
    }

    private static void await(CyclicBarrier barrier, String worker) {
        try {
            barrier.await(2, TimeUnit.SECONDS);
            System.out.println(worker + " continued after the barrier");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted at barrier", e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException("Barrier was broken", e);
        } catch (java.util.concurrent.TimeoutException e) {
            throw new RuntimeException("Timed out at barrier", e);
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while joining " + thread.getName(), e);
        }
    }
}

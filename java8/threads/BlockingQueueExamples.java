package com.learning.java8.threads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue is normally the simplest producer-consumer solution. It is
 * preferable to manually coordinating a queue with wait()/notify().
 */
public final class BlockingQueueExamples {
    private static final String STOP = "STOP";

    private BlockingQueueExamples() { }

    static void run() {
        System.out.println("\n--- BlockingQueue producer / consumer ---");
        BlockingQueue<String> workQueue = new ArrayBlockingQueue<>(2);

        Thread consumer = new Thread(() -> {
            try {
                String work;
                while (!(work = workQueue.take()).equals(STOP)) {
                    System.out.println("processed " + work);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "consumer");
        consumer.start();

        try {
            workQueue.put("invoice-1"); // waits when the bounded queue is full
            workQueue.put("invoice-2");
            workQueue.put(STOP);         // poison pill signals normal completion
            consumer.join(); //blocks main thread untill all threads finishes.
            System.out.println("Exited from main thread");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Producer was interrupted", e);
        }
    }

    public static void main(String[] args) {
        BlockingQueueExamples.run();
    }
}

package com.learning.java8.threads.synchronization;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerWithWaitAndNotify {
    private final Queue<Integer>  queue = new LinkedList<>();
    private final int capacity = 2;

    public synchronized void produce(int value) throws InterruptedException {
        while(queue.size() == capacity) {
            //wait should be called in loop, because of Spurious Wakeups or RaceCondition/Thread Stealing
            wait();
        }
        queue.add(value);
        System.out.println("Produced Value = " +  value);
        //wake up the waiting thread.
        notify();
    }
    public synchronized void consume() throws InterruptedException {
        while(queue.isEmpty()) {
            wait();
        }
        Integer value = queue.poll();
        System.out.println("Consumed Value = " + value);
        notify();
    }

    public static void main(String[] args) {
        ProducerConsumerWithWaitAndNotify dataHolder = new ProducerConsumerWithWaitAndNotify();
        Thread producer = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                try {
                    dataHolder.produce(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });

        Thread consumer = new Thread(() -> {
            for(int i = 0; i < 10; i++) {
                try {
                    dataHolder.consume();
                } catch (InterruptedException e) {}
            }
        });
        producer.start();
        consumer.start();
    }
}

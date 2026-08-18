package com.learning.java8.threads.synchronization;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWithCondition {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 2;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition producerCond = lock.newCondition();
    private final Condition consumerCond = lock.newCondition();

    public void produce(int value) throws InterruptedException {
        lock.lock();
        try{
            while(queue.size() == capacity) {
                producerCond.await();
            }
            queue.add(value);
            System.out.println("Produced Value = " +  value);
            //wakes up only the threads waiting for data. not all waiting threads.
            //in other words only wakes up waiting consumers. But wait wakes up all waiting threads.
            consumerCond.signal();
        }finally {
            lock.unlock();
        }

    }

    public void consume() throws InterruptedException {
        lock.lock();
        try{
            while(queue.isEmpty()) {
                consumerCond.await();
            }
            int value = queue.poll();
            System.out.println("Consumed Value = " +  value);
            //wakes up only the threads waiting for produce data. not all waiting threads.
            producerCond.signal();
        }finally {
            lock.unlock();
        }

    }
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumerWithCondition producerConsumer = new ProducerConsumerWithCondition();
        Thread producer = new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    producerConsumer.produce(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread consumer = new Thread(()->{
            for(int i=0;i<10;i++) {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {}
            }
        });
        producer.start();
        consumer.start();

    }
}

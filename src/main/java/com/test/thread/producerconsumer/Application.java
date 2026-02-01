package com.test.thread.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class Application {
    private static final Queue<String> queue = new LinkedList<>();
    public static void main(String[] args) {
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
    }
}

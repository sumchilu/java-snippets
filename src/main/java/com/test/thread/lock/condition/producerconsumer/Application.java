package com.test.thread.lock.condition.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Object.wait(), notify() only work for one condition.
// but using Condition class from lock, wait and relase lock based on number of conditions.
// calling condition.lock() should call within critcal section. i.e code between lock.lock() and lock.unlock()
//otherwise code throws IllegalMonitorStateException
public class Application {
    private static final Queue<String> queue = new LinkedList<>();
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    public static void main(String[] args) {
        Thread producer = new Thread(new Producer(queue, lock, condition));
        Thread consumer = new Thread(new Consumer(queue, lock, condition));
        producer.start();
        consumer.start();
    }
}

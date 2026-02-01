package com.test.thread.lock.readwrite;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReaderThread implements Runnable {
    private List<Integer> data;
    private Lock lock;
    public ReaderThread(List<Integer> data, ReentrantReadWriteLock.ReadLock readLock) {
        this.data = data;
        this.lock = readLock;
    }
    @Override
    public void run() {
        while (true) {
            try {
                readData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readData() throws InterruptedException {
        Thread.sleep(4000);
        lock.lock();
        System.out.println(Thread.currentThread().getName()+" data = " + data);
        lock.unlock();
    }
}

package com.test.thread.lock.readwrite;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WriterThread implements Runnable {
    private Lock lock;
    private List<Integer> list;
    public WriterThread(ReentrantReadWriteLock.WriteLock lock, List<Integer> list) {
        this.lock = lock;
        this.list = list;
    }
    @Override
    public void run() {
        while (true){
            try {
                writeData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeData() throws InterruptedException {
        Thread.sleep(10_000);
        lock.lock();
            int data = (int) (Math.random() * 10);
        System.out.println("Writing Data = "+ data);
        list.add(data);
        lock.unlock();
    }
}

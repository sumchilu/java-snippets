package com.test.thread.lock.readwrite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Application {
    private static final List<Integer> list = new ArrayList<>();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    public static void main(String[] args) {

        Thread writeThread = new Thread(new WriterThread((ReentrantReadWriteLock.WriteLock) lock.writeLock(), list));
        Thread reaThread1 = new Thread(new ReaderThread(list, (ReentrantReadWriteLock.ReadLock) lock.readLock()), "Reader1");
        Thread reaThread2 = new Thread(new ReaderThread(list, (ReentrantReadWriteLock.ReadLock) lock.readLock()), "Reader2");
        Thread reaThread3 = new Thread(new ReaderThread(list, (ReentrantReadWriteLock.ReadLock) lock.readLock()), "Reader3");
        Thread reaThread4 = new Thread(new ReaderThread(list, (ReentrantReadWriteLock.ReadLock) lock.readLock()), "Reader4");
        writeThread.start();
        reaThread1.start();
        reaThread2.start();
        reaThread3.start();
        reaThread4.start();
    }
}

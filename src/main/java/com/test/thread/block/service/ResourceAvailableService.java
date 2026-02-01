package com.test.thread.block.service;

import com.test.thread.block.resource.ObjectStoreResesource;

import java.util.concurrent.atomic.AtomicInteger;

public class ResourceAvailableService {
    ObjectStoreResesource objectStoreResesource = new ObjectStoreResesource();
    private AtomicInteger retryCount = new AtomicInteger(0);
    public boolean isObjectStoreResourceAvailable() {
        while( !objectStoreResesource.isResourceAvailable()){
            try {
                Thread.sleep(10 * 1000);
                System.out.println(retryCount.getAndIncrement() + " Retrying resource available...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Now Resource Available ");

        return objectStoreResesource.isResourceAvailable();
    }
}

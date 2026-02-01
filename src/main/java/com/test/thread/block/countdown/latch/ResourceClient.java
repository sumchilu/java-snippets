package com.test.thread.block.countdown.latch;

import java.util.concurrent.CountDownLatch;

public class ResourceClient {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ResouceCheck resouceCheck = new ResouceCheck(countDownLatch);
        System.out.println("Checking resource..");
        resouceCheck.isResouceAvailable();
        countDownLatch.await();
        System.out.println("Resource available now..");
        System.out.println("Exiting main");
    }
}

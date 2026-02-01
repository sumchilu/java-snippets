package com.test.thread.block.countdown.latch;

import java.util.concurrent.CountDownLatch;

public class ResouceCheck {
    private final CountDownLatch latch;
    public ResouceCheck(CountDownLatch latch) {
        this.latch = latch;
    }

    public boolean isResouceAvailable() {
        int count = 0;
        while(true && count < 5){
            try {
                Thread.sleep(10* 1000L);
                count++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        latch.countDown();

        return true;
    }
}

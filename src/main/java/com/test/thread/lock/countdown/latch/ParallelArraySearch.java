package com.test.thread.lock.countdown.latch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch can be used once, once the CountDownLatch value reached zero.
 * it can not be reset unlike semaphore.
 */
public class ParallelArraySearch {
    private static int[] data = {1,2,3,4,5,6,7,8,9};
    private static int numberOfThreads = 2;
    private static int elementToSearch = 5;
    private static  int elementFoundAtPosition = 0;

    private static CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);

    public static void main(String[] args) throws InterruptedException {

        int threadSlice = data.length/ numberOfThreads;

        for(int i=0; i< numberOfThreads; i++){
            Thread t = new Thread(new WorkerThread((i*threadSlice), (i+1)* threadSlice, elementToSearch));
            t.start();
        }
        countDownLatch.await();
        System.out.println("elementFoundAtPosition = " + elementFoundAtPosition);
    }

    static class WorkerThread implements Runnable {

        private final int left;
        private final int rigt;
        private final int elementToSearch;

        public WorkerThread(int left, int right, int elementToSearch) {
            this.left = left;
            this.rigt = right;
            this.elementToSearch = elementToSearch;
        }
        @Override
        public void run() {
            for (int i = left; i < rigt; i++) {
                if(data[i] == elementToSearch){
                    elementFoundAtPosition = i;
                    break;
                }
            }
            //If countDownLatch.countDown() not called, main thread will be blocked
            // until the countDownLatch value is zero.
            countDownLatch.countDown();
        }
    }

}

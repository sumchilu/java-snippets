package com.test.thread.block;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TreadPoolExceptionHandling {
    static boolean stopThread = false;
    static class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Exception Caught in DefaultUncaughtExceptionHandler:: ");
        }
    }
    static class DefaultThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler());
            return t;
        }
    }
    public static void main(String[] args) throws InterruptedException {

        final ExecutorService es = Executors.newFixedThreadPool(10, new DefaultThreadFactory());

        Runnable runnable = ()->{
            int i = 0;
            while(!stopThread){
                System.out.println("Running" + i++);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            es.execute(runnable);
           // es.execute(runnable);
        } catch (Exception e) {
            System.out.println("Exception caught");
        }

        System.out.println("Shutting down ExecutorService");
        es.shutdown();

        Thread.sleep(2000);
        System.out.println("StopThread to true");
        stopThread = true;
    }
}

package com.test.thread.block;

public class DaemonVsUserThread {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            System.out.println("Thread "+ Thread.currentThread().getName() + " Running");
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread About to End ");
        };

        //DaemonThread/JVM Ends when Main method exit.
        Thread deamonThread = new Thread(runnable);
        deamonThread.setName("DeamonThread");
        deamonThread.setDaemon(true);

        //UserThread is running, JVM will not exit unlike DaemonThread
        //JVM will wait until User Thread finishes it's task.
        Thread userThread = new Thread(runnable);
        userThread.setName("UserThread");
      //  deamonThread.start();
        userThread.start();
        System.out.println("Exiting from the Main");
    }
}

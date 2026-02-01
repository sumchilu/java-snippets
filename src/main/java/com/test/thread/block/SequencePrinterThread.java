package com.test.thread.block;


public class SequencePrinterThread implements Runnable {
    private static final Object lock = new Object();
    private static int turn = 1; // 1 for Thread1, 2 for Thread2, 3 for Thread3
    private final int id; // 1, 2, or 3
    private final int iterations;

    public SequencePrinterThread(int id, int iterations) {
        this.id = id;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int i = 0; i < iterations; ) {
            synchronized (lock) {
                if (turn == id) {
                    System.out.print(id + ",");
                    turn = (id % 3) + 1; // cycles 1->2->3->1
                    i++;
                    lock.notifyAll();
                } else {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 2; // number of repetitions

        Thread t1 = new Thread(new SequencePrinterThread(1, n));
        Thread t2 = new Thread(new SequencePrinterThread(2, n));
        Thread t3 = new Thread(new SequencePrinterThread(3, n));

        t1.start();
        t2.start();
        t3.start();
    }

}

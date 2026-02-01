package com.test.thread.producerconsumer;

import java.util.Queue;

public class Consumer implements Runnable {

    private Queue<String> queue;
    public Consumer(Queue<String> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        while(true){
            try {
                consume();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void consume() throws InterruptedException {
        synchronized (queue) {
            if (queue.isEmpty()) {
                System.out.println("Consumer is waiting for data");
                queue.wait();
            }
            Thread.sleep(700);
            String data = queue.poll();
            System.out.println("Consumer received data: " + data);
            if(queue.size() == 9){
                System.out.println("Consumer notifying Producer to produce");
                queue.notify();
            }
        }
    }
}

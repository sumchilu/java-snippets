package com.test.thread.producerconsumer;

import java.util.Queue;

public class Producer implements Runnable{

    private Queue<String> queue;

    public Producer(Queue<String> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        while(true){
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void produce() throws InterruptedException {
        synchronized (queue) {
            if(queue.size() ==10){
                System.out.println("Waiting for consumer to Consume");
                queue.wait();
            }
            Thread.sleep(1000);
            System.out.println("Producing message: "+ queue.size());
            queue.add("Data_"+queue.size());
            if(queue.size() == 1){
                System.out.println("Producer notifying Consumer to consume data");
                queue.notify();
            }
        }
    }
}

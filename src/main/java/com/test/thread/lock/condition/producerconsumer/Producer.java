package com.test.thread.lock.condition.producerconsumer;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//Object.wait(), notify() only work for one condition.
// but using Condition class from lock, wait and relase lock based on number of conditions.
// calling condition.lock() should call within critcal section. i.e code between lock.lock() and lock.unlock()
//otherwise code throws IllegalMonitorStateException
public class Producer implements Runnable{

    private Queue<String> queue;
    private Lock lock;
    private Condition condition;

    public Producer(Queue<String> queue, Lock lock, Condition condition) {
        this.queue = queue;
        this.lock = lock;
        this.condition = condition;
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
        lock.lock();
            if(queue.size() ==10){
                System.out.println("Waiting for consumer to Consume");
                condition.await();
            }
            Thread.sleep(1000);
            System.out.println("Producing message: "+ queue.size());
            queue.add("Data_"+queue.size());
            if(queue.size() == 1){
                System.out.println("Producer notifying Consumer to consume data");
                condition.signal();
            }
        lock.unlock();
    }
}

package com.test.thread.lock.condition.producerconsumer;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

//Object.wait(), notify() only work for one condition.
// but using Condition class from lock, wait and relase lock based on number of conditions.
// calling condition.lock() should call within critcal section. i.e code between lock.lock() and lock.unlock()
//otherwise code throws IllegalMonitorStateException
public class Consumer implements Runnable {

    private Queue<String> queue;
    private Lock lock;
    private Condition condition;

    public Consumer(Queue<String> queue, Lock lock, Condition condition) {
        this.queue = queue;
        this.lock = lock;
        this.condition = condition;
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
       // lock.lock();
            if (queue.isEmpty()) {
                System.out.println("Consumer is waiting for data");
                condition.await();
            }
            Thread.sleep(700);
            String data = queue.poll();
            System.out.println("Consumer received data: " + data);
            if(queue.size() == 9){
                System.out.println("Consumer notifying Producer to produce");
                condition.signal();
            }
       // lock.unlock();
    }
}

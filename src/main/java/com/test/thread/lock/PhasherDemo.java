package com.test.thread.lock;

import java.util.concurrent.Phaser;

public class PhasherDemo {
    private static final int[] data = {1,2,3,4,5,6,7,8};
    private static int SUM = 0;
    private static final Phaser phaser = new Phaser(1);
    public static void main(String[] args) {

        for(int i=0;i<data.length;i++){
            new Thread(new Worker(i)).start();
        }
        phaser.arriveAndAwaitAdvance(); //Wait for all threads complets its phose1. i.e double the each element.
        phaser.arriveAndAwaitAdvance(); //Wait for second phase to complete. i.e sum of all array members.

        System.out.println("Sum = "+ SUM);
        System.out.println("phaser = " + phaser.getPhase());

    }

    static class Worker implements Runnable{
        private final int threadIndex;

        public Worker(int threadIndex){
            this.threadIndex = threadIndex;
            phaser.register();
        }

        public void run() {
            //Phase1 double the value at index;
            data[threadIndex] = data[threadIndex] * 2;
            phaser.arriveAndAwaitAdvance();//wait for all threads doubled its value.

            if(threadIndex == 0){
                for(int i=0;i<data.length;i++){
                    SUM += data[i]; //Sum should be computed by the first thread and rest can be deregistred.
                }
                phaser.arriveAndDeregister(); // Finished next stage and update the main thread.

            }else{
                phaser.arriveAndDeregister(); //de register rest of the workers.
            }
        }
    }
}

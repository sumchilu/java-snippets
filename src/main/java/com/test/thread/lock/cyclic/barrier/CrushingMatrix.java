package com.test.thread.lock.cyclic.barrier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CrushingMatrix {
    private static int[][] matrix ={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,16}
    };

    private static int noOfCoumns = 4;
    private static final CyclicBarrier barrier = new CyclicBarrier(noOfCoumns, ()-> System.out.println("The Barrier is released"));

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Initial Matrix: "+ Arrays.deepToString(matrix));
        List<Thread> threads = new ArrayList<>();
        //Create Thread for each column.
        for( int i=0; i< 4; i++){
            Thread thread = new Thread(new WorkerThread(i));
            thread.start();
            threads.add(thread);
        }

        //Wait for all thread to finish to see final result.
        // without join, main thread will print current state of matrix
        // when it reach sysout.
        for(Thread thread : threads){
            thread.join();
        }

        System.out.println("Final Matrix:"+ Arrays.deepToString(matrix));
    }

    //Thread for a column
    static class WorkerThread implements Runnable{
        private int columnId;

        public WorkerThread(int columnId) {
            this.columnId = columnId;
        }

        @Override
        public void run() {
            int numberOfRows = 4;
            for(int i=1; i< numberOfRows; i++){
                int rowColumnSum = 0;
                //itrate through above row columns and find sum
                for(int j= 0; j< noOfCoumns; j++){
                    rowColumnSum = rowColumnSum + matrix[i-1][j];
                }
                matrix[i][columnId] = matrix[i][columnId] + rowColumnSum;
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }

            }
            System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        }
    }
}

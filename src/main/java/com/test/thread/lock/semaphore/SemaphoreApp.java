package com.test.thread.lock.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Difference between lock and semaphore is
 * code between lock.lock() and lock.unlock() is accessed by one thread.
 * code between semaphore.aquire() and semphore.relase()
 *   can be accessed by mulitiple permit threads mentioned in constructor.
 *
 * another difference is aquire() can be called from one thread and release can be
 *   called from another Thread.
 */
public class SemaphoreApp {
    //if permits are mentioned -ve value. Then First semaphore should call
    // release until value gets zero.
    private static Semaphore semaphore = new Semaphore(2);
    public static void main(String[] args) throws InterruptedException {

        Executor executor = new Executor();
        executor.submit(new Job(4000));
        executor.submit(new Job(5000));
        //This job will wait until one of the job completes
        // because Semaphore permits mentioned as 2.
        executor.submit(new Job(3000));

    }

    static class Executor{
        public void submit(Job job) throws InterruptedException {
            System.out.println("Launching Job: "+ job.getWork());
            //Aquired by main Thread
            semaphore.acquire();
              Thread thread = new Thread(()->{
                  try {
                      System.out.println("Executing Job: "+ job.getWork());
                      Thread.sleep(job.getWork());
                      System.out.println("Finished Executing Job: "+ job.getWork());
                      //Released by another thread.
                      semaphore.release();
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              });
              thread.start();
        }
    }

    static class Job{
        private int work;
        public Job(int work) {
            this.work = work;
        }

        public int getWork() {
            return work;
        }
    }
}

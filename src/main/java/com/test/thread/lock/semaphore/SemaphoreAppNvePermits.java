package com.test.thread.lock.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreAppNvePermits {
    //if permits are mentioned -ve value. Then First semaphore should call
    // release until value gets zero.
    private static Semaphore semaphore = new Semaphore(-2);
    public static void main(String[] args) throws InterruptedException {

        Executor executor = new Executor();
        executor.submit(new SemaphoreApp.Job(4000));
        executor.submit(new SemaphoreApp.Job(5000));
        //This job will wait until one of the job completes
        // because Semaphore permits mentioned as 2.
        executor.submit(new SemaphoreApp.Job(3000));

    }

    static class Executor{
        public void submit(SemaphoreApp.Job job) throws InterruptedException {
            System.out.println("Launching Job: "+ job.getWork());
            //If We call acquire, following code never executes. It should call release
            // because permits mentioned as -2
            //semaphore.acquire();
            semaphore.release();
            Thread thread = new Thread(()->{
                try {
                    System.out.println("Executing Job: "+ job.getWork());
                    Thread.sleep(job.getWork());
                    System.out.println("Finished Executing Job: "+ job.getWork());
                    //Released by another thread.
                    semaphore.acquire();
                    //If We call release, above code never executes. It should call acquire
                    // because permits mentioned as -2
                    //semaphore.release();
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

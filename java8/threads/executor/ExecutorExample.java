package com.learning.java8.threads.executor;

import java.util.concurrent.*;

public class ExecutorExample {
    static class FutureTask implements Callable<String> {
        String message;
        FutureTask(String message){
            this.message = message;
        }
        @Override
        public String call() throws Exception {
            return "Hi "+ message + "!";
        }
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + " is executing");
        });
        Future<String> test = executor.submit(new FutureTask("Test"));
        System.out.println("test.isDone() = " + test.isDone());
        //block current Thread until Future is completed.
        System.out.println("test.get() = " + test.get());
        executor.shutdown();

        Runnable task = () -> System.out.println(Thread.currentThread().getName() + " is executing");
        //Execute once after initial Delay of 3 seconds.
        scheduledExecutor.schedule (task, 3, TimeUnit.SECONDS);
        //Initial Delay of 1 second and repeat at every 2 seconds.
        scheduledExecutor.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
        scheduledExecutor.shutdown();
    }
}

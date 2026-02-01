package com.test.time;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class TimeOutTaskImpl {

    public static void main(String[] args)  {
       /* ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        ScheduledFuture<String> schedule = executorService.schedule(() -> {
            Thread.sleep(5 * 1000);
            return "Hi From Callable";
        }, 2, TimeUnit.SECONDS);

        System.out.println("schedule.get() = " + schedule.get());

        executorService.shutdown();*/


        String s = null;
        try {
            s = CompletableFuture.supplyAsync(() -> {
                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return "Hi From Callable";
                    }).get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout while executing task",e);
        }
        System.out.println("s = " + s);
    }
}

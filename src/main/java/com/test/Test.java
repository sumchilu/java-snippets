package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Test {
    Map<String, String> map = new HashMap<>();
    Map<String, List<String>> listMap =new HashMap<>();

    private AtomicBoolean valueFound = new AtomicBoolean(false);
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public Test(){

        map.put("1", "One");
        map.put("2", "Two");
        map.put("3", "Three");
        map.put("4", "Four");
    }
    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();

        Map<String, String> tmap = new HashMap<>(test.map);
            test.map.clear();

       /* tmap.values().stream().forEach(System.out::println);

        test.listMap.computeIfAbsent("Value", s -> new ArrayList<String>());
        test.listMap.get("Value").add("One");
        test.listMap.get("Value").add("Two");
        test.listMap.forEach((k,v)->{
            System.out.println("key=  " + k + " Value = "+ v);
        });*/


        System.out.println("test.testMethod() = " + test.testMethod());

        //test.executor.shutdown();
        System.out.println("Submitted Schedule Task");

    }

    private boolean testMethod(){

        long delay  = 1000L;
        long period = 1000L;
        executor.scheduleAtFixedRate(new MyScheduleTask(), 0, period, TimeUnit.MILLISECONDS);
        return valueFound.get();
    }

    private void myHandler(){
        if(valueFound.get()){
            System.out.println("SecretFound = ");
        }
        executor.shutdown();
    }
    class MyScheduleTask implements Runnable{

        int i =0;
        @Override
        public void run() {
            System.out.println("Executing Task");
            if( i++ == 4){
                synchronized (valueFound){
                    valueFound.set(true);
                    myHandler();
                }

            }
        }
    }
}

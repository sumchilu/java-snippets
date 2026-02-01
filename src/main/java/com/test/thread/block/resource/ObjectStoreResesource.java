package com.test.thread.block.resource;

public class ObjectStoreResesource {

    private volatile  boolean resourceAvailable = false;
    private long waitSec = 60;
    public ObjectStoreResesource(){
        Runnable runnable = () -> {
            while(true && !resourceAvailable){
                try {
                    Thread.sleep(waitSec * 1000L);
                    resourceAvailable = true;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public boolean isResourceAvailable(){
        return resourceAvailable;
    }

}

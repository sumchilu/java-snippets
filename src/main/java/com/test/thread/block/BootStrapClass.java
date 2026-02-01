package com.test.thread.block;

import com.test.thread.block.service.ResourceAvailableService;

public class BootStrapClass {
    public static void main(String[] args) {
        ResourceAvailableService service = new ResourceAvailableService();
        System.out.println("Checking isObjectStoreResourceAvailable in Main ");
        service.isObjectStoreResourceAvailable();
        System.out.println("isObjectStoreResourceAvailable Executed in Main "+ service.isObjectStoreResourceAvailable());
    }
}

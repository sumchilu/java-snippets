package com.test;

public class InstanceOrder {
    static {
        callMe(1    );
    }

    static int pk = callMe(2);

    {
        i= callMe(3);
    }

    int i = callMe(4);

    private Object obj = callMe(this);

    public InstanceOrder() { i = callMe(5); }

    private static int callMe(int pk) {
        System.out.println(pk);
        return pk;
    }

    private static Object callMe(Object obj) {
        System.out.println("obj = " + obj);
        return obj;
    }

    public static void main(String[] args) {
        InstanceOrder order = new InstanceOrder();
    }
}

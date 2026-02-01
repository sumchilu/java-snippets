package com.test.functional.callbacks;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {

        hello("firstName", "Ln",
                value->System.out.println("No Last name provided for " + value));

        //Other way of implement callback without arguments
        hello2("firstName", "Ln",
                ()->System.out.println("No Last name provided"));
    }

    static void hello(String fnName, String lastName, Consumer<String> callback) {
        if(lastName != null) {
            System.out.println("lastName = " + lastName);
        }else{
            callback.accept(lastName);
        }
    }

    static void hello2(String fnName, String lastName, Runnable callback) {
        if(lastName != null) {
            System.out.println("lastName = " + lastName);
        }else{
            callback.run();
        }
    }
}

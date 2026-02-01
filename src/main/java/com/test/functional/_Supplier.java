package com.test.functional;

import java.util.function.Supplier;

public class _Supplier {
    public static void main(String[] args) {
        System.out.println("getConnectionURL() = " + getConnectionURL());
        System.out.println("getConnectionURLSupplier = " + getConnectionURLSupplier.get());
    }

    //Declarative Supplier fuction
    static Supplier<String> getConnectionURLSupplier = ()-> "jdbc:postgresql://localhost:5432/postgres";
    //Imperative Supplier function
    static String getConnectionURL(){
        return "jdbc:postgresql://localhost:5432/postgres";
    }
}

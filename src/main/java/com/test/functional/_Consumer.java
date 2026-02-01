package com.test.functional;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class _Consumer {
    public static void main(String[] args) {
        Customer john = new Customer("John", "12334543435");
        greetCustomer(john);
     greetConsumer.accept(john);
     greetBiConsumer.accept(john, false);
    }

    static BiConsumer<Customer, Boolean> greetBiConsumer = (customer, showPhoneNumber) ->
            System.out.println("Hello "+ customer.name + "Thanks for registring phone number "
                    + (showPhoneNumber ? customer.phoneNumber: "**********"));
    static Consumer<Customer> greetConsumer = customer ->
            System.out.println("Hello "+ customer.name + "Thanks for registring phone number "
                    + customer.phoneNumber);
    //imperative Consumer function
    static void greetCustomer(Customer customer) {
        System.out.println("Hello "+ customer.name + "Thanks for registring phone number "
        + customer.phoneNumber);
    }
    static class Customer{
        private  final String name;
        private  final String phoneNumber;

        public Customer(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }
}

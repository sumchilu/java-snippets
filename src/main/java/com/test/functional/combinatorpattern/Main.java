package com.test.functional.combinatorpattern;

import java.time.LocalDate;

import static com.test.functional.combinatorpattern.CustomerRegistrationValidator.ValidationResult;
import static com.test.functional.combinatorpattern.CustomerRegistrationValidator.isAnAdult;
import static com.test.functional.combinatorpattern.CustomerRegistrationValidator.isAudult;
import static com.test.functional.combinatorpattern.CustomerRegistrationValidator.isEmailValid;
import static com.test.functional.combinatorpattern.CustomerRegistrationValidator.isPhoneNumberValid;
import static com.test.functional.combinatorpattern.CustomerRegistrationValidator.isValidEmail;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer(
                "Alice",
                "alice@gmail.com",
                "+089323234",
                LocalDate.of(2000, 1, 1));
        System.out.println("new CustomerValidatorService().isValid(customer) = " + new CustomerValidatorService().isValid(customer));

        ValidationResult result = isEmailValid()
                .and(isPhoneNumberValid())
                .and(isAnAdult())
                .apply(customer);

        //for static inline functions will be called like this
        ValidationResult result2 = isValidEmail.and(isAudult).apply(customer);

        if(result != ValidationResult.SUCCESS){
            throw new IllegalArgumentException(result.name());
        }

        if(result2 != ValidationResult.SUCCESS){
            throw new IllegalArgumentException(result2.name());
        }
    }
}

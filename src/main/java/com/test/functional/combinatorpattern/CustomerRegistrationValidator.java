package com.test.functional.combinatorpattern;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

/**
 * Combinator Designpattern validator
 */
public interface CustomerRegistrationValidator
        extends Function<Customer, CustomerRegistrationValidator.ValidationResult> {
    enum ValidationResult{
        SUCCESS,
        PHONE_NUMBER_NOT_VALID,
        EMAIL_NOT_VALID,
        IS_NOT_AN_ADULT
    }

    CustomerRegistrationValidator isValidEmail = customer -> customer.getEmail().contains("@")?
                ValidationResult.SUCCESS: ValidationResult.EMAIL_NOT_VALID;

    //or declare as a function body.
    //This is called a highOrder Function. A highOrder function takes one or more functions as parameter
    // or a function will return another function as a result.
    // This is an example of function return another function as a result.
    static CustomerRegistrationValidator isEmailValid() {
        System.out.println("isValidEmail = ");
        return customer -> customer.getEmail().contains("@")?
                ValidationResult.SUCCESS: ValidationResult.EMAIL_NOT_VALID;
    }


    static CustomerRegistrationValidator isPhoneNumberValid() {
        System.out.println("CustomerRegistrationValidator.isPhoneNumberValid");
        return customer -> customer.getPhoneNumber().startsWith("+0")
                ?ValidationResult.SUCCESS: ValidationResult.PHONE_NUMBER_NOT_VALID;

    }


    CustomerRegistrationValidator isAudult =
            customer -> Period.between(customer.getDateOfBirth(), LocalDate.now()).getYears() > 18?
                    ValidationResult.SUCCESS: ValidationResult.IS_NOT_AN_ADULT;
    static CustomerRegistrationValidator isAnAdult(){
        System.out.println("CustomerRegistrationValidator.isAnAdult");
        //Returns a Function<Customer, ValidationResult> implementation here.
        //This declaration as above inline function definition.
        return customer -> Period.between(customer.getDateOfBirth(), LocalDate.now()).getYears() > 18?
                ValidationResult.SUCCESS: ValidationResult.IS_NOT_AN_ADULT;
    }

    //This is called a highOrder Function. A highOrder function takes one or more functions as parameter
    // or a function will return another function as a result.
    // This is an example of function takes another function as argument and
    // function return another function as a result.
    default CustomerRegistrationValidator and(CustomerRegistrationValidator other) {
       return customer -> {
           ValidationResult result = this.apply(customer);
           return ValidationResult.SUCCESS.equals(result) ? other.apply(customer) : result;
       };
    }
}

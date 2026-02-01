package com.test.functional.combinatorpattern;

import java.time.LocalDate;
import java.time.Period;

public class CustomerValidatorService {
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("+0");
    }
    private boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public boolean isValid(Customer customer) {
        return isValidEmail(customer.getEmail())
                && isValidPhoneNumber(customer.getPhoneNumber())
                && isAdult(customer.getDateOfBirth());
    }
}

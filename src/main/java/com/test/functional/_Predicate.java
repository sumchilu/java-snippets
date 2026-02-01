package com.test.functional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class _Predicate {
    public static void main(String[] args) {
        String firstNumber = "91123456789";
        String secondNumber = "001123456789";
        System.out.println("isPhonenumberValid(\"91123456789\") = " + isPhonenumberValid(firstNumber));
        System.out.println("isPhonenumberValid(\"001123456789\") = " + isPhonenumberValid(secondNumber));
        System.out.println("isPhonenumberValidPredicate.test(firstNumber) = " + isPhonenumberValidPredicate.test(firstNumber));
        System.out.println("isPhonenumberValidPredicate.test(secondNumber) = " + isPhonenumberValidPredicate.test(secondNumber));

        //And Predicate
        System.out.println("isPhonenumberValidPredicate.and(containsNumber3).test(firstNumber) = " + isPhonenumberValidPredicate.and(containsNumber3).test(secondNumber));
        //or Predicate
        System.out.println("isPhonenumberValidPredicate.or(containsNumber3).test(secondNumber) = " + isPhonenumberValidPredicate.or(containsNumber3).test(secondNumber));

        Map<String, String> map = new HashMap<>();
        map.put("firstNumber", firstNumber);
        map.put("secondNumber", secondNumber);
        map.put("name", "name");
        map.entrySet().stream().filter(entry -> keyAndValueisSamePredicate.test(entry.getKey(), entry.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    static BiPredicate<String, String> keyAndValueisSamePredicate = (key, value) -> key.equals(value);
    static Predicate<String> containsNumber3 = phonenumber -> phonenumber.contains("3");
    static Predicate<String> isPhonenumberValidPredicate = phonenumber -> phonenumber.length() == 11 && phonenumber.startsWith("91");

    //Imperative Function
    static boolean isPhonenumberValid(String phonenumber) {
        return phonenumber.length() == 11 && phonenumber.startsWith("91");
    }
}

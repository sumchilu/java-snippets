package com.test.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

public class _Function {
    public static void main(String[] args) {


        int increment = increment(0);
        System.out.println("increment = " + increment);
        System.out.println("incrementByOne.apply(1) = " + incrementByOne.apply(1));
        Integer increment2 = incrementByOne.apply(1);
        Integer multiply = multiplyByTen.apply(increment2);
        System.out.println("multiply = " + multiply);

        //Instead of this Integer multiply = multiplyByTen.apply(increment2);
        //we can combine multiple functions as below.
        Integer andThenApplyFunction = incrementByOne.andThen(multiplyByTen).apply(1);
        System.out.println("andThenApplyFunction = " + andThenApplyFunction);

        System.out.println("incrementByOneAndMultiplyBiFunction = " + incrementByOneAndMultiply(2, 5));
        System.out.println("incrementByOneAndMultiplyBiFunction = " + incrementByOneAndMultiplyBiFunction.apply(2, 5));

    }

    //Declarative Function Definition
    static Function<Integer, Integer> incrementByOne = number -> number + 1;
    static Function<Integer, Integer> multiplyByTen = number -> number * 10;

    //imprerative Function.
    static int increment(int number){
        return number + 1;
    }

    //Declarative way to define a Bi-Function
    static BiFunction<Integer, Integer, Integer> incrementByOneAndMultiplyBiFunction = (number, multiplier) -> (number + 1) * multiplier;

    //Bi-Function Imperative Function
    static int incrementByOneAndMultiply(int number, int multiplier){
        return (number + 1) * multiplier;
    }
}

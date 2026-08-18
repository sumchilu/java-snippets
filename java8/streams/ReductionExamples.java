package com.learning.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

/** Use reduce only for associative combinations: sum, product, max, or a combined value. */
public final class ReductionExamples {
    private ReductionExamples() { }

    static void run() {
        System.out.println("\n--- Reductions ---");
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);

        int sum = numbers.stream().reduce(0, Integer::sum);
        int product = numbers.stream().reduce(1, (left, right) -> left * right);
        Optional<Integer> largest = numbers.stream().reduce(Integer::max);
        System.out.println("sum = " + sum + ", product = " + product
                + ", largest = " + largest.orElse(0));

        String sentence = "Streams make collection processing expressive and streams compose well";
        long wordCount = Arrays.stream(sentence.split("\\s+"))
                .map(word -> 1L)
                .reduce(0L, Long::sum);
        // In practice, count() expresses this intent more directly.
        long preferredWordCount = Arrays.stream(sentence.split("\\s+")).count();
        System.out.println("wordCount reduce = " + wordCount
                + ", wordCount count = " + preferredWordCount);

        // A reduction operator must be associative for correct parallel-stream behavior.
        BinaryOperator<String> joinWithComma = (left, right) -> left + ", " + right;
        String joined = Arrays.asList("Java", "Streams", "Reduce").stream()
                .reduce(joinWithComma).orElse("");
        System.out.println("joined = " + joined);
    }
}

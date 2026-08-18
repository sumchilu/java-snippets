package com.learning.java8.streams;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

/** Prefer IntStream/LongStream/DoubleStream for numeric aggregation. */
public final class PrimitiveStreamExamples {
    private PrimitiveStreamExamples() { }

    static void run() {
        System.out.println("\n--- Primitive streams ---");
        int[] scores = {72, 88, 91, 65, 88};

        IntSummaryStatistics stats = Arrays.stream(scores).summaryStatistics();
        System.out.println("average = " + stats.getAverage()
                + ", min = " + stats.getMin()
                + ", max = " + stats.getMax()
                + ", sum = " + stats.getSum());

        int sumOfSquares = Arrays.stream(scores)
                .map(score -> score * score)
                .sum();
        System.out.println("sumOfSquares = " + sumOfSquares);
    }
}

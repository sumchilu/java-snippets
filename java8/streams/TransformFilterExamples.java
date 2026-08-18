package com.learning.java8.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/** The most common pipeline stages: filter, map, flatMap, sorted, distinct, limit. */
public final class TransformFilterExamples {
    private TransformFilterExamples() { }

    static void run() {
        System.out.println("\n--- Transform and filter ---");
        List<String> words = Arrays.asList("java", "stream", "java", "lambda", "api");

        List<String> longUppercaseWords = words.stream()
                .filter(word -> word.length() >= 4)
                .map(String::toUpperCase)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        System.out.println("longUppercaseWords = " + longUppercaseWords);

        List<String> tags = Arrays.asList("java,streams", "collections,java", "testing");
        List<String> individualTags = tags.stream()
                .flatMap(tagLine -> Arrays.stream(tagLine.split(",")))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("individualTags = " + individualTags);
    }
}

package com.test.functional.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapExample {
    public static void main(String[] args) {
        List<List<String>> nestedList = List.of(
                List.of("1", "2", "3"),
                List.of("4", "5"),
                List.of("6", "7", "8", "9")
        );

        List<Integer> collect = nestedList.stream().map(x -> x.size()).collect(Collectors.toList());
        collect.forEach(System.out::println);
        //returns flatten all nested list data into the downstream.
        List<String> collect1 = nestedList.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
        collect1.forEach(System.out::println);

        List<User> users = Arrays.asList(
                new User("Alice", Arrays.asList("alice@work.com", "alice@personal.com")),
                new User("Bob", Arrays.asList("bob@company.com")),
                new User("Charlie", Arrays.asList("charlie@startup.com", "charlie@gmail.com"))
        );

        // Get all email addresses in one flat list
        List<String> allEmails = users.stream()
                .flatMap(user -> user.getEmailAddresses().stream())
                .collect(Collectors.toList());

        System.out.println("All email addresses: " + allEmails);

        List<String> sentences = Arrays.asList(
                "Hello world Java",
                "hello world Python"
        );

        // Split each sentence into words and flatten into a single list
        List<String> allWords = sentences.stream()
                .flatMap(line -> Stream.of(line.split(" ")))
                .collect(Collectors.toList());

        System.out.println("All words: " + allWords);

        Map<String, Long> wordCount = sentences.stream()
                .flatMap(line -> Stream.of(line.split(" ")))
                .map(word -> word.toLowerCase())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        wordCount.entrySet().stream().forEach(System.out::println);

    }

    static class User{
        private String name;
        private List<String> emailAddresses;

        public User(String name, List<String> emailAddresses) {
            this.name = name;
            this.emailAddresses = emailAddresses;
        }

        public List<String> getEmailAddresses() {
            return emailAddresses;
        }
    }
}

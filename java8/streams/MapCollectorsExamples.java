package com.learning.java8.streams;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Collectors that turn a stream into a Map. */
public final class MapCollectorsExamples {
    private MapCollectorsExamples() { }

    static void run() {
        System.out.println("\n--- Map collectors ---");
        List<Person> people = Arrays.asList(
                new Person(1, "Ada", "Engineering", 120),
                new Person(2, "Linus", "Engineering", 150),
                new Person(3, "Grace", "Research", 140),
                new Person(4, "Ada", "Research", 130));

        // toMap(keyMapper, valueMapper): IDs are unique, so no collision policy is needed.
        Map<Integer, String> namesById = people.stream().collect(
                Collectors.toMap(Person::getId, Person::getName));
        System.out.println("namesById = " + namesById);

        // Duplicate names require a merge function. Here we retain the higher salary.
        Map<String, Person> highestPaidByName = people.stream().collect(
                Collectors.toMap(Person::getName, Function.identity(),
                        (left, right) -> left.getSalary() >= right.getSalary() ? left : right));
        System.out.println("highestPaidByName = " + highestPaidByName);

        // Supply LinkedHashMap when encounter/insertion order matters.
        Map<Integer, String> orderedNamesById = people.stream().collect(
                Collectors.toMap(Person::getId, Person::getName,
                        (left, right) -> left, LinkedHashMap::new));
        System.out.println("orderedNamesById = " + orderedNamesById);

        // Alternative without streams: clearer for complex imperative logic.
        Map<Integer, String> loopVersion = new LinkedHashMap<>();
        for (Person person : people) {
            loopVersion.put(person.getId(), person.getName());
        }
        System.out.println("loopVersion = " + loopVersion);
    }

    public static final class Person {
        private final int id;
        private final String name;
        private final String department;
        private final int salary;

        public Person(int id, String name, String department, int salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public int getId() { return id; }
        String getName() { return name; }
        String getDepartment() { return department; }
        int getSalary() { return salary; }

        @Override
        public String toString() {
            return name + "(" + salary + ")";
        }
    }
}

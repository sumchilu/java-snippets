package com.learning.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** groupingBy, partitioningBy, and downstream collectors. */
public final class GroupingCollectorsExamples {
    private GroupingCollectorsExamples() { }

    static void run() {
        System.out.println("\n--- Grouping and partitioning ---");
        List<MapCollectorsExamples.Person> people = Arrays.asList(
                new MapCollectorsExamples.Person(1, "Ada", "Engineering", 120),
                new MapCollectorsExamples.Person(2, "Linus", "Engineering", 150),
                new MapCollectorsExamples.Person(3, "Grace", "Research", 140));

        Map<String, List<MapCollectorsExamples.Person>> byDepartment = people.stream()
                .collect(Collectors.groupingBy(MapCollectorsExamples.Person::getDepartment));
        System.out.println("byDepartment = " + byDepartment);

        Map<String, Long> employeeCountByDepartment = people.stream().collect(
                Collectors.groupingBy(MapCollectorsExamples.Person::getDepartment,
                        Collectors.counting()));
        System.out.println("employeeCountByDepartment = " + employeeCountByDepartment);

        Map<String, Integer> totalSalaryByDepartment = people.stream().collect(
                Collectors.groupingBy(MapCollectorsExamples.Person::getDepartment,
                        Collectors.summingInt(MapCollectorsExamples.Person::getSalary)));
        System.out.println("totalSalaryByDepartment = " + totalSalaryByDepartment);

        Map<Boolean, List<MapCollectorsExamples.Person>> salaryBands = people.stream()
                .collect(Collectors.partitioningBy(person -> person.getSalary() >= 140));
        System.out.println("salary >= 140 = " + salaryBands);
    }
}

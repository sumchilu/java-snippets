package com.learning.java8.streams.test;


import java.util.List;
import java.util.stream.Collectors;

public class GrouppingExample {
    public static void main(String[] args) {

        List<Person> list = List.of((new Person(1, "Test1", "Acc", 2000)),
                (new Person(2, "Test2", "Finance", 5000)));


        list.stream().collect(Collectors.groupingBy(Person::getId ) );
        list.stream().collect(Collectors.groupingBy(Person::getDepartment, Collectors.summingInt(Person::getSalary) ) );
        list.stream().collect(Collectors.groupingBy(Person::getDepartment , Collectors.counting() ));
    }

    static class Person {
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

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public int getSalary() {
            return salary;
        }
    }
}

package com.test.functional.streams;


import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class _Streams {
    public static void main(String[] args) {

        List<Person> people = List.of(
                new Person("Maria", Gender.FEMALE),
                new Person("Aisha", Gender.FEMALE),
                new Person("Alice", Gender.FEMALE),
                new Person("john", Gender.MALE),
                new Person("Alex", Gender.MALE)
        );

        people.stream()
                .map(person -> person.gender)
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        Function<Person, String> personStringFunction = person -> person.name;
        ToIntFunction<String> length = String::length;
        IntConsumer println = System.out::println;
        people.stream()
                .map(personStringFunction)
                .mapToInt(length)
                .forEach(println);


        //return false
        boolean allMatch = people.stream()
                .allMatch(person -> person.gender == Gender.FEMALE);
        //return true
        people.stream()
                .anyMatch(person -> person.gender == Gender.FEMALE);
        System.out.println("Male People");

        //dropWhile remove the elements until the condition false
        // once condition false, it will return from that element to
        // rest of the element. in people list MALE are added the
        // end hence we got only male people, if one male person added at zero
        // then entire list will come, it will not drop any values.
        List<Person> collect = people.stream()
                .dropWhile(person -> person.gender.equals(Gender.FEMALE))
                .collect(Collectors.toList());

        collect.forEach(System.out::println);

    }

    static class Person {
        private final String name;
        private final Gender gender;

        public Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", gender=" + gender +
                    '}';
        }
    }
    enum Gender{
        MALE,FEMALE;
    }
}

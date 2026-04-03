package com.test.lang;

import java.util.Collections;
import java.util.List;

public class TestMultiLineString {
    public static void main(String[] args) {
        //indentation of start of the string should match with variable declaration.
        // otherwise it will add empty string to the final string.
        String s = """
                This is a multiline String
                How Are you?""";

        System.out.println("s = '" + s+ "'");

        List<String> data = List.of("One", "Two", "Three");
        String join = String.join(",", Collections.nCopies(data.size(), "?"));

        System.out.println("join = " + join);
    }
}

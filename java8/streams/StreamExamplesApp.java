package com.learning.java8.streams;

import com.learning.java8.threads.ThreadExamplesApp;

/** Runs every small example. Execute with: mvn compile exec:java */
public final class StreamExamplesApp {
    private StreamExamplesApp() { }

    public static void main(String[] args) {
//        MapCollectorsExamples.run();
//        GroupingCollectorsExamples.run();
        ReductionExamples.run();
//        TransformFilterExamples.run();
//        PrimitiveStreamExamples.run();
//        ThreadExamplesApp.run();
    }
}

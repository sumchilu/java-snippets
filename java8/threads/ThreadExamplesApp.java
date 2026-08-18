package com.learning.java8.threads;

/** Runs the java.util.concurrent examples. */
public final class ThreadExamplesApp {
    private ThreadExamplesApp() { }

    public static void run() {
        ExecutorAndFutureExamples.run();
        CompletableFutureExamples.run();
        AdvancedCompletableFutureExamples.run();
        ConcurrentCollectionsExamples.run();
        LockAndSynchronizerExamples.run();
        CoordinationExamples.run();
        BlockingQueueExamples.run();
    }
}

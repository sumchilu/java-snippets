package com.learning.java8.threads;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/** Thread-safe collections and atomic updates without explicit locking. */
public final class ConcurrentCollectionsExamples {
    private ConcurrentCollectionsExamples() { }

    static void run() {
        System.out.println("\n--- Concurrent collections and atomics ---");
        ConcurrentMap<String, Integer> visitsByPage = new ConcurrentHashMap<>();

        // Atomic per-key update. Safer than a separate get(), increment(), put() sequence.
        visitsByPage.merge("/home", 1, Integer::sum);
        visitsByPage.merge("/home", 1, Integer::sum);
        visitsByPage.computeIfAbsent("/help", ignored -> 0);
        System.out.println("visitsByPage = " + visitsByPage);

        ConcurrentLinkedQueue<String> events = new ConcurrentLinkedQueue<>();
        events.offer("created");
        events.offer("validated");
        System.out.println("first event = " + events.poll() + ", remaining = " + events);

        AtomicInteger counter = new AtomicInteger();
        counter.incrementAndGet();
        counter.addAndGet(4);
        System.out.println("atomic counter = " + counter.get());
    }
}

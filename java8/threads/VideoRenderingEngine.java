package com.learning.java8.threads;

import java.util.concurrent.Phaser;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoRenderingEngine {

    public static void main(String[] args) {
        // Create a phaser with a custom onAdvance behavior to monitor phase transitions
        Phaser videoPhaser = new Phaser() {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("\n--- [SYSTEM] Phase " + phase + " Completed successfully. Registered workers: " + registeredParties + " ---");
                // Terminate phaser if there are no more active workers left
                return registeredParties == 0;
            }
        };

        // Thread pool to handle parallel video processing
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Imagine we are processing a batch of 3 video frames
        int totalFrames = 3;

        for (int i = 1; i <= totalFrames; i++) {
            final int frameId = i;

            // Dynamically register a new worker party for this frame
            videoPhaser.register();

            executor.submit(() -> {
                try {
                    // PHASE 0: Decode Frame
                    System.out.println("Frame " + frameId + ": Decoding raw bytes...");
                    Thread.sleep((long) (Math.random() * 500)); // Simulate work
                    videoPhaser.arriveAndAwaitAdvance(); // Sync Point 0

                    // PHASE 1: Apply Filters
                    System.out.println("Frame " + frameId + ": Applying color grading filters...");
                    Thread.sleep((long) (Math.random() * 500)); // Simulate work
                    videoPhaser.arriveAndAwaitAdvance(); // Sync Point 1

                    // PHASE 2: Compress and Encode Frame
                    System.out.println("Frame " + frameId + ": Encoding to MP4 format...");
                    Thread.sleep((long) (Math.random() * 500)); // Simulate work

                    // Task fully done. Deregister frame thread from the phaser
                    System.out.println("Frame " + frameId + ": Processing complete.");
                    videoPhaser.arriveAndDeregister();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // Shutdown executor gracefully once all tasks exit
        executor.shutdown();
    }
}


package com.test.thread.lock;

import java.util.concurrent.Phaser;

/**
 * Phaser
 */
class RaceParticipant implements Runnable {
    private final Phaser phaser;

    public RaceParticipant(Phaser phaser) {
        this.phaser = phaser;
        this.phaser.register(); // Register the participant
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": Reaching Checkpoint 1");
        System.out.println("Thread phaser = " + phaser.getPhase());
        phaser.arriveAndAwaitAdvance(); // Wait for others at Checkpoint 1

        System.out.println(Thread.currentThread().getName() + ": Reaching Checkpoint 2");
        System.out.println("Thread phaser = " + phaser.getPhase());
        phaser.arriveAndAwaitAdvance(); // Wait for others at Checkpoint 2

        System.out.println(Thread.currentThread().getName() + ": Finished the race!");
        System.out.println("Thread phaser = " + phaser.getPhase());
        phaser.arriveAndDeregister(); // Done with all phases, deregister
    }
}

public class RaceSimulation {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(1); // Register the main thread initially
        int numRacers = 4;

        for (int i = 0; i < numRacers; i++) {
            new Thread(new RaceParticipant(phaser), "Racer " + (i + 1)).start();
        }

        System.out.println("Race starting...");
        System.out.println("phaser = " + phaser.getPhase());
        phaser.arriveAndAwaitAdvance(); // Main thread waits for all racers to register and reach Checkpoint 1

        System.out.println("Phase 1 complete. Proceed to Phase 2 (Checkpoint 2)");
        phaser.arriveAndAwaitAdvance(); // Main thread waits for all racers to reach Checkpoint 2

        System.out.println("Phase 2 complete. Proceed to finish line.");
        phaser.arriveAndDeregister(); // Main thread is done coordinating

        System.out.println("Race finished!");
    }
}


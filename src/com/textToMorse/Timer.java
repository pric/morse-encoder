package com.textToMorse;

public class Timer {

    private long startTime;
    private long endTime;

    public void start() {

        startTime = System.nanoTime();
    }

    public void stop() {

        endTime = System.nanoTime();

        final long executionTime = endTime - startTime;

        System.out.println(String.format("\n\n The execution took %s ms", (executionTime/1000000f)));
    }
}

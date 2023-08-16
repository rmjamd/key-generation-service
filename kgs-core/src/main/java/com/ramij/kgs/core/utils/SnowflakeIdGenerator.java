package com.ramij.kgs.core.utils;

public class SnowflakeIdGenerator {

    private static final int TIMESTAMP_BITS = 41;
    private static final int WORKER_BITS = 10;
    private static final int SEQUENCE_BITS = 12;
//    private static final int SIGN_BIT = 1;

    private static final long MAX_WORKER_ID = (1L << WORKER_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private static final long EPOCH = 1630000000000L; // Customize this to your desired epoch (in milliseconds)

    private static long lastTimestamp = -1L;
    private static long sequence = 0L;
    private SnowflakeIdGenerator(){}


    public static synchronized long generateId(long givenWorkerId) {
        if (givenWorkerId < 0 || givenWorkerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("Worker ID must be between 0 and " + MAX_WORKER_ID);
        }
        long currentTimestamp = System.currentTimeMillis() - EPOCH;

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - currentTimestamp) + " milliseconds.");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Sequence exhausted for the current millisecond
                // Wait until the next millisecond
                currentTimestamp = waitNextMillis(currentTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        long signBit = 0L; // Assuming positive numbers
        return (signBit << (TIMESTAMP_BITS + WORKER_BITS + SEQUENCE_BITS))
                | (currentTimestamp << (WORKER_BITS + SEQUENCE_BITS))
                | (givenWorkerId << SEQUENCE_BITS)
                | sequence;

    }

    private static long waitNextMillis(long lastTimestamp) {
        long currentTimestamp = System.currentTimeMillis() - EPOCH;
        while (currentTimestamp <= lastTimestamp) {
            currentTimestamp = System.currentTimeMillis() - EPOCH;
        }
        return currentTimestamp;
    }

}


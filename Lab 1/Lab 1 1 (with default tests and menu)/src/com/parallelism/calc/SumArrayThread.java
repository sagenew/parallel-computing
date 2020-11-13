package com.parallelism.calc;

import java.util.stream.IntStream;

public class SumArrayThread extends Thread {
    private int [][] array;
    private long sum;
    SumArrayThread(int [][] array) {
        this.array = array;
    }

    public long getSum() {
        return sum;
    }

    @Override
    public void run() {
        long tempSum = 0;
        for (int[] i : array) {
            tempSum += IntStream.of(i).sum();
        }
        this.sum = tempSum;
    }
}

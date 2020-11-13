package com.parallelism.calc;

import java.util.stream.IntStream;

public class SerialCalc {
    final int[][] array;
    final int n;
    final int m;
    long sum;
    long calcTime;
    public SerialCalc(int [][] array, int n, int m) {
        this.array = array;
        this.n = n;
        this.m = m;
        this.sum = 0;
        this.calcTime = 0;
    }

    public void calc() {
        long startTime = System.nanoTime();
        int tempSum = 0;
        for (int i = 0; i < n; i++) {
            tempSum += IntStream.of(array[i]).sum();
        }
        long endTime = System.nanoTime();

        this.sum = tempSum;
        this.calcTime = endTime - startTime;
    }

    public void print() {
        System.out.println("- Sum of numbers in the array : " + this.sum);
        System.out.println("- Total calculation time : " + this.calcTime + "ns");
    }


    public long getSum() {
        return sum;
    }

    public long getCalcTime() {
        return calcTime;
    }
}

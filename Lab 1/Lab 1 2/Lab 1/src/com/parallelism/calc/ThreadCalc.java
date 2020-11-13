package com.parallelism.calc;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ThreadCalc extends SerialCalc {
    private final int threadCount;

    public ThreadCalc(int[][] array, int n, int m, int threadCount) {
        super(array, n, m);
        this.threadCount = threadCount;
    }

    @Override
    public void calc() {
        long startTime = System.nanoTime();
        int [] threadArraySizes = new int[threadCount];
        float tempN = n;
        float span;
        int arraySize;

        for (int i = 0; i < threadCount; i++) {
            span = tempN / (float) (threadCount - i);
            arraySize = (int) Math.ceil(span);
            threadArraySizes[i] = arraySize;
            tempN -= arraySize;
        }

        SumArrayThread[] threadArray = new SumArrayThread[threadCount];
        int [][] arraysToCalc;
        int iterN = 0;
        for(int i = 0; i < threadCount; i++) {
            arraysToCalc = new int[threadArraySizes[i]][this.m];
            for (int j = 0; j < arraysToCalc.length; j++) {
                arraysToCalc[j] = this.array[iterN];
                iterN++;
            }
            threadArray[i] = new SumArrayThread(arraysToCalc);
            threadArray[i].start();
        }

        for( int i = 0; i < threadCount; i++) {
            try {
                threadArray[i].join();
            } catch (InterruptedException ignored) { }
        }

        long sum = 0;
        for( int i = 0; i < threadCount; i++ ) {
            sum += threadArray[i].getSum();
        }
        long endTime = System.nanoTime();
        this.sum = sum;
        this.calcTime = endTime - startTime;

    }
}

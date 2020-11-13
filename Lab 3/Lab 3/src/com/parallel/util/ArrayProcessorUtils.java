package com.parallel.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ArrayProcessorUtils {
    ExecutorService executorService = Executors.newCachedThreadPool();
    private final BiFunction<Integer,Integer,Integer> iterateCounter = (x, y) -> y + 1;
    private final BiFunction<Integer,Integer,Integer> sum = Integer::sum;
    private final BiFunction<Integer,Integer,Integer> xor = (x, y) -> x ^ y;
    private final BiPredicate<Integer, Integer> min = (x, y) -> x < y;
    private final BiPredicate<Integer, Integer> max = (x, y) -> x > y;


    public int sumElements(int [] array) {
        AtomicInteger atomicSum = new AtomicInteger();
        IntStream.of(array)
                .parallel()
                .forEach(element -> processElementByFunction(element, atomicSum, sum));
        return atomicSum.get();
    }

    public int countElementsByPredicate(int [] array, Predicate<Integer> predicate) {
        AtomicInteger atomicCounter = new AtomicInteger();
        IntStream.of(array)
                .parallel()
                .filter(predicate::test)
                .forEach(element -> processElementByFunction(element, atomicCounter, iterateCounter));
        return atomicCounter.get();
    }

    public int calculateCheckSum(int [] array) {
        AtomicInteger atomicCheckSum = new AtomicInteger();
        IntStream.of(array)
                .parallel()
                .forEach(element -> processElementByFunction(element, atomicCheckSum, xor));
        return atomicCheckSum.get();
    }

    public int [][] findMinMaxElements (int [] array) {
        AtomicInteger minIndex = new AtomicInteger();
        AtomicInteger maxIndex = new AtomicInteger();

        IntStream.range(0, array.length)
                .parallel()
                .forEach(index -> updateMinMax(array, index, minIndex, maxIndex));
        return new int[][] {{minIndex.get(), array[minIndex.get()]},{maxIndex.get(), array[maxIndex.get()]}};
    }

    private void processElementByFunction(int element, AtomicInteger result, BiFunction<Integer, Integer, Integer> function) {
        int oldValue;
        int newValue;

        do {
            oldValue = result.get();
            newValue = function.apply(element, oldValue);
        } while (!result.compareAndSet(oldValue, newValue));
    }

    private void updateMinMax(int[] array, int currentIndex, AtomicInteger minIndex, AtomicInteger maxIndex) {
        updateIndexByPredicate(array, currentIndex, minIndex, min);
        updateIndexByPredicate(array, currentIndex, maxIndex, max);
    }

    private void updateIndexByPredicate(int[] array, int currentIndex, AtomicInteger result, BiPredicate<Integer, Integer> predicate) {
        int oldIndex;
        do {
            oldIndex = result.get();
        } while (predicate.test(array[currentIndex], array[oldIndex])
                && !result.compareAndSet(oldIndex, currentIndex));
    }


}

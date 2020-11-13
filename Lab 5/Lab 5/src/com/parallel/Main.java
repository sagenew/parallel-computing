package com.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    /* Варіант 8 : Создать 2 массива (или коллекции) со случайными числами. В первом массиве —
    оставить элементы которые больше среднего значения массива, во втором —
    меньше. Отсортировать массивы и слить в один отсортированный массив те
    элементы, которые есть и в первом и во втором массиве. */

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<ArrayList<Integer>> firstFuture = CompletableFuture
                .supplyAsync(() -> Utils.getNewArray(500))
                .thenApply(Utils::deleteIfLessThanAverage)
                .thenApply(Utils::sortArrayAscending);
        System.out.println(firstFuture.get());

        CompletableFuture<ArrayList<Integer>> secondFuture = CompletableFuture
                .supplyAsync(() -> Utils.getNewArray(500))
                .thenApply(Utils::deleteIfMoreThanAverage)
                .thenApply(Utils::sortArrayAscending);
        System.out.println(secondFuture.get());

        CompletableFuture<ArrayList<Integer>> result = firstFuture
                .thenCombine(secondFuture, Utils.intersection)
                .thenApply(Utils::sortArrayAscending);
        System.out.println(result.get());

    }
}

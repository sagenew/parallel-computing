package com.parallel.lab;

import com.parallel.util.ArrayProcessorUtils;

public class ArrayProcessor {
    private ArrayProcessorUtils utils;
    private final int arraySize;
    private int [] array;

    public ArrayProcessor(int arraySize) {
        this.arraySize = arraySize;
        utils = new ArrayProcessorUtils();
        array = new int[arraySize];
    }

    public void initArray() {
        for (int i = 0; i < arraySize; i++) {
            array[i] = (int)((Math.random() * 100000));
        }
    }

    public void startProcessing() {
        System.out.println("Start initialisation.");
        initArray();
        System.out.println("Initialisation completed, sum of elements in the array : " + utils.sumElements(array) + "\n");

        System.out.println("1) Count elements by predicate : ");
        System.out.println("x % 3 == 0 : " + utils.countElementsByPredicate(array, x -> x %  3 == 0) + " elements.");
        System.out.println("x % 5 == 0 : " + utils.countElementsByPredicate(array, x -> x %  5 == 0) + " elements.");
        System.out.println("x > 5000   : " + utils.countElementsByPredicate(array, x -> x >  5000  ) + " elements.");
        System.out.println("x <=1000    : " + utils.countElementsByPredicate(array, x -> x <= 1000 ) + " elements." + "\n");

        System.out.println("2) Find min and max elements with indexes :");
        int [][] results = utils.findMinMaxElements(array);
        System.out.println("MIN element : " + results[0][1] + ", index : " + results[0][0]);
        System.out.println("MAX element : " + results[1][1] + ", index : " + results[1][0] + "\n");

        System.out.println("3) Calculate checksum of the array : " + utils.calculateCheckSum(array) + "\n");

        System.out.println("Гуменчук Артур, ІП-71, лабораторна робота №3");
    }

}

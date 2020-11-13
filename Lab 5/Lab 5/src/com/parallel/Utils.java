package com.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiFunction;

public class Utils {
    public static ArrayList<Integer> getNewArray(int capacity) {
        ArrayList<Integer> array = new ArrayList<>();
        Integer randInt;
        for (int i = 0; i < capacity; i++) {
            do {
                randInt = getNewRandomInteger();
            } while (array.contains(randInt));
            array.add(randInt);
        }
        return array;
    }

    public static ArrayList<Integer> deleteIfLessThanAverage (ArrayList<Integer> array) {
        Integer sum = 0;
        for (Integer i : array) sum += i;
        Integer avg = sum / array.size();
        array.removeIf(i -> i < avg);
        return array;
    }

    public static ArrayList<Integer> deleteIfMoreThanAverage (ArrayList<Integer> array) {
        Integer sum = 0;
        for (Integer i : array) sum += i;
        Integer avg = sum / array.size();
        array.removeIf(i -> i > avg);
        return array;
    }

    public static ArrayList<Integer> sortArrayAscending (ArrayList<Integer> array) {
        Collections.sort(array);
        return array;
    }

    public static BiFunction < ArrayList<Integer>, ArrayList<Integer>, ArrayList<Integer> > intersection =
            (array1, array2) -> {
                ArrayList<Integer> mergedArray = new ArrayList<>();
                for(Integer i : array1) if(array2.contains(i)) mergedArray.add(i);
                for(Integer i : array2) if((array1.contains(i)) && !array2.contains(i)) mergedArray.add(i);
                return mergedArray;
            };

    private static Integer getNewRandomInteger() {
        return (int)(Math.random() * 999 + 1);
    }
}

package com.parallel;

import com.parallel.lab.ArrayProcessor;

public class Main {

    private static final int ARRAY_SIZE = 1000;

    public static void main(String[] args) {
        ArrayProcessor arrayProcessor = new ArrayProcessor(ARRAY_SIZE);
        arrayProcessor.startProcessing();
    }
}

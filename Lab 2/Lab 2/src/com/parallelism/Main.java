package com.parallelism;

public class Main {

    public static void main(String[] args) {
        int numberOfTasks = 100;
        int numberOfCPUs = 2;
        int numberOfProcesses = 1;
        int generationIntervalLowerLimit = 100;
        int generationIntervalUpperLimit = 1000;
        int taskExecutionLowerLimit = 500;
        int taskExecutionUpperLimit = 2000;

        CPUController cpuController = new CPUController(
                numberOfTasks, numberOfCPUs, numberOfProcesses,
                generationIntervalLowerLimit, generationIntervalUpperLimit,
                taskExecutionLowerLimit,taskExecutionUpperLimit);
        cpuController.startProcessing();

    }
}

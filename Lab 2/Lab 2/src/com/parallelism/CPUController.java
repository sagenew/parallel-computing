package com.parallelism;

import java.util.concurrent.atomic.AtomicInteger;

public class CPUController {
    private final int numberOfTasks;
    private final int numberOfCPUs;
    private final int numberOfProcesses;
    private final int generationIntervalLowerLimit;
    private final int generationIntervalUpperLimit;
    private final int taskExecutionLowerLimit;
    private final int taskExecutionUpperLimit;
    public AtomicInteger tasksCreated = new AtomicInteger(0);

    public CPUController(int numberOfTasks, int numberOfCPUs, int numberOfProcesses,
                         int generationIntervalLowerLimit, int generationIntervalUpperLimit,
                         int taskExecutionLowerLimit, int taskExecutionUpperLimit) {
        this.numberOfTasks = numberOfTasks;
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfCPUs = numberOfCPUs;
        this.generationIntervalLowerLimit = generationIntervalLowerLimit;
        this.generationIntervalUpperLimit = generationIntervalUpperLimit;
        this.taskExecutionLowerLimit = taskExecutionLowerLimit;
        this.taskExecutionUpperLimit = taskExecutionUpperLimit;
    }

    public void startProcessing() {
        ThreadGroup CPUThreadGroup = new ThreadGroup("CPUs");
        Thread [] CPUs = new CPU[numberOfCPUs];
        ThreadGroup processThreadGroup = new ThreadGroup("Process Streams");
        Thread [] processes = new CPUProcess[numberOfProcesses];
        TaskHandler taskHandler = new TaskHandler((CPU[]) CPUs);

        for(int i = 0; i < numberOfProcesses; i++) {
            processes[i] = new CPUProcess(processThreadGroup, "Process Stream" + " # " + (i+1),
                    taskHandler, numberOfTasks,
                    generationIntervalLowerLimit, generationIntervalUpperLimit,
                    taskExecutionLowerLimit,
                    taskExecutionUpperLimit, tasksCreated);
        }

        for(int i = 0; i < numberOfCPUs; i++) {
            CPUs[i] = new CPU(CPUThreadGroup, "CPU" + " # " + (i+1), taskHandler);
        }

        for(Thread process : processes) {
            process.start();
        }

        for(Thread cpu : CPUs) {
            cpu.start();
        }

        while  (true) {
            if (tasksCreated.get() >= numberOfTasks && taskHandler.isEmpty()) {
                processThreadGroup.interrupt();
                CPUThreadGroup.interrupt();
                break;
            }
        }
        System.out.println("Tasks eliminated : " + taskHandler.getTasksEliminated()
                + " (" + ((float)taskHandler.getTasksEliminated()/(float)numberOfTasks) * 100 +"%)");


    }

}

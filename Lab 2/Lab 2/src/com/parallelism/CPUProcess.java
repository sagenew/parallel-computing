package com.parallelism;

import java.util.concurrent.atomic.AtomicInteger;

public class CPUProcess extends Thread {
    private final TaskHandler taskHandler;
    private final int numberOfTasks;
    private final int intervalLowerLimit;
    private final int intervalUpperLimit;
    private final int executionLowerLimit;
    private final int executionUpperLimit;
    private final AtomicInteger tasksCreated;

    public CPUProcess(ThreadGroup group, String name, TaskHandler taskHandler, int numberOfTasks, int intervalLowerLimit,
                      int intervalUpperLimit, int executionLowerLimit, int executionUpperLimit,
                      AtomicInteger tasksCreated) {
        super(group, name);
        this.taskHandler = taskHandler;
        this.numberOfTasks = numberOfTasks;
        this.intervalLowerLimit = intervalLowerLimit;
        this.intervalUpperLimit = intervalUpperLimit;
        this.executionLowerLimit = executionLowerLimit;
        this.executionUpperLimit = executionUpperLimit;
        this.tasksCreated = tasksCreated;
    }

    @Override
    public void run() { startTaskGeneration(); }

    public void startTaskGeneration() {
        while (tasksCreated.get() < numberOfTasks) {
            handleInterval();
            if(tasksCreated.get() >= numberOfTasks) {
                break;
            }
            taskHandler.push(createTask());
        }
    }

    public void handleInterval() {
        try {
            Thread.sleep(intervalLowerLimit + (int)(Math.random() * (intervalUpperLimit-intervalLowerLimit)));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Task createTask() {
        Task newTask = new Task(tasksCreated.incrementAndGet(),
                executionLowerLimit + (int)(Math.random() * (executionUpperLimit-executionLowerLimit)));
        System.out.println(this.getName() + " PUSH " + newTask);
        return newTask;
    }
}

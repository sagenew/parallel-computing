package com.parallelism;

public class Task {
    private final int taskId;
    private final int executionTimeMillis;

    public Task(int taskId, int executionTimeMillis) {
        this.taskId = taskId;
        this.executionTimeMillis = executionTimeMillis;
    }

    public void execute() {
        try {
            Thread.sleep(executionTimeMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return "Task # " + taskId + ", duration " + executionTimeMillis;
    }
}

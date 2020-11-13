package com.parallelism;

public class TaskHandler {

    private volatile Task currentTask;
    private int tasksEliminated = 0;
    private CPU [] cpus;

    TaskHandler(CPU[] cpus) {
        this.cpus = cpus;
    }

    public synchronized void push (Task task) {
            currentTask = task;
            notifyAll();
            for (CPU cpu : cpus) {
                if(!cpu.getStatus()) return;
            }
            tasksEliminated++;
            currentTask = null;
    }

    public synchronized Task pop () {
        Task popped = null;

        try {
            while (isEmpty()) wait();
            popped = currentTask;
            currentTask = null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return popped;
    }

    public int getTasksEliminated() {
        return tasksEliminated;
    }

    public boolean isEmpty() {
        return (currentTask == null);
    }



}

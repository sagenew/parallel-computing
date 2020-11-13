package com.parallelism;

public class CPU extends Thread {
    private final TaskHandler taskHandler;
    private int tasksProcessed;
    private boolean isProcessing;

    public CPU(ThreadGroup group, String name, TaskHandler taskHandler) {
        super(group, name);
        this.taskHandler = taskHandler;
        tasksProcessed = 0;
        isProcessing = false;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Task task = taskHandler.pop();
            if(task == null)
                continue;
            System.out.println(this.getName() + " GOT " + task);
            process(task);
        }
    }

    public void process(Task task) {
        isProcessing = true;
        task.execute();
        tasksProcessed++;
        isProcessing = false;
    }

    public boolean getStatus() {
        return isProcessing;
    }
}

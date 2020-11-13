package com.parallel.philosopher;

public abstract class Philosopher extends Thread {
    private int id;
    private int mealsEaten;
    public Philosopher(ThreadGroup threadGroup, String name, int id) {
        super(threadGroup,name);
        this.id = id;
    }

    public void think() {
        printThinkingMessage();
    }

    public void eat() {
        printEatingMessage();

        try {
            Thread.sleep(10);
            mealsEaten++;
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }

    protected abstract void printEatingMessage();

    protected void printThinkingMessage() {
        System.out.println(getName() + " is thinking. Hmm...");
    }

    public int getMealsEaten() {
        return mealsEaten;
    }

    public int getPhilosopherId() {
        return id;
    }
}

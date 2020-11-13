package com.parallel.philosopher.impl.synchroforks;

import com.parallel.philosopher.impl.synchroforks.fork.Fork;
import com.parallel.philosopher.Philosopher;

public class PhilosopherWithSynchroForks extends Philosopher {
    private Fork firstFork;
    private Fork secondFork;

    public PhilosopherWithSynchroForks(ThreadGroup threadGroup, String name, int id, Fork leftFork, Fork rightFork) {
        super(threadGroup,name, id);
        if(rightFork.getId() > leftFork.getId()) {
            this.firstFork = leftFork;
            this.secondFork = rightFork;
        } else {
            this.firstFork = rightFork;
            this.secondFork = leftFork;
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (firstFork) {
                synchronized (secondFork) {
                    eat();
                }
            }

            think();
        }
    }

    @Override
    protected void printEatingMessage() {
        System.out.println(getName() + " is eating with " + firstFork.toString() + " and " + secondFork.toString() + ".");
    }
}

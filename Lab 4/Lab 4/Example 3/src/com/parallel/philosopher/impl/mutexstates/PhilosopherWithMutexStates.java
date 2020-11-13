package com.parallel.philosopher.impl.mutexstates;

import com.parallel.philosopher.Philosopher;
import com.parallel.philosopher.impl.mutexstates.mutex.Mutex;
import java.util.List;

public class PhilosopherWithMutexStates extends Philosopher {
    enum State {HUNGRY, THINKING, EATING}

    private State state;
    private final List<Philosopher> philosophers;
    private PhilosopherWithMutexStates leftNeighbour;
    private PhilosopherWithMutexStates rightNeighbour;
    private final Object mutex;
    private boolean isReadyToEat;

    public PhilosopherWithMutexStates(ThreadGroup threadGroup, String name, int id, List<Philosopher> philosophers, Object mutex) {
        super(threadGroup, name, id);
        this.philosophers = philosophers;
        this.mutex = mutex;
        isReadyToEat = false;
    }

    @Override
    public void run() {
        assignNeighbours();
        while (!isInterrupted()) {
            try {
                think();
                takeForks();
                eat();
                putForks();
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    @Override
    public void think() {
        state = State.THINKING;
        printThinkingMessage();
    }

    private void takeForks() throws InterruptedException {
        synchronized (mutex) {
            state = State.HUNGRY;
            checkState();
        }
        setSelfDown();
    }

    private void putForks()  {
        synchronized (mutex) {
            state = State.THINKING;
            leftNeighbour.checkState();
            rightNeighbour.checkState();
        }
    }

    private void checkState() {
        if(state == State.HUNGRY
                && leftNeighbour.state != State.EATING
                && rightNeighbour.state != State.EATING) {
            state = State.EATING;
            setSelfUp();
        }
    }

    private synchronized void setSelfUp() {
            this.isReadyToEat = true;
            this.notifyAll();
    }

    private synchronized void setSelfDown() throws InterruptedException {
        if(isReadyToEat)
            this.isReadyToEat = false;
                else this.wait();
    }


    private void assignNeighbours() {
        int i, leftNeighbourIndex, rightNeighbourIndex;
        i = getPhilosopherId();
        leftNeighbourIndex = (i != 0) ? i - 1 : philosophers.size() - 1;
        rightNeighbourIndex = (i != philosophers.size() - 1) ? i + 1 : 0;
        this.leftNeighbour = (PhilosopherWithMutexStates) philosophers.get(leftNeighbourIndex);
        this.rightNeighbour = (PhilosopherWithMutexStates) philosophers.get(rightNeighbourIndex);

    }

    @Override
    protected void printEatingMessage() {
        System.out.println(getName() + " is eating. Delicious spaghetti..");
    }



}

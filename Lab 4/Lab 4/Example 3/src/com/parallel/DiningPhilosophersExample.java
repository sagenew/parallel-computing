package com.parallel;

import com.parallel.philosopher.impl.mutexstates.mutex.Mutex;
import com.parallel.philosopher.impl.synchroforks.fork.Fork;

import com.parallel.philosopher.Philosopher;
import com.parallel.philosopher.impl.mutexstates.PhilosopherWithMutexStates;
import com.parallel.philosopher.impl.synchroforks.PhilosopherWithSynchroForks;

import java.util.ArrayList;
import java.util.List;

public class DiningPhilosophersExample {
    //3. Обідаючі філософи.
    //Варіант 8 - 1) wait/notify, synchronized, object.
    private static final int PHILOSOPHERS_NUMBER = 7;

    public static void main(String[] args) throws InterruptedException {
        final ThreadGroup philosophersThreadGroup = new ThreadGroup("Philosophers");

//        List<Philosopher> philosophers = initPhilosophersDefault(philosophersThreadGroup);
//        List<Philosopher> philosophers = initPhilosophersWithSynchroForks(philosophersThreadGroup);
        List<Philosopher> philosophers = initPhilosophersWithMutexStates(philosophersThreadGroup);

        philosophers.forEach(Thread::start);

        Thread.sleep(1000);

        philosophersThreadGroup.interrupt();

        printMealsEaten(philosophers);

        System.out.println("3. Обідаючі філософи. 1 - wait/notify, synchronized, object(варіант 8).");
    }

    private static List<Fork> initForks() {
        List<Fork> forks = new ArrayList<>();
        for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
            forks.add(new Fork(i));
        }
        return forks;
    }

    private static List<Philosopher> initPhilosophersDefault(ThreadGroup philosophersThreadGroup) {
        List<Philosopher> philosophers = new ArrayList<>();
        List<Fork> forks = initForks();
        philosophers.add(new PhilosopherWithSynchroForks(philosophersThreadGroup, "Plato"       , 0, forks.get(0), forks.get(1)));
        philosophers.add(new PhilosopherWithSynchroForks(philosophersThreadGroup, "Aristotle"   , 1, forks.get(1), forks.get(2)));
        philosophers.add(new PhilosopherWithSynchroForks(philosophersThreadGroup, "Diogenes"    , 2, forks.get(2), forks.get(3)));
        philosophers.add(new PhilosopherWithSynchroForks(philosophersThreadGroup, "Archimedes"  , 3, forks.get(3), forks.get(4)));
        philosophers.add(new PhilosopherWithSynchroForks(philosophersThreadGroup, "Socrates"    , 4, forks.get(0), forks.get(4)));
        return philosophers;
    }

    private static List<Philosopher> initPhilosophersWithSynchroForks(ThreadGroup philosophersThreadGroup) {
        List<Philosopher> philosophers = new ArrayList<>();
        List<Fork> forks = initForks();
        int leftForkIndex, rightForkIndex;
        for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
            leftForkIndex = i;
            rightForkIndex = (i + 1) % PHILOSOPHERS_NUMBER;
            philosophers.add(new PhilosopherWithSynchroForks(philosophersThreadGroup, "Philosopher #" + (i + 1), i, forks.get(leftForkIndex), forks.get(rightForkIndex)));
        }
        return philosophers;
    }

    private static List<Philosopher> initPhilosophersWithMutexStates(ThreadGroup philosophersThreadGroup) {
        List<Philosopher> philosophers = new ArrayList<>();
        Object mutex = new Object();
        for (int i = 0; i < PHILOSOPHERS_NUMBER; i++) {
            philosophers.add(new PhilosopherWithMutexStates(philosophersThreadGroup, "Philosopher #" + (i + 1), i, philosophers, mutex));
        }
        return philosophers;
    }

    private static void printMealsEaten(List<Philosopher> philosophers) {
        System.out.println();
        for (Philosopher philosopher : philosophers) {
            System.out.println(philosopher.getName() + " has eaten " + philosopher.getMealsEaten() + " meals.");
        }
        System.out.println();
    }

}
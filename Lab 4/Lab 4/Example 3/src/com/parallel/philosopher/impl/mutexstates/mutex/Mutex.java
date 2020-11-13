package com.parallel.philosopher.impl.mutexstates.mutex;

public class Mutex {
    public synchronized void down() throws InterruptedException {
        wait();
    }

    public synchronized void up() {
        notifyAll();
    }
}

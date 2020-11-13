package com.parallel.philosopher.impl.synchroforks.fork;

public class Fork {
    private final int id;

    public Fork(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Fork #" + (id + 1);
    }
}

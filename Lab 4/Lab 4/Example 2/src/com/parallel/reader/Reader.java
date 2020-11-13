package com.parallel.reader;

import com.parallel.messagestorage.MessageStorage;

public class Reader extends Thread{
    private final MessageStorage storage;

    public Reader(ThreadGroup threadGroup, String name, MessageStorage storage) {
        super(threadGroup, name);
        this.storage = storage;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
             readMessage();
        }
    }

    private void readMessage() {
        String message;
        try {
            Thread.sleep(100);
            message = storage.read();
            if(message != null) printMessage(message);
        } catch (InterruptedException e) {
            interrupt();
            System.out.printf("%s INTERRUPTED. \n", getName());
        }
    }

    private void printMessage(String message) {
        System.out.printf("%s reads %s. \n", getName(), message);
    }
}

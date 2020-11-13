package com.parallel.writer;

import com.parallel.messagestorage.MessageStorage;

import java.util.concurrent.atomic.AtomicInteger;

public class Writer extends Thread {
    private final MessageStorage storage;
    private AtomicInteger messageCounter;

    public Writer(ThreadGroup threadGroup, String name, MessageStorage storage, AtomicInteger messageCounter) {
        super(threadGroup, name);
        this.storage = storage;
        this.messageCounter = messageCounter;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            writeMessage(generateMessage());
        }
    }

    private void writeMessage(String message) {
        try {
            Thread.sleep(100);
            printMessage(message);
            storage.write(message);
        } catch (InterruptedException e) {
            interrupt();
            System.out.printf("%s INTERRUPTED. \n", getName());
        }
    }

    private String generateMessage() {
        return String.format("Message #%d written by %s", messageCounter.incrementAndGet(), getName());
    }

    private void printMessage(String message) {
        System.out.printf("%s writes new message : \"%s\". \n", getName(), message);
    }

}

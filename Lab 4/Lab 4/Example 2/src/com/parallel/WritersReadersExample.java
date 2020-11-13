package com.parallel;

import com.parallel.messagestorage.MessageStorage;
import com.parallel.reader.Reader;
import com.parallel.writer.Writer;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class WritersReadersExample {
    //2. Письменники - читачі
    //Варіант 8 - Semaphore, з блокуванням нових читачів, коли письменник чекає на вхід.
    private final static int WRITERS_NUMBER = 2;
    private final static int READERS_NUMBER = 5;
    private final static int WRITERS_SEMAPHORE_PERMITS = 1;
    private final static int READERS_SEMAPHORE_PERMITS = 1;

    public static void main(String[] args) throws InterruptedException {

        Thread[] writers = new Thread[WRITERS_NUMBER];
        Thread[] readers = new Thread[READERS_NUMBER];
        Semaphore writeSemaphore = new Semaphore(WRITERS_SEMAPHORE_PERMITS);
        Semaphore readSemaphore = new Semaphore(READERS_SEMAPHORE_PERMITS);
        ThreadGroup writersGroup = new ThreadGroup("Writers");
        ThreadGroup readersGroup = new ThreadGroup("Readers");
        MessageStorage storage = new MessageStorage(writeSemaphore, readSemaphore);
        AtomicInteger messageCounter = new AtomicInteger(0);

        for (int i = 0; i < WRITERS_NUMBER; i++) {
            writers[i] = new Writer(writersGroup, "Writer #" + (i + 1), storage, messageCounter);
            writers[i].start();
        }

        for (int i = 0; i < READERS_NUMBER; i++) {
            readers[i] = new Reader(readersGroup, "Reader #" + (i + 1), storage);
            readers[i].start();
        }

        Thread.sleep(10000);

        writersGroup.interrupt();
        readersGroup.interrupt();

        System.out.println("//2. Письменники - читачі. Semaphore, з блокуванням нових читачів, коли письменник чекає на вхід. (варіант 8)");
    }
}

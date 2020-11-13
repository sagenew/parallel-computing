package com.parallel.messagestorage;


import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageStorage {
    private String message = null;
    private final Semaphore writeSemaphore;
    private final Semaphore readSemaphore;
    private final Semaphore incrementReadersSemaphore = new Semaphore(1);
    private final Semaphore incrementWritersSemaphore = new Semaphore(1);
    int writersCounter = 0;
    int readersCounter = 0;

    public MessageStorage(Semaphore writeSemaphore, Semaphore readSemaphore) {
        this.writeSemaphore = writeSemaphore;
        this.readSemaphore = readSemaphore;
    }

    public void write(final String message) throws InterruptedException {
        incrementWritersSemaphore.acquire();
        writersCounter++;
        if (writersCounter == 1) readSemaphore.acquire();
        incrementWritersSemaphore.release();

        writeSemaphore.acquire();
        this.message = message;
//        System.out.printf("The shared storage got new message : \"%s\", writers in queue : %d.\n", message, writersCounter);

        writeSemaphore.release();

        incrementWritersSemaphore.acquire();
        writersCounter--;
        if (writersCounter == 0) readSemaphore.release();
        incrementWritersSemaphore.release();
    }

    public String read() throws InterruptedException {
        String message;

        readSemaphore.acquire();
        incrementReadersSemaphore.acquire();
        readersCounter++;
        if (readersCounter == 1) writeSemaphore.acquire();
        incrementReadersSemaphore.release();
        readSemaphore.release();

//        System.out.printf("%s is being read, current readers : %d.\n", this.message, readersCounter);
        message = this.message;

        incrementReadersSemaphore.acquire();
        readersCounter--;
        if (readersCounter == 0) writeSemaphore.release();
        incrementReadersSemaphore.release();

        return message;
    }
}

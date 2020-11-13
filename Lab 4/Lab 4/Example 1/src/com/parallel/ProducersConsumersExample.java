package com.parallel;

import com.parallel.consumer.Consumer;
import com.parallel.producer.Producer;
import com.parallel.queue.ProductQueue;

import java.util.concurrent.atomic.AtomicInteger;

public class ProducersConsumersExample {
    //1. Виробники — споживачі.
    //Варіант 8 - ReentrantLock

    private final static int PRODUCERS_NUMBER = 3;
    private final static int CONSUMERS_NUMBER = 3;
    private final static int QUEUE_MAX_SIZE = 10;

    public static void main(String[] args) throws InterruptedException {
        ProductQueue queue = new ProductQueue(QUEUE_MAX_SIZE);
        AtomicInteger productCounter = new AtomicInteger();
        ThreadGroup producersGroup = new ThreadGroup("Producers");
        ThreadGroup consumersGroup = new ThreadGroup("Consumers");
        Thread[] producers = new Thread[PRODUCERS_NUMBER];
        Thread[] consumers = new Thread[CONSUMERS_NUMBER];

        for (int i = 0; i < PRODUCERS_NUMBER; i++) {
            producers[i] = new Producer(producersGroup, "Producer #" + (i + 1), queue, productCounter);
            producers[i].start();
        }

        for (int i = 0; i < CONSUMERS_NUMBER; i++) {
            consumers[i] = new Consumer(consumersGroup, "Consumer #" + (i + 1), queue);
            consumers[i].start();
        }

        Thread.sleep(10000);

        producersGroup.interrupt();
        do {
            Thread.sleep(1);
        } while (queue.size() != 0);

        consumersGroup.interrupt();

        System.out.println();
        for (Thread producer : producers) {
            System.out.println(producer.toString());
        }

        for (Thread consumer : consumers) {
            System.out.println(consumer.toString());
        }
        System.out.println();
        System.out.println("1. Виробники - споживачі, ReentrantLock (варіант 8).");
     }
}

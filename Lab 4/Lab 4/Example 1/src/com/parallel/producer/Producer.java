package com.parallel.producer;

import com.parallel.data.Product;
import com.parallel.queue.ProductQueue;

import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends Thread {
    ProductQueue queue;
    int productCreated;
    AtomicInteger productCounter;

    public Producer(ThreadGroup threadGroup, String name, ProductQueue queue, AtomicInteger productCounter) {
        super(threadGroup, name);
        this.queue = queue;
        productCreated = 0;
        this.productCounter = productCounter;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            queue.push(createProduct());
            handleInterval();
        }
    }

    private Product createProduct() {
            Product newProduct = new Product(productCounter.incrementAndGet(),
                    100 + (int)(Math.random() * 900) + 100);
            System.out.println(this.getName() + " PRODUCES " + newProduct);
            productCreated++;
            return newProduct;
    }

    private void handleInterval() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return this.getName() + " PRODUCED " + productCreated + " products.";
    }
}

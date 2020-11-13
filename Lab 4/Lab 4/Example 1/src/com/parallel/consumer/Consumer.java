package com.parallel.consumer;

import com.parallel.data.Product;
import com.parallel.queue.ProductQueue;

public class Consumer extends Thread {
    ProductQueue queue;
    int productConsumed;

    public Consumer(ThreadGroup threadGroup, String name, ProductQueue queue) {
        super(threadGroup, name);
        this.queue = queue;
        productConsumed = 0;
    }

    @Override
    public void run() {
        Product product;
        while (!isInterrupted()) {
            product = queue.pop();
            if (product == null) continue;
            consumeProduct(product);
        }
    }

    private void consumeProduct(Product product) {
        productConsumed++;
        System.out.println(this.getName() + " CONSUMES " + product.toString());
        product.consume();
    }

    @Override
    public String toString() {
        return this.getName() + " CONSUMED " + productConsumed + " products.";
    }
}

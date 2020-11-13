package com.parallel.data;

public class Product {
    private final int productId;
    private final int consumingTime;

    public Product(int taskId, int consumingTime) {
        this.productId = taskId;
        this.consumingTime = consumingTime;
    }

    public void consume() {
        try {
            Thread.sleep(consumingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public String toString() {
        return "Product # " + productId + ", time " +  consumingTime;
    }
}

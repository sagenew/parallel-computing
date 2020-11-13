package com.parallel.queue;

import com.parallel.data.Product;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProductQueue {
    int maxSize;
    Queue<Product> queue = new LinkedList<>();
    ReentrantLock lock = new ReentrantLock();
    Condition emptyCondition = lock.newCondition();
    Condition fullCondition = lock.newCondition();

    public ProductQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(Product product) {
        lock.lock();
        try {
            while (queue.size() == maxSize) {
                System.out.println("QUEUE IS FULL");
                fullCondition.await();
            }
            queue.add(product);
            emptyCondition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

    }

    public Product pop() {
        lock.lock();
        Product product = null;
        try {
            while (queue.size() == 0) {
                System.out.println("QUEUE IS EMPTY");
                emptyCondition.await();
            }
            product = queue.remove();
            fullCondition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
        return product;
    }

    public int size() {
        return queue.size();
    }
}

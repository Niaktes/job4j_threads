package ru.job4j.waiting;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int maxElements;

    public SimpleBlockingQueue(final int maxElements) {
        this.maxElements = maxElements;
    }

    public synchronized void offer(T value) {
        while (queue.size() >= maxElements) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(value);
        this.notify();
    }

    public synchronized T poll() {
        while(queue.isEmpty()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        T element = queue.poll();
        this.notify();
        return element;
    }

}
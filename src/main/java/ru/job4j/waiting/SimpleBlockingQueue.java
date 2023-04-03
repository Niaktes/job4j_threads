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

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= maxElements) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while(queue.isEmpty()) {
           this.wait();
        }
        T element = queue.poll();
        this.notifyAll();
        return element;
    }

}
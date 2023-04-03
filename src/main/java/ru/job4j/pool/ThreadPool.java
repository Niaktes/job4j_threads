package ru.job4j.pool;

import ru.job4j.waiting.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(16);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            },"thread " + i));
        }
        threads.forEach(Thread::start);
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        this.notifyAll();
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

}
package ru.job4j.pool;

import ru.job4j.waiting.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private static final int SIZE = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        for (int i = 0; i < SIZE; i++) {
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
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

}
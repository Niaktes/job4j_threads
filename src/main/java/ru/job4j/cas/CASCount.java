package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int update;
        int expected;
        do {
            expected = count.get();
            update = count.get() + 1;
        } while (!count.compareAndSet(expected, update));
    }

    public int get() {
        return count.get();
    }

}
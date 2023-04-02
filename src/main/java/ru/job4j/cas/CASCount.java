package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount(int startValue) {
        count.set(startValue);
    }

    public void increment() {
        Integer update;
        Integer expected;
        do {
            expected = count.get();
            update = count.get() + 1;
        } while (!count.compareAndSet(expected, update));
    }

    public int get() {
        return count.get();
    }

}
package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CASCountTest {

    @Test
    void whenOneThreadCountIncrement1000ThenGet1000() {
        CASCount count = new CASCount(0);
        for (int i = 0; i < 1000; i++) {
            count.increment();
        }
        assertEquals(1000, count.get());
    }

    @Test
    void when3ThreadsCountIncrement1000EachThenGet3000() throws InterruptedException {
        CASCount count = new CASCount(0);
        Runnable increment = () -> {
            for (int i = 0; i < 1000; i++) {
                count.increment();
            }
        };
        Thread first = new Thread (increment);
        Thread second = new Thread(increment);
        Thread third = new Thread(increment);
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertEquals(3000, count.get());
    }

}
package ru.job4j.waiting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    SimpleBlockingQueue<Integer> queue;
    int nextElement;
    int takenElement;

    @BeforeEach
    void init() {
        queue = new SimpleBlockingQueue<>(3);
        nextElement = 0;
    }

    @Test
    void whenAddAndPollThenGetValue() throws InterruptedException {
        Thread producer = new Thread (() -> {
            try {
                queue.offer(++nextElement);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread (() -> {
            try {
                takenElement = queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(1, takenElement);
    }

    @Test
    void whenAddManyTimesThenProducerThreadWaiting() throws InterruptedException {
        Thread producer = new Thread (() -> {
            try {
                for (int i = 0; i <= 3; i++) {
                    queue.offer(++nextElement);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread (() -> {
            try {
                takenElement = queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        producer.start();
        Thread.sleep(100);
        assertEquals(Thread.State.WAITING, producer.getState());
        Thread.sleep(100);
        consumer.start();
        Thread.sleep(100);
        producer.join();
        consumer.join();
        assertEquals(Thread.State.TERMINATED, producer.getState());
    }

    @Test
    void whenPollFromEmptyThenConsumerThreadWaiting() throws InterruptedException {
        Thread producer = new Thread (() -> {
            try {
                queue.offer(++nextElement);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread (() -> {
            try {
                takenElement = queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
        consumer.start();
        Thread.sleep(100);
        assertEquals(Thread.State.WAITING, consumer.getState());
        producer.start();
        Thread.sleep(100);
        assertEquals(1, takenElement);
        producer.join();
        consumer.join();
        assertEquals(Thread.State.TERMINATED, consumer.getState());
    }

}
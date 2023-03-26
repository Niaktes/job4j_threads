package ru.job4j.threads;

public class RaceConditionExample {

    public static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        RaceConditionExample rc = new RaceConditionExample();
        Thread t1 = new Thread(rc :: increment);
        t1.start();
        Thread t2 = new Thread(rc :: increment);
        t2.start();
        t1.join();
        t2.join();
    }

    public synchronized void increment() {
        for (int i = 0; i < 99999; i++) {
            int current = num;
            int next = ++num;
            if (current + 1 != next) {
                throw new IllegalStateException("Некорректное сравнение: " + current + " + 1 = " + next);
            }
        }
    }

}
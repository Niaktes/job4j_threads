package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread (
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread (
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        System.out.println(first.getState());
        second.start();
        System.out.println(second.getState());
        first.join();
        second.join();
        System.out.println(first.getState());
        System.out.println(second.getState());
        System.out.println("Работа обоих нитей завершена.");

    }

}
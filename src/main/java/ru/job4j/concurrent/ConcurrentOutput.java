package ru.job4j.concurrent;

public class ConcurrentOutput {

    public static void main(String[] args) {

        /*
        Создание Thread явно, без использования лямбды:
        Thread another = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName());
                    }
                }
        );
         */
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        second.start();
        /*
        Данный метод, в отличие от start() не заставляет выполнять операции в отдельной нити, а вызывает указанное в конструкторе напрямую.
        another.run();
         */
        System.out.println(Thread.currentThread().getName());
    }

}
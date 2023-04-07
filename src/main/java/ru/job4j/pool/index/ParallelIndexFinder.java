package ru.job4j.pool.index;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexFinder<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T desired;

    public ParallelIndexFinder(T[] array, int from, int to, T desired) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.desired = desired;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelIndexFinder<T> leftFinder = new ParallelIndexFinder<>(array, from, mid, desired);
        ParallelIndexFinder<T> rightFinder = new ParallelIndexFinder<>(array, mid + 1, to, desired);
        leftFinder.fork();
        rightFinder.fork();
        Integer index = leftFinder.join();
        return rightFinder.join() != null ? rightFinder.join() : index;
    }

    public static <T> Integer find(T desired, T[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexFinder<>(array, 0, array.length-1, desired));
    }

    private Integer linearSearch() {
        Integer index = null;
        for (int i = from; i <= to; i++) {
            if (desired.getClass() != array[i].getClass()) {
                throw new IllegalArgumentException("Different types of data");
            }
            if (desired.equals(array[i])) {
                index = i;
            }
        }
        return index;
    }

}
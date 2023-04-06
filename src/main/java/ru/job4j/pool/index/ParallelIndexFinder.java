package ru.job4j.pool.index;

import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexFinder extends RecursiveTask<Integer> {

    private final Object[] array;
    private final int from;
    private final int to;
    private final Object desired;

    ParallelIndexFinder(Object[] array, int from, int to, Object desired) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.desired = desired;
    }

    @Override
    protected Integer compute() {
        Integer index = null;
        if (to - from <= 10) {
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
        int mid = (from + to) / 2;
        ParallelIndexFinder leftFinder = new ParallelIndexFinder(array, from, mid, desired);
        ParallelIndexFinder rightFinder = new ParallelIndexFinder(array, mid + 1, to, desired);
        leftFinder.fork();
        rightFinder.fork();
        index = leftFinder.join();
        return rightFinder.join() != null ? rightFinder.join() : index;
    }

    public static Integer find(Object desired, Object[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer index = forkJoinPool.invoke(new ParallelIndexFinder(array, 0, array.length-1, desired));
        if (index == null) {
            throw new NoSuchElementException();
        }
        return index;
    }

}
package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamExample {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4 ,5);
        Stream<Integer> stream = list.parallelStream();
        System.out.println(stream.isParallel());
        Optional<Integer> multiplication = stream.reduce((a, b) -> a * b);
        System.out.println(multiplication.get());
        System.out.print(System.lineSeparator());

        IntStream parallel = IntStream.range(1, 100). parallel();
        System.out.println(parallel.isParallel());
        IntStream sequential = parallel.sequential();
        System.out.println(sequential.isParallel());
        System.out.print(System.lineSeparator());

        list.stream().peek(System.out::print).toList();
        System.out.print(System.lineSeparator() + "--" + System.lineSeparator());
        list.stream().parallel().peek(System.out::print).toList();
        System.out.println(System.lineSeparator());

        list.stream().forEach(System.out::print);
        System.out.print(System.lineSeparator() + "--" + System.lineSeparator());
        list.stream().parallel().forEach(System.out::print);
        System.out.print(System.lineSeparator() + "--" + System.lineSeparator());
        list.stream().parallel().forEachOrdered(System.out::print);
    }

}
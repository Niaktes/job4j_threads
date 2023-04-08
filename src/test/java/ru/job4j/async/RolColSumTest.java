package ru.job4j.async;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RolColSumTest {

    int[][] matrix;

    @BeforeEach
    void init() {
        matrix = new int[][] {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };
    }

    @Test
    void whenUseSumMethodThenGetRightSums() {
        Sums[] expected = new Sums[] {
                new Sums(12, 6),
                new Sums(15, 15),
                new Sums(18, 24)
        };
        assertArrayEquals(expected, RolColSum.sum(matrix));
    }

    @Test
    void whenUseAsyncSumMethodThenGetRightSums() {
        Sums[] expected = new Sums[] {
                new Sums(12, 6),
                new Sums(15, 15),
                new Sums(18, 24)
        };
        assertArrayEquals(expected, RolColSum.asyncSum(matrix));
    }

}

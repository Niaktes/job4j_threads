package ru.job4j.async;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.job4j.async.RolColSum.Sums;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Sums[] sums = RolColSum.sum(matrix);
        assertEquals(12, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(18, sums[2].getRowSum());
        assertEquals(6, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(24, sums[2].getColSum());
    }

    @Test
    void whenUseAsyncSumMethodThenGetRightSums() {
        Sums[] sums = RolColSum.asyncSum(matrix);
        assertEquals(12, sums[0].getRowSum());
        assertEquals(15, sums[1].getRowSum());
        assertEquals(18, sums[2].getRowSum());
        assertEquals(6, sums[0].getColSum());
        assertEquals(15, sums[1].getColSum());
        assertEquals(24, sums[2].getColSum());
    }

}

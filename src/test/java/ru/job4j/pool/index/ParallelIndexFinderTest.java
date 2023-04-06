package ru.job4j.pool.index;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ParallelIndexFinderTest {

    @Test
    void whenLittleArrayWithElementThenGetElementIndex() {
        Integer[] array = new Integer[] {5, 8, 11, 21, 1, 9, 3};
        Integer desired = 1;
        assertEquals(4, ParallelIndexFinder.find(desired, array));
    }

    @Test
    void whenBigArrayWithElementThenGetElementIndex() {
        Integer[] array = new Integer[] {5, 8, 11, 21, 4, 9, 3, 12, 81, 2, 44, 0, 99, 1, -15, 0, 57, 34, 8, 5, 4, 3, 2};
        Integer desired = 1;
        assertEquals(13, ParallelIndexFinder.find(desired, array));
    }

    @Test
    void whenArrayAndDesiredObjectDifferentTypesThenGetIAException() {
        String[] array = new String[] {"5", "8", "11", "21", "1", "9", "3"};
        Integer desired = 1;
        assertThrows(IllegalArgumentException.class, () -> ParallelIndexFinder.find(desired, array));
    }

    @Test
    void whenNoElementInArrayThenGetNSEException() {
        Integer[] array = new Integer[] {5, 8, 11, 21, 4, 9, 3, 12, 81, 2, 44, 0, 99, 2, -15, 0, 57, 34, 8, 5, 4, 3, 2};
        Integer desired = 1;
        assertThrows(NoSuchElementException.class, () -> ParallelIndexFinder.find(desired, array));
    }

}
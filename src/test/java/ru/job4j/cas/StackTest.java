package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.poll());
        assertEquals(2, stack.poll());
        assertEquals(1, stack.poll());
    }

    @Test
    void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertEquals(1, stack.poll());
    }

    @Test
    void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.poll());
        assertEquals(1, stack.poll());
    }

}
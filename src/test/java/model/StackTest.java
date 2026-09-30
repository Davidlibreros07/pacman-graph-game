package model;

import org.icesi.implementacionjuegopacman.structures.Stack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    @Test
    public void testPushPopOrder() {
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");

        assertEquals("C", stack.pop());
        assertEquals("B", stack.pop());
        assertEquals("A", stack.pop());
    }

    @Test
    public void testTop() {
        Stack<Integer> stack = new Stack<>();
        stack.push(100);
        stack.push(200);
        assertEquals(200, stack.top());
    }

    @Test
    public void testIsEmpty() {
        Stack<Double> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        stack.push(2.71);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testSize() {
        Stack<Character> stack = new Stack<>();
        assertEquals(0, stack.size());
        stack.push('A');
        stack.push('B');
        assertEquals(2, stack.size());
        stack.pop();
        assertEquals(1, stack.size());
    }
}

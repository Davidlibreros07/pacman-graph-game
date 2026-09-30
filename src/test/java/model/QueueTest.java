package model;

import org.icesi.implementacionjuegopacman.structures.Queue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

    @Test
    public void testEnqueueDequeueOrder() {
        Queue<String> queue = new Queue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals("C", queue.dequeue());
    }

    @Test
    public void testFront() {
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(10);
        queue.enqueue(20);
        assertEquals(10, queue.front());
    }

    @Test
    public void testIsEmpty() {
        Queue<Double> queue = new Queue<>();
        assertTrue(queue.isEmpty());
        queue.enqueue(3.14);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testSize() {
        Queue<Character> queue = new Queue<>();
        assertEquals(0, queue.size());
        queue.enqueue('X');
        queue.enqueue('Y');
        assertEquals(2, queue.size());
        queue.dequeue();
        assertEquals(1, queue.size());
    }

    @Test
    public void testDequeueEmpty() {
        Queue<String> queue = new Queue<>();
        assertNull(queue.dequeue());
    }
}

package model;

import org.icesi.implementacionjuegopacman.structures.PriorityQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PriorityQueueTest {

    @Test
    public void testInsertExtractMin() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        pq.insert("C", 3);
        pq.insert("A", 1);
        pq.insert("B", 2);

        assertEquals("A", pq.extractMin());
        assertEquals("B", pq.extractMin());
        assertEquals("C", pq.extractMin());
    }

    @Test
    public void testIsEmpty() {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        assertTrue(pq.isEmpty());
        pq.insert(42, 5);
        assertFalse(pq.isEmpty());
    }

    @Test
    public void testExtractMinEmpty() {
        PriorityQueue<String> pq = new PriorityQueue<>();
        assertNull(pq.extractMin());
    }
}

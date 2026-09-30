package model;

import org.icesi.implementacionjuegopacman.Algoritmos.BFS;
import org.icesi.implementacionjuegopacman.structures.MatrizAdyacencia;
import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BFSTest{

    private GrafoInterfaz<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new MatrizAdyacencia<>();
        graph.addArista("A", "B");
        graph.addArista("A", "C");
        graph.addArista("B", "D");
        graph.addArista("C", "E");
    }

    @Test
    public void testBFSDesdeA() {
        List<String> result = BFS.bfs(graph, "A");


        assertEquals(5, result.size());
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
        assertTrue(result.contains("D"));
        assertTrue(result.contains("E"));


        assertEquals("A", result.get(0));
    }

    @Test
    public void testBFSDesdeHoja() {
        List<String> result = BFS.bfs(graph, "D");

        assertTrue(result.contains("D"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("A"));
    }

    @Test
    public void testBFSNodoAislado() {
        graph.addVertice("Z");

        List<String> result = BFS.bfs(graph, "Z");

        assertEquals(1, result.size());
        assertTrue(result.contains("Z"));
    }
}


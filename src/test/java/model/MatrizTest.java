package model;


import org.icesi.implementacionjuegopacman.structures.MatrizAdyacencia;
import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    public class MatrizTest {

        private GrafoInterfaz<String> graph;

        @BeforeEach
        public void setUp() {
            graph = new MatrizAdyacencia<>();
        }

        @Test
        public void testAddVertice() {
            graph.addVertice("A");
            assertTrue(graph.containsVertice("A"));
        }

        @Test
        public void testAddArista() {
            graph.addArista("A", "B");
            assertTrue(graph.areConnected("A", "B"));
            assertEquals(1, graph.getPeso("A", "B"));
        }

        @Test
        public void testAddAristaConPeso() {
            graph.addArista("A", "B", 5);
            assertEquals(5, graph.getPeso("A", "B"));
        }

        @Test
        public void testGetVecinos() {
            graph.addArista("A", "B");
            graph.addArista("A", "C");
            List<String> neighbors = graph.getVecinos("A");

            assertEquals(2, neighbors.size());
            assertTrue(neighbors.contains("B"));
            assertTrue(neighbors.contains("C"));
        }

        @Test
        public void testRemoveArista() {
            graph.addArista("A", "B");
            graph.removeArista("A", "B");
            assertFalse(graph.areConnected("A", "B"));
        }
        
        @Test
        public void testGetAllVertices() {
            graph.addVertice("A");
            graph.addVertice("B");
            List<String> vertices = graph.getAllVertices();

            assertEquals(2, vertices.size());
            assertTrue(vertices.contains("A"));
            assertTrue(vertices.contains("B"));
        }
    }



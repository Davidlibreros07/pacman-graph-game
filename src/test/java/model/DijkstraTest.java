package model;

import org.icesi.implementacionjuegopacman.Algoritmos.Dijkstra;
import org.icesi.implementacionjuegopacman.structures.MatrizAdyacencia;
import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTest {

    private GrafoInterfaz<String> graph;

    @BeforeEach
    public void setUp() {
        graph = new MatrizAdyacencia<>();
        graph.addArista("A", "B", 4);
        graph.addArista("A", "C", 2);
        graph.addArista("B", "C", 5);
        graph.addArista("B", "D", 10);
        graph.addArista("C", "D", 3);
        graph.addArista("D", "E", 1);
    }

    @Test
    public void testCaminoMinimoEstandarDesdeA() {
        Map<String, Integer> distances = Dijkstra.dijkstra(graph, "A");

        assertEquals(0, distances.get("A"));
        assertEquals(4, distances.get("B"));
        assertEquals(2, distances.get("C"));
        assertEquals(5, distances.get("D"));
        assertEquals(6, distances.get("E"));
    }

    @Test
    public void testNodoDesconectadoDesdeA() {
        graph.addVertice("Z");

        Map<String, Integer> distances = Dijkstra.dijkstra(graph, "A");

        assertEquals(Integer.MAX_VALUE, distances.get("Z"));
    }

    @Test
    public void testCaminoMinimoDesdeNodoIntermedioC() {
        graph.addVertice("Z"); // Para probar también nodo no conectado

        Map<String, Integer> distances = Dijkstra.dijkstra(graph, "C");

        assertEquals(0, distances.get("C"));   // distancia a sí mismo
        assertEquals(3, distances.get("D"));   // C -> D
        assertEquals(4, distances.get("E"));   // C -> D -> E
        assertEquals(5, distances.get("B"));   // C -> B directamente
        assertEquals(2, distances.get("A"));   // C -> A
        assertEquals(Integer.MAX_VALUE, distances.get("Z")); // Z desconectado
    }
}
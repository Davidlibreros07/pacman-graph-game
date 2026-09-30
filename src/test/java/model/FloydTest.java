package model;

import org.icesi.implementacionjuegopacman.Algoritmos.FloyD_W;
import org.icesi.implementacionjuegopacman.structures.ListaAdyacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class FloydTest {

    ListaAdyacencia<String> grafo;

    @BeforeEach
    public void setUp() {
        grafo = new ListaAdyacencia<>();
        grafo.addArista("A", "B", 1);
        grafo.addArista("B", "C", 2);
        grafo.addArista("C", "D", 1);
        grafo.addArista("A", "D", 4);
    }

    @Test
    public void testDistanciasEstandar() {
        Map<String, Map<String, Integer>> dist = FloyD_W.floydWarshall(grafo);

        assertEquals(0, dist.get("A").get("A"));
        assertEquals(1, dist.get("A").get("B"));
        assertEquals(3, dist.get("A").get("C"));
        assertEquals(4, dist.get("A").get("D"));
    }

    @Test
    public void testCaminoMismoNodo() {
        Map<String, Map<String, String>> next = FloyD_W.floydWarshallPaths(grafo);
        List<String> camino = FloyD_W.getPath(next, "C", "C");

        assertTrue(camino.isEmpty() || (camino.size() == 1 && camino.get(0).equals("C")));
    }

    @Test
    public void testCaminoMinimoInteresante() {
        Map<String, Map<String, String>> next = FloyD_W.floydWarshallPaths(grafo);
        List<String> camino = FloyD_W.getPath(next, "A", "D");

        List<List<String>> posibles = Arrays.asList(
                Arrays.asList("A", "D"),
                Arrays.asList("A", "B", "C", "D")
        );

        assertTrue(posibles.contains(camino), "El camino debe ser una de las rutas mínimas válidas");
    }
}
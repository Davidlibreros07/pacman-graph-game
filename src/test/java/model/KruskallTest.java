package model;

import org.icesi.implementacionjuegopacman.Algoritmos.Kruskall;
import org.icesi.implementacionjuegopacman.structures.Arista;
import org.icesi.implementacionjuegopacman.structures.ListaAdyacencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class KruskallTest {

    ListaAdyacencia<String> grafo;
    ListaAdyacencia<String> g;

    @BeforeEach
    public void setUp() {
        grafo = new ListaAdyacencia<>();
        grafo.addArista("A", "B", 1);
        grafo.addArista("B", "C", 2);
        grafo.addArista("C", "D", 1);
        grafo.addArista("A", "D", 4);
    }

    @BeforeEach
    public void setUp2() {
        g = new ListaAdyacencia<>();
        g.addArista("A", "B", 1);
        g.addArista("B", "C", 1);
        g.addArista("C", "D", 1);
        g.addArista("A", "D", 1);
    }



    @Test
    public void testMSTEstandar() {
        Set<Arista<String>> mst = Kruskall.kruskal(grafo);
        int totalPeso = mst.stream().mapToInt(Arista::getPeso).sum();

        assertEquals(4, totalPeso);
        assertEquals(3, mst.size());
    }

    @Test
    public void testMSTGrafoVacio() {
        ListaAdyacencia<String> grafoVacio = new ListaAdyacencia<>();
        Set<Arista<String>> mst = Kruskall.kruskal(grafoVacio);

        assertTrue(mst.isEmpty());
    }

    @Test
    public void testMSTMismoPeso() {
        setUp2(); // peso igual a las demás

        Set<Arista<String>> mst = Kruskall.kruskal(g);
        int totalPeso = mst.stream().mapToInt(Arista::getPeso).sum();

        assertEquals(3, mst.size());
        assertEquals(3, totalPeso); // 3 aristas de peso 1
    }
}
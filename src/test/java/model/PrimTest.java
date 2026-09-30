package model;

import org.icesi.implementacionjuegopacman.Algoritmos.Prim;
import org.icesi.implementacionjuegopacman.structures.Arista;
import org.icesi.implementacionjuegopacman.structures.ListaAdyacencia;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PrimTest {

    ListaAdyacencia<String> grafo;
    ListaAdyacencia<String> grafoIgual;

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
        grafoIgual = new ListaAdyacencia<>();
        grafoIgual.addArista("A", "B", 1);
        grafoIgual.addArista("B", "C", 1);
        grafoIgual.addArista("C", "D", 1);
        grafoIgual.addArista("A", "D", 1);
    }

    @Test
    public void testMSTEstandar() {
        Set<Arista<String>> mst = Prim.prim(grafo, "A");
        int totalPeso = mst.stream().mapToInt(Arista::getPeso).sum();

        assertEquals(4, totalPeso); // A-B (1), B-C (2), C-D (1)
        assertEquals(3, mst.size());
    }

    @Test
    public void testGrafoConUnSoloNodo() {
        ListaAdyacencia<String> grafoMin = new ListaAdyacencia<>();
        grafoMin.addVertice("A");

        Set<Arista<String>> mst = Prim.prim(grafoMin, "A");

        assertTrue(mst.isEmpty());
    }

    @Test
    public void testMSTConPesosIguales() {
        setUp2();// Peso igual

        Set<Arista<String>> mst = Prim.prim(grafoIgual, "A");
        int totalPeso = mst.stream().mapToInt(Arista::getPeso).sum();

        assertEquals(3, mst.size());
        assertEquals(3, totalPeso); // 3 aristas de peso 1
    }
}
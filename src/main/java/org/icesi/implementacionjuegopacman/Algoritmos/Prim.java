package org.icesi.implementacionjuegopacman.Algoritmos;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.icesi.implementacionjuegopacman.structures.PriorityQueue;
import org.icesi.implementacionjuegopacman.structures.Arista;


import java.util.*;

public class Prim {

    public static <T> Set<Arista<T>> prim(GrafoInterfaz<T> grafo, T inicio) {
        Set<T> visitados = new HashSet<>();
        PriorityQueue<T> cola = new PriorityQueue<>();
        Map<T, T> anteriores = new HashMap<>();
        Map<T, Integer> pesos = new HashMap<>();
        Set<Arista<T>> arbol = new HashSet<>();

        for (T vertice : grafo.getAllVertices()) {
            pesos.put(vertice, Integer.MAX_VALUE);
        }

        pesos.put(inicio, 0);
        cola.insert(inicio, 0);

        while (!cola.isEmpty()) {
            T actual = cola.extractMin();
            visitados.add(actual);

            for (T vecino : grafo.getVecinos(actual)) {
                int peso = grafo.getPeso(actual, vecino);

                if (!visitados.contains(vecino) && peso < pesos.get(vecino)) {
                    pesos.put(vecino, peso);
                    anteriores.put(vecino, actual);
                    cola.insert(vecino, peso);
                }
            }
        }

        // Construcción del conjunto de aristas del árbol de expansión mínima
        for (T destino : anteriores.keySet()) {
            T origen = anteriores.get(destino);
            int peso = grafo.getPeso(origen, destino);
            arbol.add(new Arista<>(origen, destino, peso));
        }

        return arbol;
    }



    }



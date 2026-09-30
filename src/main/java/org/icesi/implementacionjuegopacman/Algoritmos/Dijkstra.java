package org.icesi.implementacionjuegopacman.Algoritmos;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.icesi.implementacionjuegopacman.structures.PriorityQueue;

import java.util.*;

public class Dijkstra {

    public static <T> Map<T, Integer> dijkstra(GrafoInterfaz<T> graph, T origin) {
        Map<T, Integer> distances = new HashMap<>();
        Set<T> visited = new HashSet<>();
        PriorityQueue<T> queue = new PriorityQueue<>();

        // Inicializamos distancias
        for (T vertice : graph.getAllVertices()) {
            distances.put(vertice, Integer.MAX_VALUE); //EL VALOR MAXIMO PERMITIDO EN JAVA
        }
        distances.put(origin, 0);

        queue.insert(origin, 0);

        while (!queue.isEmpty()) {
            T current = queue.extractMin();
            if (visited.contains(current)) continue;
            visited.add(current);

            for (T Vecino : graph.getVecinos(current)) {
                int peso = graph.getPeso(current, Vecino);
                int currentDistance = distances.get(current);
                int newDistance = currentDistance + peso;

                if (newDistance < distances.get(Vecino)) {
                    distances.put(Vecino, newDistance);
                    queue.insert(Vecino, newDistance);
                }
            }
        }

        return distances;
    }

    public static <T> List<T> getPath(GrafoInterfaz<T> graph, T start, T goal) {
        Map<T, Integer> distances = new HashMap<>();
        Map<T, T> previous = new HashMap<>();
        PriorityQueue<T> queue = new PriorityQueue<>();

        for (T vertex : graph.getAllVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        queue.insert(start, 0);

        while (!queue.isEmpty()) {
            T current = queue.extractMin();

            if (current.equals(goal)) break;

            for (T neighbor : graph.getVecinos(current)) {
                int alt = distances.get(current) + graph.getPeso(current, neighbor);
                if (alt < distances.get(neighbor)) {
                    distances.put(neighbor, alt);
                    previous.put(neighbor, current);
                    queue.insert(neighbor, alt);
                }
            }
        }

        List<T> path = new LinkedList<>();
        T current = goal;
        while (current != null && previous.containsKey(current)) {
            path.add(0, current);
            current = previous.get(current);
        }

        if (!path.isEmpty() && !path.get(0).equals(start)) {
            path.add(0, start);
        }

        return path;
    }



}

package org.icesi.implementacionjuegopacman.Algoritmos;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.icesi.implementacionjuegopacman.structures.Queue;

import java.util.*;


public class BFS {

    public static <T> List<T> bfs(GrafoInterfaz<T> graph, T origen) {
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new Queue<>();

        visited.add(origen);
        queue.enqueue(origen);

        while (!queue.isEmpty()) {
            T Vertice = queue.dequeue();
            result.add(Vertice);

            List<T> Vecinos = graph.getVecinos(Vertice);
            for (T Vecino : Vecinos) {
                if (!visited.contains(Vecino)) {
                    visited.add(Vecino);
                    queue.enqueue(Vecino);
                }
            }
        }

        return result;
    }
    public static <T> List<T> getPath(GrafoInterfaz<T> graph, T start, T goal) {
        Map<T, T> cameFrom = new HashMap<>();
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new Queue<>();

        queue.enqueue(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            T current = queue.dequeue();

            if (current.equals(goal)) {
                List<T> path = new LinkedList<>();
                while (current != null) {
                    path.add(0, current);
                    current = cameFrom.get(current);
                }
                return path;
            }

            for (T neighbor : graph.getVecinos(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                    queue.enqueue(neighbor);
                }
            }
        }

        return new ArrayList<>(); // No hay camino
    }

}

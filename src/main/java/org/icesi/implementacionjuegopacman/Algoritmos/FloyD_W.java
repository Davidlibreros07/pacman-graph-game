package org.icesi.implementacionjuegopacman.Algoritmos;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;

import java.util.*;

public class FloyD_W {

    public static <T> Map<T, Map<T, Integer>> floydWarshall(GrafoInterfaz<T> graph) {
        List<T> vertices = graph.getAllVertices();
        Map<T, Map<T, Integer>> dist = new HashMap<>();

        // Inicializar distancias
        for (T u : vertices) {
            dist.put(u, new HashMap<>());
            for (T v : vertices) {
                if (u.equals(v)) {
                    dist.get(u).put(v, 0);
                } else if (graph.getVecinos(u).contains(v)) {
                    dist.get(u).put(v, graph.getPeso(u, v));
                } else {
                    dist.get(u).put(v, Integer.MAX_VALUE);
                }
            }
        }

        // Algoritmo de Floyd-Warshall
        for (T k : vertices) {
            for (T i : vertices) {
                for (T j : vertices) {
                    int dik = dist.get(i).get(k);
                    int dkj = dist.get(k).get(j);
                    int dij = dist.get(i).get(j);

                    if (dik != Integer.MAX_VALUE && dkj != Integer.MAX_VALUE
                            && dik + dkj < dij) {
                        dist.get(i).put(j, dik + dkj);
                    }
                }
            }
        }

        return dist;
    }

    public static <T> Map<T, Map<T, T>> floydWarshallPaths(GrafoInterfaz<T> graph) {
        List<T> vertices = graph.getAllVertices();
        Map<T, Map<T, Integer>> dist = new HashMap<>();
        Map<T, Map<T, T>> next = new HashMap<>();

        for (T u : vertices) {
            dist.put(u, new HashMap<>());
            next.put(u, new HashMap<>());
            for (T v : vertices) {
                if (u.equals(v)) {
                    dist.get(u).put(v, 0);
                } else if (graph.getVecinos(u).contains(v)) {
                    dist.get(u).put(v, graph.getPeso(u, v));
                    next.get(u).put(v, v);
                } else {
                    dist.get(u).put(v, Integer.MAX_VALUE);
                }
            }
        }

        for (T k : vertices) {
            for (T i : vertices) {
                for (T j : vertices) {
                    int dik = dist.get(i).get(k);
                    int dkj = dist.get(k).get(j);
                    int dij = dist.get(i).get(j);

                    if (dik != Integer.MAX_VALUE && dkj != Integer.MAX_VALUE
                            && dik + dkj < dij) {
                        dist.get(i).put(j, dik + dkj);
                        next.get(i).put(j, next.get(i).get(k));
                    }
                }
            }
        }

        return next;
    }

    public static <T> List<T> getPath(Map<T, Map<T, T>> next, T u, T v) {
        if (!next.containsKey(u) || !next.get(u).containsKey(v)) {
            return new ArrayList<>(); // No hay camino
        }

        List<T> path = new ArrayList<>();
        T current = u;

        while (!current.equals(v)) {
            path.add(current);

            Map<T, T> nextMap = next.get(current);
            if (nextMap == null || !nextMap.containsKey(v)) {
                return new ArrayList<>(); // No hay camino desde aquí
            }

            current = nextMap.get(v);
            if (current == null) {
                return new ArrayList<>(); // Camino roto
            }
        }

        path.add(v);
        return path;
    }

}

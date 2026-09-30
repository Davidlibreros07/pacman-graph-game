package org.icesi.implementacionjuegopacman.Algoritmos;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.icesi.implementacionjuegopacman.structures.Arista;

import java.util.*;

public class Kruskall {

    private static class UnionFind<T> {
        private final Map<T, T> padre = new HashMap<>();

        public void makeSet(Collection<T> elementos) {
            for (T elem : elementos) {
                padre.put(elem, elem);
            }
        }

        public T find(T x) {
            if (!padre.get(x).equals(x)) {
                padre.put(x, find(padre.get(x)));
            }
            return padre.get(x);
        }

        public void union(T x, T y) {
            T raizX = find(x);
            T raizY = find(y);
            if (!raizX.equals(raizY)) {
                padre.put(raizX, raizY);
            }
        }
    }

    public static <T> Set<Arista<T>> kruskal(GrafoInterfaz<T> grafo) {
        Set<Arista<T>> arbol = new HashSet<>();
        UnionFind<T> unionFind = new UnionFind<>();
        unionFind.makeSet(grafo.getAllVertices());

        // Recolectamos todas las aristas del grafo
        List<Arista<T>> aristas = new ArrayList<>();
        Set<String> visitadas = new HashSet<>();

        for (T u : grafo.getAllVertices()) {
            for (T v : grafo.getVecinos(u)) {
                String clave1 = u.toString() + "-" + v.toString();
                String clave2 = v.toString() + "-" + u.toString();
                if (!visitadas.contains(clave1) && !visitadas.contains(clave2)) {
                    int peso = grafo.getPeso(u, v);
                    aristas.add(new Arista<>(u, v, peso));
                    visitadas.add(clave1);
                }
            }
        }

        // Ordenamos las aristas por peso
        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        // Aplicamos Kruskal
        for (Arista<T> arista : aristas) {
            T u = arista.getOrigen();
            T v = arista.getDestino();

            if (!unionFind.find(u).equals(unionFind.find(v))) {
                unionFind.union(u, v);
                arbol.add(arista);
            }
        }

        return arbol;
    }
}


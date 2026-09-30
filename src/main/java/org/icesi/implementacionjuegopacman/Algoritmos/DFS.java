package org.icesi.implementacionjuegopacman.Algoritmos;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;
import org.icesi.implementacionjuegopacman.structures.Stack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DFS {

    public static <T> List<T> dfs(GrafoInterfaz<T> graph, T origen) {
        List<T> result = new ArrayList<>();
        Set<T> visited = new HashSet<>();
        Stack<T> stack = new Stack<>();

        stack.push(origen);

        while (!stack.isEmpty()) {
            T vertice = stack.pop();

            if (!visited.contains(vertice)) {
                visited.add(vertice);
                result.add(vertice);

                // Agregamos los vecinos al stack
                List<T> Vecinos = graph.getVecinos(vertice);
                for (T Vecino : Vecinos) {
                    if (!visited.contains(Vecino)) {
                        stack.push(Vecino);
                    }
                }
            }
        }

        return result;
    }
}

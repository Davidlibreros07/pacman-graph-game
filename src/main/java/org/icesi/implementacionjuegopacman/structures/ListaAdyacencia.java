package org.icesi.implementacionjuegopacman.structures;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListaAdyacencia<T> implements GrafoInterfaz<T> {

    private Map<T, List<Arista<T>>> adjacencyList;

    public ListaAdyacencia() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertice(T vertice) {
        adjacencyList.putIfAbsent(vertice, new ArrayList<>());
    }

    @Override
    public void addArista(T origen, T destino) {
        addArista(origen, destino, 1); // peso por defecto
    }

    @Override
    public void addArista(T origen, T destino, int peso) {
        addVertice(origen);
        addVertice(destino);

        adjacencyList.get(origen).add(new Arista<>(origen, destino, peso));
        adjacencyList.get(destino).add(new Arista<>(destino, origen, peso)); // si es no dirigido
    }

    @Override
    public void removeVertice(T vertice) {
        adjacencyList.remove(vertice);
        for (List<Arista<T>> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.getDestino().equals(vertice));
        }
    }

    @Override
    public void removeArista(T origen, T destino) {
        List<Arista<T>> edgesFromSource = adjacencyList.get(origen);
        List<Arista<T>> edgesFromDest = adjacencyList.get(destino);

        if (edgesFromSource != null)
            edgesFromSource.removeIf(edge -> edge.getDestino().equals(destino));

        if (edgesFromDest != null)
            edgesFromDest.removeIf(edge -> edge.getDestino().equals(origen));
    }

    @Override
    public boolean containsVertice(T vertice) {
        return adjacencyList.containsKey(vertice);
    }

    @Override
    public boolean areConnected(T origen, T destino) {
        if (!containsVertice(origen)) return false;
        for (Arista<T> edge : adjacencyList.get(origen)) {
            if (edge.getDestino().equals(destino)) return true;
        }
        return false;
    }

    @Override
    public List<T> getVecinos(T vertice) {
        List<T> Vecinos = new ArrayList<>();
        if (!containsVertice(vertice)) return Vecinos;

        for (Arista<T> edge : adjacencyList.get(vertice)) {
            Vecinos.add(edge.getDestino());
        }

        return Vecinos;
    }

    @Override
    public int getPeso(T origen, T destino) {
        if (!containsVertice(origen)) return -1;
        for (Arista<T> edge : adjacencyList.get(origen)) {
            if (edge.getDestino().equals(destino)) {
                return edge.getPeso();
            }
        }
        return -1;
    }

    @Override
    public List<T> getAllVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }
}

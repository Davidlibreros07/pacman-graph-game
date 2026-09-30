package org.icesi.implementacionjuegopacman.structures;

import org.icesi.implementacionjuegopacman.Interfaces.GrafoInterfaz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MatrizAdyacencia<T> implements GrafoInterfaz<T> {

    private Map<T, Integer> vertexIndexMap; // Mapea vértices a índices de la matriz
    private List<T> vertices; // Lista de vértices para indexar
    private int[][] adjacencyMatrix; // Matriz de pesos
    private int capacity = 100; // Capacidad inicial (puedes ajustar)

    public MatrizAdyacencia() {
        vertexIndexMap = new HashMap<>();
        vertices = new ArrayList<>();
        adjacencyMatrix = new int[capacity][capacity];
    }

    @Override
    public void addVertice(T vertice) {
        if (!vertexIndexMap.containsKey(vertice)) {
            vertexIndexMap.put(vertice, vertices.size());
            vertices.add(vertice);
        }
    }

    @Override
    public void addArista(T origen, T destino) {
        addArista(origen, destino, 1); // Peso por defecto: 1 (no ponderado)
    }

    @Override
    public void addArista(T origen, T destino, int peso) {
        if (!vertexIndexMap.containsKey(origen)) addVertice(origen);
        if (!vertexIndexMap.containsKey(destino)) addVertice(destino);

        int i = vertexIndexMap.get(origen);
        int j = vertexIndexMap.get(destino);
        adjacencyMatrix[i][j] = peso;
        adjacencyMatrix[j][i] = peso; // Si el grafo es no dirigido
    }

    @Override
    public void removeVertice(T vertice) {
        // No implementado para mantenerlo simple por ahora
        // Podrías hacerlo marcando como eliminado o reconstruyendo la matriz
    }

    @Override
    public void removeArista(T origen, T destino) {
        if (containsVertice(origen) && containsVertice(destino)) {
            int i = vertexIndexMap.get(origen);
            int j = vertexIndexMap.get(destino);
            adjacencyMatrix[i][j] = 0;
            adjacencyMatrix[j][i] = 0; // Si es no dirigido
        }
    }

    @Override
    public boolean containsVertice(T vertice) {
        return vertexIndexMap.containsKey(vertice);
    }

    @Override
    public boolean areConnected(T origen, T destino) {
        if (!containsVertice(origen) || !containsVertice(destino)) return false;
        int i = vertexIndexMap.get(origen);
        int j = vertexIndexMap.get(destino);
        return adjacencyMatrix[i][j] != 0;
    }

    @Override
    public List<T> getVecinos(T vertice) {
        List<T> Vecinos = new ArrayList<>();
        if (!containsVertice(vertice)) return Vecinos;

        int i = vertexIndexMap.get(vertice);
        for (int j = 0; j < vertices.size(); j++) {
            if (adjacencyMatrix[i][j] != 0) {
                Vecinos.add(vertices.get(j));
            }
        }
        return Vecinos;
    }

    @Override
    public int getPeso(T origen, T destino) {
        if (!containsVertice(origen) || !containsVertice(destino)) return -1;
        int i = vertexIndexMap.get(origen);
        int j = vertexIndexMap.get(destino);
        return adjacencyMatrix[i][j];
    }

    @Override
    public List<T> getAllVertices() {
        return new ArrayList<>(vertices);
    }
}


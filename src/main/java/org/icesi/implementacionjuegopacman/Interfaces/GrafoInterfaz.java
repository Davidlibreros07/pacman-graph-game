package org.icesi.implementacionjuegopacman.Interfaces;

import java.util.List;

public interface GrafoInterfaz<T> {

    void addVertice(T vertice);

    // Agrega una arista no ponderada (peso por defecto: 1)
    void addArista(T origen, T destino);

    // Agrega una arista ponderada (recibe peso como parametro)
    void addArista(T origen, T destino, int peso);

    void removeVertice(T vertice);

    void removeArista(T origen, T destino);

    boolean containsVertice(T vertice);

    boolean areConnected(T origen, T destino);

    List<T> getVecinos(T vertice);

    int getPeso(T origen, T destino);

    List<T> getAllVertices();
}


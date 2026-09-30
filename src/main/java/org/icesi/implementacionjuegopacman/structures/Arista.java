package org.icesi.implementacionjuegopacman.structures;

public class Arista<T> {
    private T destino;
    private int peso;
    private T origen;

    public Arista(T origen,T destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;

    }

    public T getOrigen() {return origen;}

    public T getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}

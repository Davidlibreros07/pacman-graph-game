package org.icesi.implementacionjuegopacman.Interfaces;

public interface StackInterface<T> {
    void push(T element);
    T pop();
    T top();
    boolean isEmpty();
    int size();
}


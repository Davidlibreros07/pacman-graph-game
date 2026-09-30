package org.icesi.implementacionjuegopacman.Interfaces;

public interface QueueInterface<T> {
    void enqueue(T element);
    T dequeue();
    T front();
    boolean isEmpty();
    int size();
}

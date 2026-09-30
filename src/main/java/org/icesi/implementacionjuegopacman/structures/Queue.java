package org.icesi.implementacionjuegopacman.structures;

import org.icesi.implementacionjuegopacman.Interfaces.QueueInterface;

public class Queue<T> implements QueueInterface<T> {

    private static class QueueNode<T> {
        T data;
        QueueNode<T> next;

        QueueNode(T data) {
            this.data = data;
        }
    }

    private QueueNode<T> front;
    private QueueNode<T> rear;
    private int size;

    public Queue() {
        front = rear = null;
        size = 0;
    }

    @Override
    public void enqueue(T element) {
        QueueNode<T> node = new QueueNode<>(element);
        if (isEmpty()) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) return null;
        T data = front.data;
        front = front.next;
        size--;
        if (front == null) rear = null;
        return data;
    }

    @Override
    public T front() {
        if (isEmpty()) return null;
        return front.data;
    }

    @Override
    public boolean isEmpty() {
        return front == null;
    }

    @Override
    public int size() {
        return size;
    }
}

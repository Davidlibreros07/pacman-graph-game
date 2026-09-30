package org.icesi.implementacionjuegopacman.structures;


import org.icesi.implementacionjuegopacman.Interfaces.StackInterface;

public class Stack<T> implements StackInterface<T> {

    private static class StackNode<T> {
        T data;
        StackNode<T> next;
        StackNode(T data) { this.data = data; }
    }

    private StackNode<T> top;
    private int size;

    @Override
    public void push(T element) {
        StackNode<T> node = new StackNode<>(element);
        node.next = top;
        top = node;
        size++;
    }

    @Override
    public T pop() {
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    @Override
    public T top() {
        return top.data;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        return size;
    }
}


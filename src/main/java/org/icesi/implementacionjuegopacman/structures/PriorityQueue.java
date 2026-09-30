package org.icesi.implementacionjuegopacman.structures;

import java.util.ArrayList;

public class PriorityQueue<T> {

    private ArrayList<PriorityNode<T>> heap;

    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    public void insert(T element, int priority) {
        PriorityNode<T> node = new PriorityNode<>(element, priority);
        heap.add(node);
        heapifyUp(heap.size() - 1);
    }

    public T extractMin() {
        if (isEmpty()) return null;

        T min = heap.get(0).getElement();
        PriorityNode<T> last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(index).getPriority() < heap.get(parent).getPriority()) {
                swap(index, parent);
                index = parent;
            } else break;
        }
    }

    private void heapifyDown(int index) {
        int left, right, smallest;
        while (true) {
            left = 2 * index + 1;
            right = 2 * index + 2;
            smallest = index;

            if (left < heap.size() && heap.get(left).getPriority() < heap.get(smallest).getPriority()) {
                smallest = left;
            }

            if (right < heap.size() && heap.get(right).getPriority() < heap.get(smallest).getPriority()) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else break;
        }
    }

    private void swap(int i, int j) {
        PriorityNode<T> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

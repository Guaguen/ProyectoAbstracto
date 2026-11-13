package com.example.proyectobd2.structures;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> front;
    private Node<T> rear;
    private int size;

    public void enqueue(T data) {
        Node<T> nuevo = new Node<>(data);
        if (rear == null) {
            front = rear = nuevo;
        } else {
            rear.next = nuevo;
            rear = nuevo;
        }
        size++;
    }

    public T dequeue() {
        if (front == null) return null;
        T data = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return data;
    }

    public T peek() { return front == null ? null : front.data; }
    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }

    public List<T> toList() {
        List<T> lista = new ArrayList<>();
        Node<T> actual = front;
        while (actual != null) {
            lista.add(actual.data);
            actual = actual.next;
        }
        return lista;
    }
}
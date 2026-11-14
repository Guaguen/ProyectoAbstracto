package com.example.proyectobd2.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LinkedList<T> {

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    private Node<T> head;
    private int size;

    public void add(T data) {
        Node<T> nuevo = new Node<>(data);
        if (head == null) {
            head = nuevo;
        } else {
            Node<T> actual = head;
            while (actual.next != null) actual = actual.next;
            actual.next = nuevo;
        }
        size++;
    }

    public T find(Predicate<T> condicion) {
        Node<T> actual = head;
        while (actual != null) {
            if (condicion.test(actual.data)) return actual.data;
            actual = actual.next;
        }
        return null;
    }

    public boolean remove(Predicate<T> condicion) {
        if (head == null) return false;
        if (condicion.test(head.data)) {
            head = head.next;
            size--;
            return true;
        }
        Node<T> actual = head;
        while (actual.next != null) {
            if (condicion.test(actual.next.data)) {
                actual.next = actual.next.next;
                size--;
                return true;
            }
            actual = actual.next;
        }
        return false;
    }

    public int size() { return size; }

    public List<T> toList() {
        List<T> lista = new ArrayList<>();
        Node<T> actual = head;
        while (actual != null) {
            lista.add(actual.data);
            actual = actual.next;
        }
        return lista;
    }
}
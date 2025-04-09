package com.svedentsov.aqa.tasks.data_structures;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Решение задачи №35: Реализация базового Стека и Очереди.
 * Содержит классы MyStack и MyQueue.
 */
public class Task35_ImplementStackQueue {

    /**
     * Простая реализация стека (LIFO - Last In, First Out) на основе {@link LinkedList}.
     * Предоставляет основные операции стека: push, pop, peek, isEmpty, size.
     * Использует методы addFirst, removeFirst, getFirst из LinkedList.
     */
    static class MyStack {
        // LinkedList эффективен для операций с началом списка.
        private final LinkedList<Integer> list = new LinkedList<>();

        /**
         * Добавляет элемент на вершину стека (в начало списка).
         *
         * @param value Значение для добавления.
         */
        public void push(int value) {
            list.addFirst(value);
        }

        /**
         * Удаляет и возвращает элемент с вершины стека (из начала списка).
         *
         * @return Элемент с вершины стека.
         * @throws NoSuchElementException если стек пуст.
         */
        public int pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Cannot pop from an empty stack.");
            }
            return list.removeFirst();
        }

        /**
         * Возвращает элемент с вершины стека (из начала списка) без его удаления.
         *
         * @return Элемент с вершины стека.
         * @throws NoSuchElementException если стек пуст.
         */
        public int peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Cannot peek into an empty stack.");
            }
            return list.getFirst();
        }

        /**
         * Проверяет, пуст ли стек.
         *
         * @return {@code true}, если стек пуст, {@code false} иначе.
         */
        public boolean isEmpty() {
            return list.isEmpty();
        }

        /**
         * Возвращает количество элементов в стеке.
         *
         * @return Размер стека.
         */
        public int size() {
            return list.size();
        }
    }

    /**
     * Простая реализация очереди (FIFO - First In, First Out) на основе {@link LinkedList}.
     * Предоставляет основные операции очереди: enqueue (add), dequeue (remove), peek, isEmpty, size.
     * Использует методы addLast (для добавления в конец) и removeFirst (для удаления из начала).
     */
    static class MyQueue {
        private final LinkedList<Integer> list = new LinkedList<>();

        /**
         * Добавляет элемент в конец очереди.
         *
         * @param value Значение для добавления.
         */
        public void enqueue(int value) {
            list.addLast(value); // Добавляем в конец списка
        }

        /**
         * Удаляет и возвращает элемент из начала очереди.
         *
         * @return Элемент из начала очереди.
         * @throws NoSuchElementException если очередь пуста.
         */
        public int dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Cannot dequeue from an empty queue.");
            }
            return list.removeFirst(); // Удаляем из начала списка
        }

        /**
         * Возвращает элемент из начала очереди без его удаления.
         *
         * @return Элемент из начала очереди.
         * @throws NoSuchElementException если очередь пуста.
         */
        public int peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Cannot peek into an empty queue.");
            }
            return list.getFirst(); // Смотрим первый элемент
        }

        /**
         * Проверяет, пуста ли очередь.
         *
         * @return {@code true}, если очередь пуста, {@code false} иначе.
         */
        public boolean isEmpty() {
            return list.isEmpty();
        }

        /**
         * Возвращает количество элементов в очереди.
         *
         * @return Размер очереди.
         */
        public int size() {
            return list.size();
        }
    }

    /**
     * Точка входа для демонстрации работы классов MyStack и MyQueue.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("--- Stack Demonstration ---");
        MyStack stack = new MyStack();
        System.out.println("Is stack empty? " + stack.isEmpty()); // true
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("Stack size: " + stack.size());       // 3
        System.out.println("Stack content (internal list): " + stack.list); // [30, 20, 10]
        System.out.println("Peek: " + stack.peek());           // 30
        System.out.println("Pop: " + stack.pop());            // 30
        System.out.println("Stack content after pop: " + stack.list); // [20, 10]
        System.out.println("Peek after pop: " + stack.peek());   // 20
        System.out.println("Is stack empty? " + stack.isEmpty()); // false
        System.out.println("Pop: " + stack.pop());            // 20
        System.out.println("Pop: " + stack.pop());            // 10
        System.out.println("Is stack empty? " + stack.isEmpty()); // true
        try {
            stack.pop(); // Попытка извлечь из пустого стека
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception on empty pop: " + e.getMessage());
        }

        System.out.println("\n--- Queue Demonstration ---");
        MyQueue queue = new MyQueue();
        System.out.println("Is queue empty? " + queue.isEmpty()); // true
        queue.enqueue(100); // Добавляем в конец
        queue.enqueue(200); // Добавляем в конец
        queue.enqueue(300); // Добавляем в конец
        System.out.println("Queue size: " + queue.size());         // 3
        System.out.println("Queue content (internal list): " + queue.list); // [100, 200, 300]
        System.out.println("Peek: " + queue.peek());             // 100 (первый добавленный)
        System.out.println("Dequeue: " + queue.dequeue());       // 100 (удаляем первый добавленный)
        System.out.println("Queue content after dequeue: " + queue.list); // [200, 300]
        System.out.println("Peek after dequeue: " + queue.peek()); // 200
        System.out.println("Is queue empty? " + queue.isEmpty());   // false
        System.out.println("Dequeue: " + queue.dequeue());       // 200
        System.out.println("Dequeue: " + queue.dequeue());       // 300
        System.out.println("Is queue empty? " + queue.isEmpty());   // true
        try {
            queue.peek(); // Попытка посмотреть в пустую очередь
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception on empty peek: " + e.getMessage());
        }
    }
}

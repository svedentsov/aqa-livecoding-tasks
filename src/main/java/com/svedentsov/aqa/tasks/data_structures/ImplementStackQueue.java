package com.svedentsov.aqa.tasks.data_structures;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Решение задачи №35: Реализация базового Стека и Очереди.
 * Описание: Используя массив или `LinkedList`.
 * (Проверяет: основы структур данных, ООП)
 * Задание: Реализуйте класс `MyStack`, используя `java.util.LinkedList` или массив,
 * который будет иметь методы `push(int value)`, `pop()` (возвращает и удаляет
 * верхний элемент), `peek()` (возвращает верхний элемент без удаления) и `isEmpty()`.
 * Дополнительно реализован `MyQueue`.
 * Пример: `MyStack stack = new MyStack(); stack.push(1); stack.push(2); stack.pop()` -> `2`.
 * `stack.peek()` -> `1`. `stack.isEmpty()` -> `false`.
 */
public class ImplementStackQueue {

    /**
     * Простая реализация стека (LIFO - Last In, First Out) на основе {@link LinkedList}.
     * Предоставляет основные операции стека: push, pop, peek, isEmpty, size.
     * Использует методы addFirst, removeFirst, getFirst из LinkedList, которые
     * эффективны для операций с началом списка.
     * Не является потокобезопасным (non-thread-safe).
     */
    static class MyStack {
        // LinkedList подходит, так как push/pop/peek с начала списка выполняются за O(1).
        private final LinkedList<Integer> storage = new LinkedList<>();

        /**
         * Добавляет элемент на вершину стека.
         * Эквивалентно добавлению в начало списка.
         *
         * @param value Значение для добавления.
         */
        public void push(int value) {
            storage.addFirst(value);
        }

        /**
         * Удаляет и возвращает элемент с вершины стека.
         * Эквивалентно удалению из начала списка.
         *
         * @return Элемент с вершины стека.
         * @throws NoSuchElementException если стек пуст.
         */
        public int pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Cannot pop from an empty stack.");
            }
            return storage.removeFirst();
        }

        /**
         * Возвращает элемент с вершины стека без его удаления.
         * Эквивалентно получению элемента из начала списка.
         *
         * @return Элемент с вершины стека.
         * @throws NoSuchElementException если стек пуст.
         */
        public int peek() {
            if (isEmpty()) {
                throw new NoSuchElementException("Cannot peek into an empty stack.");
            }
            return storage.getFirst();
        }

        /**
         * Проверяет, пуст ли стек.
         *
         * @return {@code true}, если стек пуст, {@code false} иначе.
         */
        public boolean isEmpty() {
            return storage.isEmpty();
        }

        /**
         * Возвращает количество элементов в стеке.
         *
         * @return Размер стека.
         */
        public int size() {
            return storage.size();
        }

        /**
         * Возвращает строковое представление стека (для отладки).
         * Показывает элементы от вершины к основанию.
         */
        @Override
        public String toString() {
            return "MyStack (top -> bottom): " + storage.toString();
        }
    }

    /**
     * Простая реализация очереди (FIFO - First In, First Out) на основе {@link LinkedList}.
     * Предоставляет основные операции очереди: enqueue (add), dequeue (remove), peek, isEmpty, size.
     * Использует методы addLast (для добавления в конец) и removeFirst (для удаления из начала),
     * оба из которых эффективны для LinkedList (O(1)).
     * Не является потокобезопасным (non-thread-safe).
     */
    static class MyQueue {
        private final LinkedList<Integer> storage = new LinkedList<>();

        /**
         * Добавляет элемент в конец очереди.
         *
         * @param value Значение для добавления.
         */
        public void enqueue(int value) {
            storage.addLast(value); // Добавляем в конец
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
            return storage.removeFirst(); // Удаляем из начала
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
            return storage.getFirst(); // Смотрим первый элемент
        }

        /**
         * Проверяет, пуста ли очередь.
         *
         * @return {@code true}, если очередь пуста, {@code false} иначе.
         */
        public boolean isEmpty() {
            return storage.isEmpty();
        }

        /**
         * Возвращает количество элементов в очереди.
         *
         * @return Размер очереди.
         */
        public int size() {
            return storage.size();
        }

        /**
         * Возвращает строковое представление очереди (для отладки).
         * Показывает элементы от начала к концу.
         */
        @Override
        public String toString() {
            return "MyQueue (front -> rear): " + storage.toString();
        }
    }

    /**
     * Точка входа для демонстрации работы классов MyStack и MyQueue.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {

        // --- Демонстрация Стека ---
        System.out.println("--- Stack Demonstration ---");
        MyStack stack = new MyStack();
        System.out.println("Initial: " + stack + ", isEmpty: " + stack.isEmpty() + ", Size: " + stack.size());

        System.out.println("Push 10");
        stack.push(10);
        System.out.println("Push 20");
        stack.push(20);
        System.out.println("Push 30");
        stack.push(30);
        System.out.println("Current: " + stack + ", isEmpty: " + stack.isEmpty() + ", Size: " + stack.size());

        System.out.println("Peek: " + stack.peek()); // 30
        System.out.println("Pop: " + stack.pop()); // 30
        System.out.println("Current: " + stack + ", Size: " + stack.size());

        System.out.println("Peek: " + stack.peek()); // 20
        System.out.println("Pop: " + stack.pop()); // 20
        System.out.println("Current: " + stack + ", Size: " + stack.size());

        System.out.println("Pop: " + stack.pop()); // 10
        System.out.println("Current: " + stack + ", isEmpty: " + stack.isEmpty() + ", Size: " + stack.size());

        // Попытка операций на пустом стеке
        try {
            System.out.print("Attempting pop() on empty stack: ");
            stack.pop();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception - " + e.getMessage());
        }
        try {
            System.out.print("Attempting peek() on empty stack: ");
            stack.peek();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception - " + e.getMessage());
        }

        // --- Демонстрация Очереди ---
        System.out.println("\n--- Queue Demonstration ---");
        MyQueue queue = new MyQueue();
        System.out.println("Initial: " + queue + ", isEmpty: " + queue.isEmpty() + ", Size: " + queue.size());

        System.out.println("Enqueue 100");
        queue.enqueue(100);
        System.out.println("Enqueue 200");
        queue.enqueue(200);
        System.out.println("Enqueue 300");
        queue.enqueue(300);
        System.out.println("Current: " + queue + ", isEmpty: " + queue.isEmpty() + ", Size: " + queue.size());

        System.out.println("Peek: " + queue.peek()); // 100
        System.out.println("Dequeue: " + queue.dequeue()); // 100
        System.out.println("Current: " + queue + ", Size: " + queue.size());

        System.out.println("Peek: " + queue.peek()); // 200
        System.out.println("Enqueue 400");
        queue.enqueue(400);
        System.out.println("Current: " + queue + ", Size: " + queue.size());

        System.out.println("Dequeue: " + queue.dequeue()); // 200
        System.out.println("Dequeue: " + queue.dequeue()); // 300
        System.out.println("Dequeue: " + queue.dequeue()); // 400
        System.out.println("Current: " + queue + ", isEmpty: " + queue.isEmpty() + ", Size: " + queue.size());

        // Попытка операций на пустой очереди
        try {
            System.out.print("Attempting dequeue() on empty queue: ");
            queue.dequeue();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception - " + e.getMessage());
        }
        try {
            System.out.print("Attempting peek() on empty queue: ");
            queue.peek();
        } catch (NoSuchElementException e) {
            System.out.println("Caught expected exception - " + e.getMessage());
        }
    }
}

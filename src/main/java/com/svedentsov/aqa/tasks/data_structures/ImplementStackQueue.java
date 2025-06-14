package com.svedentsov.aqa.tasks.data_structures;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Решение задачи №35: Реализация базового Стека и Очереди.
 * Описание: Используя массив или LinkedList.
 * (Проверяет: основы структур данных, ООП)
 * Задание: Реализуйте класс MyStack, используя java.util.LinkedList или массив,
 * который будет иметь методы push(int value), pop() (возвращает и удаляет
 * верхний элемент), peek() (возвращает верхний элемент без удаления) и isEmpty().
 * Дополнительно реализован MyQueue.
 */
public class ImplementStackQueue {

    /**
     * Простая реализация стека (LIFO - Last In, First Out) на основе {@link LinkedList}.
     * Класс сделан public для доступа из тестов.
     */
    public static class MyStack {
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
    public static class MyQueue {
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
    }
}

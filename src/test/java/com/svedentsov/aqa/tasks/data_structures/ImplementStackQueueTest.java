package com.svedentsov.aqa.tasks.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ImplementStackQueueTest {

    @Nested
    @DisplayName("Тесты для MyStack")
    class MyStackTests {

        private ImplementStackQueue.MyStack stack;

        @BeforeEach
        void setUp() {
            stack = new ImplementStackQueue.MyStack();
        }

        @Test
        @DisplayName("Новый стек должен быть пустым")
        void newStackShouldBeEmpty() {
            assertTrue(stack.isEmpty());
            assertEquals(0, stack.size());
        }

        @Test
        @DisplayName("push добавляет элемент на вершину")
        void pushAddsElementToTop() {
            stack.push(1);
            assertFalse(stack.isEmpty());
            assertEquals(1, stack.size());
            assertEquals(1, stack.peek());

            stack.push(2);
            assertEquals(2, stack.size());
            assertEquals(2, stack.peek());
        }

        @Test
        @DisplayName("pop удаляет и возвращает верхний элемент (LIFO)")
        void popRemovesAndReturnsTopElement() {
            stack.push(1);
            stack.push(2);
            stack.push(3);

            assertEquals(3, stack.pop());
            assertEquals(2, stack.size());
            assertEquals(2, stack.pop());
            assertEquals(1, stack.size());
            assertEquals(1, stack.pop());
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("peek возвращает верхний элемент, не удаляя его")
        void peekReturnsTopElementWithoutRemoving() {
            stack.push(10);
            stack.push(20);

            assertEquals(20, stack.peek());
            assertEquals(2, stack.size()); // Размер не должен измениться
        }

        @Test
        @DisplayName("pop на пустом стеке должен выбрасывать исключение")
        void popOnEmptyStackThrowsException() {
            assertThrows(NoSuchElementException.class, () -> stack.pop());
        }

        @Test
        @DisplayName("peek на пустом стеке должен выбрасывать исключение")
        void peekOnEmptyStackThrowsException() {
            assertThrows(NoSuchElementException.class, () -> stack.peek());
        }
    }

    @Nested
    @DisplayName("Тесты для MyQueue")
    class MyQueueTests {

        private ImplementStackQueue.MyQueue queue;

        @BeforeEach
        void setUp() {
            queue = new ImplementStackQueue.MyQueue();
        }

        @Test
        @DisplayName("Новая очередь должна быть пустой")
        void newQueueShouldBeEmpty() {
            assertTrue(queue.isEmpty());
            assertEquals(0, queue.size());
        }

        @Test
        @DisplayName("enqueue добавляет элемент в конец очереди")
        void enqueueAddsElementToRear() {
            queue.enqueue(1);
            assertFalse(queue.isEmpty());
            assertEquals(1, queue.size());
            assertEquals(1, queue.peek());

            queue.enqueue(2);
            assertEquals(2, queue.size());
            assertEquals(1, queue.peek()); // peek все еще должен возвращать первый элемент
        }

        @Test
        @DisplayName("dequeue удаляет и возвращает первый элемент (FIFO)")
        void dequeueRemovesAndReturnsFrontElement() {
            queue.enqueue(10);
            queue.enqueue(20);
            queue.enqueue(30);

            assertEquals(10, queue.dequeue());
            assertEquals(2, queue.size());
            assertEquals(20, queue.dequeue());
            assertEquals(1, queue.size());
            assertEquals(30, queue.dequeue());
            assertTrue(queue.isEmpty());
        }

        @Test
        @DisplayName("peek возвращает первый элемент, не удаляя его")
        void peekReturnsFrontElementWithoutRemoving() {
            queue.enqueue(100);
            queue.enqueue(200);

            assertEquals(100, queue.peek());
            assertEquals(2, queue.size()); // Размер не должен измениться
        }

        @Test
        @DisplayName("dequeue на пустой очереди должен выбрасывать исключение")
        void dequeueOnEmptyQueueThrowsException() {
            assertThrows(NoSuchElementException.class, () -> queue.dequeue());
        }

        @Test
        @DisplayName("peek на пустой очереди должен выбрасывать исключение")
        void peekOnEmptyQueueThrowsException() {
            assertThrows(NoSuchElementException.class, () -> queue.peek());
        }
    }
}

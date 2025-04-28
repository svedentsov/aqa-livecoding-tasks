package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для последовательности Фибоначчи")
class FibonacciSequenceTest {

    private FibonacciSequence fibonacciSequence;

    @BeforeEach
    void setUp() {
        fibonacciSequence = new FibonacciSequence();
    }

    @Test
    @DisplayName("Проверка базового случая F(0)")
    void testFibonacciZero() {
        assertEquals(0L, fibonacciSequence.fibonacci(0), "Fibonacci(0) должен быть 0");
    }

    @Test
    @DisplayName("Проверка базового случая F(1)")
    void testFibonacciOne() {
        assertEquals(1L, fibonacciSequence.fibonacci(1), "Fibonacci(1) должен быть 1");
    }

    @ParameterizedTest(name = "Проверка F({0}) = {1}")
    @DisplayName("Проверка общих чисел Фибоначчи")
    @CsvSource({
            "2, 1",
            "3, 2",
            "4, 3",
            "5, 5",
            "6, 8",
            "10, 55",
            "20, 6765",
            "30, 832040"
    })
    void testFibonacciCommonValues(int n, long expected) {
        assertEquals(expected, fibonacciSequence.fibonacci(n), "Fibonacci(" + n + ") должен быть " + expected);
    }

    @Test
    @DisplayName("Проверка наибольшего числа Фибоначчи в пределах long (F(92))")
    void testFibonacciMaxLong() {
        // F(92) - это максимальное число Фибоначчи, которое помещается в long
        assertEquals(7540113804746346429L, fibonacciSequence.fibonacci(92), "Fibonacci(92) должен быть максимальным значением для long");
    }

    @ParameterizedTest(name = "Проверка отрицательного ввода: {0}")
    @DisplayName("Проверка отрицательного ввода вызывает IllegalArgumentException")
    @ValueSource(ints = {-1, -5, -100})
    void testFibonacciNegativeInput(int n) {
        // Проверяем, что вызов метода с отрицательным n выбрасывает IllegalArgumentException
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            fibonacciSequence.fibonacci(n);
        }, "Отрицательный ввод должен вызывать IllegalArgumentException");

        // Проверяем, что сообщение об ошибке содержит ожидаемый текст
        assertTrue(thrown.getMessage().contains("Индекс Фибоначчи не может быть отрицательным"), "Сообщение исключения должно указывать на отрицательный индекс");
        assertTrue(thrown.getMessage().contains(String.valueOf(n)), "Сообщение исключения должно включать проблемный индекс");
    }

    @Test
    @DisplayName("Проверка переполнения для индекса 93 вызывает ArithmeticException")
    void testFibonacciOverflow() {
        // F(93) переполняет тип long, ожидаем ArithmeticException
        ArithmeticException thrown = assertThrows(ArithmeticException.class, () -> {
            fibonacciSequence.fibonacci(93);
        }, "Fibonacci(93) должен переполняться и вызывать ArithmeticException");

        // Проверяем, что сообщение об ошибке содержит ожидаемый текст о переполнении
        assertTrue(thrown.getMessage().contains("переполняет тип long"), "Сообщение исключения должно указывать на переполнение");
        assertTrue(thrown.getMessage().contains("для индекса 93"), "Сообщение исключения должно упоминать индекс 93");
    }
}

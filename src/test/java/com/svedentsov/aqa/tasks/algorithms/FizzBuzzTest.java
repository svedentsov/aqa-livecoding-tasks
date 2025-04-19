package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzTest {

    private FizzBuzz fizzBuzz;

    @BeforeEach
    void setUp() {
        fizzBuzz = new FizzBuzz();
    }

    @Test
    @DisplayName("Тест стандартного случая FizzBuzz до 15")
    void testFizzBuzzUpTo15() {
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz", "13", "14", "FizzBuzz");
        List<String> actual = fizzBuzz.generateFizzBuzz(15);
        assertEquals(expected, actual, "Последовательность для n=15 должна совпадать с ожидаемой");
    }

    @Test
    @DisplayName("Тест для n=1")
    void testFizzBuzzUpTo1() {
        List<String> expected = List.of("1");
        List<String> actual = fizzBuzz.generateFizzBuzz(1);
        assertEquals(expected, actual, "Последовательность для n=1 должна содержать только '1'");
    }

    @Test
    @DisplayName("Тест для n=3")
    void testFizzBuzzUpTo3() {
        List<String> expected = List.of("1", "2", "Fizz");
        List<String> actual = fizzBuzz.generateFizzBuzz(3);
        assertEquals(expected, actual, "Последовательность для n=3 должна быть '1', '2', 'Fizz'");
    }

    @Test
    @DisplayName("Тест для n=5")
    void testFizzBuzzUpTo5() {
        List<String> expected = List.of("1", "2", "Fizz", "4", "Buzz");
        List<String> actual = fizzBuzz.generateFizzBuzz(5);
        assertEquals(expected, actual, "Последовательность для n=5 должна включать 'Buzz'");
    }

    @Test
    @DisplayName("Тест для n=0 (некорректный ввод)")
    void testFizzBuzzForZero() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fizzBuzz.generateFizzBuzz(0);
        assertEquals(expected, actual, "Для n=0 должен возвращаться пустой список");
        assertTrue(actual.isEmpty(), "Для n=0 должен возвращаться пустой список (проверка isEmpty)");
    }

    @Test
    @DisplayName("Тест для отрицательного n (некорректный ввод)")
    void testFizzBuzzForNegativeN() {
        List<String> expected = Collections.emptyList();
        List<String> actual = fizzBuzz.generateFizzBuzz(-10);
        assertEquals(expected, actual, "Для отрицательного n должен возвращаться пустой список");
    }

    // Дополнительные тесты с использованием параметризации для проверки отдельных значений
    @ParameterizedTest(name = "Для числа {0} ожидается результат \"{1}\"")
    @CsvSource({
            "1, 1",
            "2, 2",
            "3, Fizz",
            "4, 4",
            "5, Buzz",
            "6, Fizz",
            "9, Fizz",
            "10, Buzz",
            "15, FizzBuzz",
            "30, FizzBuzz",
            "31, 31"})
    @DisplayName("Параметризованный тест отдельных значений")
    void testSingleValue(int number, String expectedValue) {
        // Примечание: Этот тест неявно вызывает приватный метод getFizzBuzzValue
        // через публичный generateFizzBuzz(number). Получаем список и берем последний элемент.
        List<String> resultList = fizzBuzz.generateFizzBuzz(number);
        // Убеждаемся, что список не пуст (для number >= 1)
        assertFalse(resultList.isEmpty(), "Список не должен быть пустым для number >= 1");
        // Берем последний элемент, который соответствует значению для 'number'
        String actualValue = resultList.get(resultList.size() - 1);
        assertEquals(expectedValue, actualValue);
    }
}

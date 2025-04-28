package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для проверки на простое число")
class PrimeNumberCheckTest {

    private PrimeNumberCheck primeNumberCheck;

    @BeforeEach
    void setUp() {
        // Инициализация объекта перед каждым тестом
        primeNumberCheck = new PrimeNumberCheck();
    }

    @ParameterizedTest(name = "Число {0} НЕ простое")
    @DisplayName("Проверка чисел, которые не являются простыми по определению (<= 1 или отрицательные)")
    @ValueSource(ints = {-10, -1, 0, 1})
    void testNotPrimeByDefinition(int number) {
        assertFalse(primeNumberCheck.isPrime(number), "Числа <= 1 и отрицательные не являются простыми: " + number);
    }

    @Test
    @DisplayName("Проверка базового простого числа: 2")
    void testIsPrimeTwo() {
        assertTrue(primeNumberCheck.isPrime(2), "Число 2 должно быть простым");
    }

    @Test
    @DisplayName("Проверка базового простого числа: 3")
    void testIsPrimeThree() {
        assertTrue(primeNumberCheck.isPrime(3), "Число 3 должно быть простым");
    }

    @ParameterizedTest(name = "Число {0} НЕ простое (четное > 2 или кратное 3)")
    @DisplayName("Проверка небольших составных чисел (четные > 2, кратные 3)")
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 15})
    void testSmallCompositeNumbers(int number) {
        assertFalse(primeNumberCheck.isPrime(number), "Небольшие составные числа должны быть НЕ простыми: " + number);
    }

    @ParameterizedTest(name = "Число {0} простое")
    @DisplayName("Проверка простых чисел (используется оптимизация 6k ± 1)")
    @ValueSource(ints = {5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113})
    void testIsPrimeNumbers(int number) {
        assertTrue(primeNumberCheck.isPrime(number), "Число " + number + " должно быть простым");
    }

    @ParameterizedTest(name = "Число {0} НЕ простое (составное)")
    @DisplayName("Проверка составных чисел (не покрываются первыми оптимизациями)")
    @ValueSource(ints = {25, 33, 35, 49, 55, 65, 77, 85, 91, 95, 111, 117, 119, 121})
    void testIsCompositeNumbers(int number) {
        assertFalse(primeNumberCheck.isPrime(number), "Число " + number + " должно быть составным");
    }

    @Test
    @DisplayName("Проверка очень большого простого числа (Integer.MAX_VALUE)")
    void testIsPrimeLargeNumber() {
        // Integer.MAX_VALUE (2147483647) является простым числом (простое Мерсенна M_31)
        assertTrue(primeNumberCheck.isPrime(Integer.MAX_VALUE), "Integer.MAX_VALUE должно быть простым числом");
    }

    // Можно добавить тесты для других больших составных чисел, если необходимо
    @Test
    @DisplayName("Проверка очень большого составного числа")
    void testIsCompositeLargeNumber() {
        // Пример большого составного числа (например, MAX_VALUE - 1)
        assertFalse(primeNumberCheck.isPrime(Integer.MAX_VALUE - 1), "Очень большое четное число должно быть составным");
        // Пример другого большого составного числа (MAX_VALUE - 2, делится на 3)
        assertFalse(primeNumberCheck.isPrime(Integer.MAX_VALUE - 2), "Очень большое число, кратное 3, должно быть составным");
    }
}

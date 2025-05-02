package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для GeneratePrimesSieve")
class GeneratePrimesSieveTest {

    private GeneratePrimesSieve generator;

    @BeforeEach
    void setUp() {
        generator = new GeneratePrimesSieve();
    }

    // --- Источник данных для тестов ---
    // Формат: n (верхняя граница), ожидаемый список простых чисел
    static Stream<Arguments> provideLimitsAndExpectedPrimes() {
        return Stream.of(
                Arguments.of(10, List.of(2, 3, 5, 7)),
                Arguments.of(20, List.of(2, 3, 5, 7, 11, 13, 17, 19)),
                Arguments.of(30, List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)),
                Arguments.of(2, List.of(2)),
                Arguments.of(3, List.of(2, 3)),
                Arguments.of(11, List.of(2, 3, 5, 7, 11)),
                Arguments.of(100, Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97))
        );
    }

    // --- Тесты для корректных значений n >= 2 ---
    @ParameterizedTest(name = "Простые числа до n={0}")
    @MethodSource("provideLimitsAndExpectedPrimes")
    @DisplayName("Должен генерировать правильный список простых чисел для n >= 2")
    void shouldGenerateCorrectPrimesForValidInput(int n, List<Integer> expectedPrimes) {
        List<Integer> actualPrimes = generator.generatePrimes(n);
        assertEquals(expectedPrimes, actualPrimes, "Сгенерированный список простых чисел не совпадает с ожидаемым");
    }

    // --- Тесты для граничных случаев n < 2 ---
    @ParameterizedTest(name = "n={0} -> Ожидание: пустой список")
    @ValueSource(ints = {-5, -1, 0, 1}) // Различные значения < 2
    @DisplayName("Должен возвращать пустой список для n < 2")
    void shouldReturnEmptyListForNLessThan2(int n) {
        List<Integer> actualPrimes = generator.generatePrimes(n);
        assertTrue(actualPrimes.isEmpty(), "Список должен быть пустым, если n < 2");
        // Дополнительно можно проверить, что возвращается именно Collections.emptyList() или его эквивалент
        assertEquals(Collections.emptyList(), actualPrimes, "Должен возвращаться неизменяемый пустой список");
    }

    // Тест на потенциально большой n (может вызвать OutOfMemoryError, если памяти мало)
    // Полезен для проверки, что алгоритм в принципе работает, но может быть нестабильным
     @Test
     @DisplayName("Тест для относительно большого n (может требовать много памяти)")
     void testLargeN() {
         int largeN = 1_000_000; // Примерно 1МБ для boolean массива
         assertDoesNotThrow(() -> {
             List<Integer> primes = generator.generatePrimes(largeN);
             assertFalse(primes.isEmpty(), "Список для большого n не должен быть пустым");
             // Можно добавить проверку количества простых чисел, если оно известно (для 1M ~ 78498)
             // assertEquals(78498, primes.size());
         }, "Генерация для большого N не должна выбрасывать неожиданных исключений (кроме OOM)");
     }
}

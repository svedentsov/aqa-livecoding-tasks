package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для GCD (Наибольший Общий Делитель)")
class GCDTest {

    private GCD gcdCalculator;

    @BeforeEach
    void setUp() {
        gcdCalculator = new GCD();
    }

    // --- Тесты для gcdRecursive ---
    @Nested
    @DisplayName("Метод gcdRecursive")
    class RecursiveGcdTests {

        @ParameterizedTest(name = "НОД({0}, {1}) должен быть {2}")
        @CsvSource({
                // Основные случаи
                "48, 18, 6",
                "18, 48, 6",  // Порядок не важен
                "10, 5, 5",
                "5, 10, 5",
                "7, 13, 1",   // Взаимно простые
                "13, 7, 1",
                "21, 49, 7",
                "100, 75, 25",
                // Случаи с нулем
                "0, 5, 5",
                "5, 0, 5",
                "0, 0, 0",
                // Случаи с единицей
                "1, 1, 1",
                "1, 5, 1",
                "5, 1, 1",
                "2147483647, 1, 1", // С MAX_VALUE
                "2147483647, 0, 2147483647",
                "2147483647, 2147483647, 2147483647",
                // Случаи с отрицательными числами
                "-48, 18, 6",
                "48, -18, 6",
                "-48, -18, 6"
        })
        @DisplayName("Проверка НОД для различных пар чисел")
        void shouldCalculateGcdCorrectly(int a, int b, int expectedGcd) {
            assertEquals(expectedGcd, gcdCalculator.gcdRecursive(a, b));
        }
    }

    // --- Тесты для gcdIterative ---
    @Nested
    @DisplayName("Метод gcdIterative")
    class IterativeGcdTests {

        @ParameterizedTest(name = "НОД({0}, {1}) должен быть {2}")
        @CsvSource({
                // Основные случаи
                "48, 18, 6",
                "18, 48, 6",  // Порядок не важен
                "10, 5, 5",
                "5, 10, 5",
                "7, 13, 1",   // Взаимно простые
                "13, 7, 1",
                "21, 49, 7",
                "100, 75, 25",
                // Случаи с нулем
                "0, 5, 5",
                "5, 0, 5",
                "0, 0, 0",
                // Случаи с единицей
                "1, 1, 1",
                "1, 5, 1",
                "5, 1, 1",
                "2147483647, 1, 1", // С MAX_VALUE
                "2147483647, 0, 2147483647",
                "2147483647, 2147483647, 2147483647",
                // Случаи с отрицательными числами
                "-48, 18, 6",
                "48, -18, 6",
                "-48, -18, 6"
        })
        @DisplayName("Проверка НОД для различных пар чисел")
        void shouldCalculateGcdCorrectly(int a, int b, int expectedGcd) {
            assertEquals(expectedGcd, gcdCalculator.gcdIterative(a, b));
        }
    }
}

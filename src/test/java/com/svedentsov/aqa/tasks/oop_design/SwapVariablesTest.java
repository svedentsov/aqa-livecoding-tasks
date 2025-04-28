package com.svedentsov.aqa.tasks.oop_design;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@DisplayName("Тесты для SwapVariables")
class SwapVariablesTest {

    private SwapVariables swapper;

    @BeforeEach
    void setUp() {
        swapper = new SwapVariables();
    }

    // --- Источник данных для тестов ---
    // Формат: initial_a, initial_b, expected_a_after_swap, expected_b_after_swap
    static Stream<Arguments> provideSwapArguments() {
        return Stream.of(
                Arguments.of(5, 10, 10, 5), // Положительные числа
                Arguments.of(-7, 15, 15, -7), // Отрицательное и положительное
                Arguments.of(-3, -9, -9, -3), // Оба отрицательные
                Arguments.of(0, 100, 100, 0), // С нулем
                Arguments.of(42, 42, 42, 42), // Одинаковые числа
                Arguments.of(Integer.MAX_VALUE, 1, 1, Integer.MAX_VALUE), // Граничные значения 1
                Arguments.of(Integer.MAX_VALUE, 0, 0, Integer.MAX_VALUE), // Граничные значения 2
                Arguments.of(Integer.MIN_VALUE, -1, -1, Integer.MIN_VALUE), // Граничные значения 3
                Arguments.of(Integer.MIN_VALUE, 0, 0, Integer.MIN_VALUE), // Граничные значения 4
                Arguments.of(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE) // Противоположные границы
        );
    }

    // --- Тесты для swapUsingArithmetic ---
    @Nested
    @DisplayName("Тесты для метода swapUsingArithmetic")
    class ArithmeticSwapTests {

        @ParameterizedTest(name = "a={0}, b={1} -> swapped a={2}, b={3}")
        @MethodSource("com.svedentsov.aqa.tasks.oop_design.SwapVariablesTest#provideSwapArguments")
        void shouldReturnSwappedValuesUsingArithmetic(int initialA, int initialB, int expectedA, int expectedB) {
            int[] result = swapper.swapUsingArithmetic(initialA, initialB);
            assertArrayEquals(new int[]{expectedA, expectedB}, result,
                    "Арифметический обмен должен вернуть корректно поменянные значения");
            // Можно добавить отдельные ассерты для читаемости
            // assertEquals(expectedA, result[0], "Значение 'a' после арифметического обмена некорректно");
            // assertEquals(expectedB, result[1], "Значение 'b' после арифметического обмена некорректно");
        }
    }

    // --- Тесты для swapUsingXor ---
    @Nested
    @DisplayName("Тесты для метода swapUsingXor")
    class XorSwapTests {

        @ParameterizedTest(name = "a={0}, b={1} -> swapped a={2}, b={3}")
        @MethodSource("com.svedentsov.aqa.tasks.oop_design.SwapVariablesTest#provideSwapArguments")
        void shouldReturnSwappedValuesUsingXor(int initialA, int initialB, int expectedA, int expectedB) {
            int[] result = swapper.swapUsingXor(initialA, initialB);
            assertArrayEquals(new int[]{expectedA, expectedB}, result,
                    "XOR обмен должен вернуть корректно поменянные значения");
            // Можно добавить отдельные ассерты для читаемости
            // assertEquals(expectedA, result[0], "Значение 'a' после XOR обмена некорректно");
            // assertEquals(expectedB, result[1], "Значение 'b' после XOR обмена некорректно");
        }
    }
}

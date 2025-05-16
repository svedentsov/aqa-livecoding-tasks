package com.svedentsov.aqa.tasks.dp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для Knapsack01 (Задача о рюкзаке 0/1)")
class Knapsack01Test {

    private Knapsack01 solver;

    @BeforeEach
    void setUp() {
        solver = new Knapsack01();
    }

    // --- Источник данных для валидных тестов ---
    // Формат: weights[], values[], capacity, expectedMaxValue
    static Stream<Arguments> provideValidKnapsackProblems() {
        return Stream.of(
                Arguments.of(new int[]{10, 20, 30}, new int[]{60, 100, 120}, 50, 220, "Example 1"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, new int[]{10, 20, 30, 40, 50}, 7, 70, "Example 2"),
                Arguments.of(new int[]{5, 4, 6, 3}, new int[]{10, 40, 30, 50}, 10, 90, "Example 3"),
                Arguments.of(new int[]{10, 20, 30}, new int[]{60, 100, 120}, 0, 0, "Capacity 0"),
                Arguments.of(new int[]{10}, new int[]{60}, 5, 0, "Item heavier than capacity"),
                Arguments.of(new int[]{}, new int[]{}, 10, 0, "Empty items"),
                // Корректный тест, когда все предметы влезают
                Arguments.of(new int[]{10, 20, 30}, new int[]{60, 100, 120}, 220, 280, "All items fit"),
                // Тест: выбор одного крупного ценноcго предмета vs нескольких мелких
                Arguments.of(new int[]{1, 1, 1, 1, 5}, new int[]{1, 1, 1, 1, 10}, 5, 10, "Single large vs many small"),
                // Тест: несколько мелких, включая один очень ценный, все помещаются
                Arguments.of(new int[]{1, 1, 1, 1, 1}, new int[]{10, 1, 1, 1, 1}, 5, 14, "Multiple small including one very valuable")
        );
    }

    // --- Источник данных для тестов исключений ---
    // Формат: weights[], values[], capacity, expectedMessagePart
    static Stream<Arguments> provideInvalidKnapsackInputs() {
        return Stream.of(
                Arguments.of(null, new int[]{1}, 10, "Input arrays (weights, values) cannot be null."),
                Arguments.of(new int[]{1}, null, 10, "Input arrays (weights, values) cannot be null."),
                Arguments.of(new int[]{1, 2}, new int[]{1}, 10, "Weights and values arrays must have the same length."),
                Arguments.of(new int[]{1}, new int[]{1}, -1, "Capacity cannot be negative."),
                Arguments.of(new int[]{-1}, new int[]{1}, 10, "Weights must be non-negative."),
                Arguments.of(new int[]{1}, new int[]{-1}, 10, "Values must be non-negative.")
        );
    }

    @Nested
    @DisplayName("Метод knapsack01DPTable (2D DP)")
    class DPTableTests {
        @ParameterizedTest(name = "{4}")
        @MethodSource("com.svedentsov.aqa.tasks.dp.Knapsack01Test#provideValidKnapsackProblems")
        void shouldSolveKnapsackCorrectly(int[] weights, int[] values, int capacity, int expectedMaxValue, String description) {
            assertEquals(expectedMaxValue, solver.knapsack01DPTable(weights, values, capacity), description);
        }

        @ParameterizedTest(name = "Невалидный ввод ({3})")
        @MethodSource("com.svedentsov.aqa.tasks.dp.Knapsack01Test#provideInvalidKnapsackInputs")
        void shouldThrowIllegalArgumentExceptionForInvalidInput(int[] weights, int[] values, int capacity, String expectedMessagePart) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
                solver.knapsack01DPTable(weights, values, capacity);
            });
            assertTrue(ex.getMessage().contains(expectedMessagePart));
        }
    }

    @Nested
    @DisplayName("Метод knapsack01DPOptimized (1D DP)")
    class DPOptimizedTests {
        @ParameterizedTest(name = "{4}")
        @MethodSource("com.svedentsov.aqa.tasks.dp.Knapsack01Test#provideValidKnapsackProblems")
        void shouldSolveKnapsackCorrectlyOptimized(int[] weights, int[] values, int capacity, int expectedMaxValue, String description) {
            assertEquals(expectedMaxValue, solver.knapsack01DPOptimized(weights, values, capacity), description);
        }

        @ParameterizedTest(name = "Невалидный ввод ({3})")
        @MethodSource("com.svedentsov.aqa.tasks.dp.Knapsack01Test#provideInvalidKnapsackInputs")
        void shouldThrowIllegalArgumentExceptionForInvalidInput(int[] weights, int[] values, int capacity, String expectedMessagePart) {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
                solver.knapsack01DPOptimized(weights, values, capacity);
            });
            assertTrue(ex.getMessage().contains(expectedMessagePart));
        }
    }
}

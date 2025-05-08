package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тесты для MaximumSubarraySum (Kadane)")
class MaximumSubarraySumTest {

    private MaximumSubarraySum solver;

    @BeforeEach
    void setUp() {
        solver = new MaximumSubarraySum();
    }

    // --- Источник данных для тестов ---
    // Формат: массив nums, ожидаемая максимальная сумма
    static Stream<Arguments> provideArraysForMaxSum() {
        return Stream.of(
                Arguments.of(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}, 6), // Стандартный пример
                Arguments.of(new int[]{1}, 1),                             // Один элемент
                Arguments.of(new int[]{5, 4, -1, 7, 8}, 23),               // Все в сумме
                Arguments.of(new int[]{-1, -2, -3}, -1),                   // Только отрицательные
                Arguments.of(new int[]{-1}, -1),                           // Один отрицательный элемент
                Arguments.of(new int[]{1, 2, 3, -2, 5}, 9),                // Пример 1
                Arguments.of(new int[]{-1, -2, 3, 4, -5, 6}, 8),           // Пример 2
                Arguments.of(new int[]{2, 3, -1, -20, 5, 5}, 10),          // Пример 3
                Arguments.of(new int[]{-2, -1, -4, -3}, -1)                // Еще отрицательные
        );
    }

    // --- Параметризованный тест для maxSubArraySum ---
    @ParameterizedTest(name = "Массив: {0} -> Ожидаемая сумма: {1}")
    @MethodSource("provideArraysForMaxSum")
    @DisplayName("Должен находить правильную максимальную сумму подмассива")
    void shouldFindMaxSubarraySum(int[] nums, int expectedSum) {
        assertEquals(expectedSum, solver.maxSubArraySum(nums));
    }

    // --- Тесты для некорректных входов (null или пустой массив) ---
    @ParameterizedTest(name = "Вход: {0} -> Ожидание: IllegalArgumentException")
    @NullAndEmptySource // Проверяет null и new int[]{}
    @DisplayName("Должен выбросить IllegalArgumentException для null или пустого массива")
    void shouldThrowIllegalArgumentExceptionForNullOrEmptyArray(int[] nums) {
        String inputType = (nums == null) ? "null" : "пустой массив";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            solver.maxSubArraySum(nums);
        });
        assertEquals("Input array cannot be null or empty.", exception.getMessage(),
                "Неверное сообщение об ошибке для " + inputType);
    }
}

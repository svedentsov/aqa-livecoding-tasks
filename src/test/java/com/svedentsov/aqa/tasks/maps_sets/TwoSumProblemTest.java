package com.svedentsov.aqa.tasks.maps_sets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для TwoSumProblem")
class TwoSumProblemTest {

    private TwoSumProblem solver;

    @BeforeEach
    void setUp() {
        solver = new TwoSumProblem();
    }

    // --- Источник данных для тестов ---
    // Формат: массив nums, target, ожидаемый массив индексов (или пустой)
    static Stream<Arguments> provideTwoSumCases() {
        return Stream.of(
                // Примеры из описания
                Arguments.of(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
                Arguments.of(new int[]{3, 2, 4}, 6, new int[]{1, 2}),
                Arguments.of(new int[]{3, 3}, 6, new int[]{0, 1}), // Одинаковые числа
                // Другие случаи
                Arguments.of(new int[]{0, 4, 3, 0}, 0, new int[]{0, 3}), // С нулями
                Arguments.of(new int[]{-1, -3, 5, 9}, 4, new int[]{0, 2}), // С отрицательными
                Arguments.of(new int[]{-1, -3, -5, -9}, -8, new int[]{1, 2}), // Сумма отрицательных
                Arguments.of(new int[]{2, 5, 5, 11}, 10, new int[]{1, 2}), // Дубликаты, образующие сумму
                // Случаи без решения
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 10, new int[0]),
                Arguments.of(new int[]{}, 5, new int[0]), // Пустой массив
                Arguments.of(new int[]{5}, 10, new int[0]) // Массив из одного элемента
        );
    }

    // --- Параметризованный тест для findTwoSumIndices ---
    @ParameterizedTest(name = "nums={0}, target={1} -> Ожидание: {2}")
    @MethodSource("provideTwoSumCases")
    @DisplayName("Должен находить правильные индексы или возвращать пустой массив")
    void shouldFindTwoSumIndices(int[] nums, int target, int[] expectedIndices) {
        int[] actualIndices = solver.findTwoSumIndices(nums, target);

        // Сортируем оба массива перед сравнением, так как порядок индексов в результате не гарантирован
        Arrays.sort(actualIndices);
        Arrays.sort(expectedIndices);

        assertArrayEquals(expectedIndices, actualIndices,
                "Массив индексов должен совпадать с ожидаемым (после сортировки)");
    }

    // --- Тест для некорректного входа (null массив) ---
    @Test
    @DisplayName("Должен выбросить IllegalArgumentException для null массива")
    void shouldThrowIllegalArgumentExceptionForNullInput() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            solver.findTwoSumIndices(null, 10);
        });
        assertEquals("Input array cannot be null.", exception.getMessage(),
                "Неверное сообщение об ошибке для null массива");
    }
}

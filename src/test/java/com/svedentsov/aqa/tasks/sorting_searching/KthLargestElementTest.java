package com.svedentsov.aqa.tasks.sorting_searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тесты для KthLargestElement")
class KthLargestElementTest {

    private KthLargestElement finder;

    @BeforeEach
    void setUp() {
        finder = new KthLargestElement();
    }

    // --- Источник данных для валидных тестов ---
    // Формат: массив nums, k, ожидаемый результат
    static Stream<Arguments> provideArraysForKthLargest() {
        return Stream.of(
                Arguments.of(new int[]{3, 2, 1, 5, 6, 4}, 2, 5), // Пример 1
                Arguments.of(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, 4), // Пример 2 (с дубликатами)
                Arguments.of(new int[]{1}, 1, 1),                 // Один элемент
                Arguments.of(new int[]{2, 1}, 1, 2),               // k=1 (максимальный)
                Arguments.of(new int[]{2, 1}, 2, 1),               // k=n (минимальный)
                Arguments.of(new int[]{7, 6, 5, 4, 3, 2, 1}, 3, 5), // Отсортирован по убыванию
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7}, 3, 5), // Отсортирован по возрастанию
                Arguments.of(new int[]{5, 2, 4, 1, 3, 6, 0}, 7, 0), // k=n (минимальный 0)
                Arguments.of(new int[]{5, 2, 4, 1, 3, 6, 0}, 1, 6)  // k=1 (максимальный)
        );
    }

    // --- Источник данных для тестов исключений ---
    // Формат: массив nums, k
    static Stream<Arguments> provideInvalidInputs() {
        return Stream.of(
                Arguments.of(new int[]{1, 2}, 3),   // k > length
                Arguments.of(new int[]{1, 2}, 0),   // k <= 0
                Arguments.of(new int[]{1, 2}, -1),  // k <= 0
                Arguments.of(new int[]{}, 1),     // Пустой массив
                Arguments.of(null, 1)       // Null массив
        );
    }

    // --- Тесты для findKthLargestSorting ---
    @Nested
    @DisplayName("Метод findKthLargestSorting")
    class SortingMethodTests {

        @ParameterizedTest(name = "arr={0}, k={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.sorting_searching.KthLargestElementTest#provideArraysForKthLargest")
        void shouldFindKthLargestUsingSorting(int[] originalNums, int k, int expectedValue) {
            // Важно: Создаем копию, т.к. метод сортировки модифицирует массив
            int[] numsCopy = Arrays.copyOf(originalNums, originalNums.length);
            int actualValue = finder.findKthLargestSorting(numsCopy, k);
            assertEquals(expectedValue, actualValue);
        }

        @ParameterizedTest(name = "Невалидный вход: arr={0}, k={1} -> IllegalArgumentException")
        @MethodSource("com.svedentsov.aqa.tasks.sorting_searching.KthLargestElementTest#provideInvalidInputs")
        void shouldThrowExceptionForInvalidInput(int[] nums, int k) {
            assertThrows(IllegalArgumentException.class, () -> {
                // Для null массива копия не нужна
                int[] numsCopy = (nums == null) ? null : Arrays.copyOf(nums, nums.length);
                finder.findKthLargestSorting(numsCopy, k);
            }, "Должно быть выброшено исключение для невалидных входов");
        }
    }

    // --- Тесты для findKthLargestHeap ---
    @Nested
    @DisplayName("Метод findKthLargestHeap")
    class HeapMethodTests {

        @ParameterizedTest(name = "arr={0}, k={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.sorting_searching.KthLargestElementTest#provideArraysForKthLargest")
        void shouldFindKthLargestUsingHeap(int[] nums, int k, int expectedValue) {
            // Метод с кучей не модифицирует исходный массив
            int actualValue = finder.findKthLargestHeap(nums, k);
            assertEquals(expectedValue, actualValue);
        }

        @ParameterizedTest(name = "Невалидный вход: arr={0}, k={1} -> IllegalArgumentException")
        @MethodSource("com.svedentsov.aqa.tasks.sorting_searching.KthLargestElementTest#provideInvalidInputs")
        void shouldThrowExceptionForInvalidInput(int[] nums, int k) {
            assertThrows(IllegalArgumentException.class, () -> {
                finder.findKthLargestHeap(nums, k);
            }, "Должно быть выброшено исключение для невалидных входов");
        }
    }
}

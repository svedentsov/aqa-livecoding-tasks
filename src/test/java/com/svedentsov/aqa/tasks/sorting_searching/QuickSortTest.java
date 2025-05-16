package com.svedentsov.aqa.tasks.sorting_searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для QuickSort")
class QuickSortTest {

    private QuickSort sorter;

    @BeforeEach
    void setUp() {
        sorter = new QuickSort();
    }

    // --- Источник данных для тестов ---
    // Формат: исходный массив, ожидаемый отсортированный массив, описание
    static Stream<Arguments> provideArraysForSorting() {
        return Stream.of(
                Arguments.of(new int[]{5, 1, 4, 2, 8, 5, 0, -3, 99}, new int[]{-3, 0, 1, 2, 4, 5, 5, 8, 99}, "Смешанный массив"),
                Arguments.of(new int[]{1}, new int[]{1}, "Один элемент"),
                Arguments.of(new int[]{3, 1}, new int[]{1, 3}, "Два элемента"),
                Arguments.of(new int[]{}, new int[]{}, "Пустой массив"),
                Arguments.of(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "Обратно отсортированный"),
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "Уже отсортированный"),
                Arguments.of(new int[]{2, 3, 1, 5, 4, 7, 6, 9, 8}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "Случайный порядок"),
                Arguments.of(new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5}, new int[]{1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9}, "С дубликатами"),
                Arguments.of(new int[]{5, -2, 10, 0, -8, 10, -2}, new int[]{-8, -2, -2, 0, 5, 10, 10}, "С отрицательными и дубликатами")
        );
    }

    // --- Параметризованный тест для quickSort ---
    @ParameterizedTest(name = "{2}") // Используем описание из Arguments
    @MethodSource("provideArraysForSorting")
    @DisplayName("Должен корректно сортировать различные массивы")
    void shouldSortArraysCorrectly(int[] originalArray, int[] expectedSortedArray, String description) {
        // quickSort модифицирует массив на месте.
        // В @MethodSource Arguments.of(new int[]{...}) создает новый экземпляр массива для каждого набора аргументов,
        // поэтому можно передавать originalArray напрямую.
        sorter.quickSort(originalArray);
        assertArrayEquals(expectedSortedArray, originalArray, "Массив должен быть отсортирован: " + description);
    }

    // --- Тесты для граничных случаев (null, пустой, один элемент) ---
    // Эти случаи обрабатываются в самом начале метода quickSort и не требуют копирования

    @Test
    @DisplayName("Должен корректно обрабатывать null массив (ничего не делать)")
    void shouldHandleNullArrayGracefully() {
        int[] nullArray = null;
        assertDoesNotThrow(() -> sorter.quickSort(nullArray), "Обработка null массива не должна вызывать исключений");
        assertNull(nullArray, "Null массив должен остаться null"); // Проверка для полноты
    }

    @Test
    @DisplayName("Должен корректно обрабатывать пустой массив (остается пустым)")
    void shouldHandleEmptyArrayGracefully() {
        int[] emptyArray = new int[]{};
        int[] expectedArray = new int[]{}; // Ожидаем, что он останется пустым
        sorter.quickSort(emptyArray);
        assertArrayEquals(expectedArray, emptyArray, "Пустой массив должен оставаться пустым");
    }

    @Test
    @DisplayName("Должен корректно обрабатывать массив из одного элемента (остается без изменений)")
    void shouldHandleSingleElementArrayGracefully() {
        int[] singleElementArray = {42};
        int[] expectedArray = {42}; // Ожидаем, что он останется таким же
        sorter.quickSort(singleElementArray);
        assertArrayEquals(expectedArray, singleElementArray, "Массив из одного элемента должен оставаться неизменным");
    }
}

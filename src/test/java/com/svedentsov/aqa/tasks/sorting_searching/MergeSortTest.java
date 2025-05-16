package com.svedentsov.aqa.tasks.sorting_searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Тесты для MergeSort")
class MergeSortTest {

    private MergeSort sorter;

    @BeforeEach
    void setUp() {
        sorter = new MergeSort();
    }

    // --- Источник данных для тестов ---
    // Формат: исходный массив, ожидаемый отсортированный массив, описание
    static Stream<Arguments> provideArraysForSorting() {
        return Stream.of(
                Arguments.of(new int[]{5, 1, 4, 2, 8, 5, 0, -3, 99}, new int[]{-3, 0, 1, 2, 4, 5, 5, 8, 99}, "Смешанный массив"),
                Arguments.of(new int[]{1}, new int[]{1}, "Один элемент"),
                Arguments.of(new int[]{3, 1}, new int[]{1, 3}, "Два элемента"),
                Arguments.of(new int[]{}, new int[]{}, "Пустой массив"),
                Arguments.of(new int[]{9, 8, 7, 6, 5}, new int[]{5, 6, 7, 8, 9}, "Обратно отсортированный"),
                Arguments.of(new int[]{2, 3, 1, 5, 4}, new int[]{1, 2, 3, 4, 5}, "Другой случай"),
                Arguments.of(new int[]{2, 2, 1, 1, 3, 3}, new int[]{1, 1, 2, 2, 3, 3}, "С дубликатами"),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5}, "Уже отсортированный"),
                Arguments.of(new int[]{5, -2, 10, 0, -8}, new int[]{-8, -2, 0, 5, 10}, "С отрицательными числами")
        );
    }

    // --- Параметризованный тест для mergeSort ---
    @ParameterizedTest(name = "{2}") // Используем описание из Arguments
    @MethodSource("provideArraysForSorting")
    @DisplayName("Должен корректно сортировать различные массивы")
    void shouldSortArraysCorrectly(int[] originalArray, int[] expectedSortedArray, String description) {
        // Метод mergeSort модифицирует массив на месте.
        // Для теста мы передаем originalArray, который будет изменен,
        // и затем сравниваем его с expectedSortedArray.
        // Если бы originalArray был нужен в неизменном виде после теста,
        // мы бы передавали Arrays.copyOf(originalArray, originalArray.length).
        sorter.mergeSort(originalArray);
        assertArrayEquals(expectedSortedArray, originalArray, "Массив должен быть отсортирован: " + description);
    }

    // --- Тесты для граничных случаев (null, пустой, один элемент) ---
    // Эти случаи обрабатываются в самом начале метода mergeSort и не требуют копирования

    @Test
    @DisplayName("Должен корректно обрабатывать null массив (ничего не делать)")
    void shouldHandleNullArrayGracefully() {
        int[] nullArray = null;
        assertDoesNotThrow(() -> sorter.mergeSort(nullArray), "Обработка null массива не должна вызывать исключений");
        // Проверять nullArray == null после вызова бессмысленно, т.к. ссылка не меняется, а метод void.
        // Важно, что нет NullPointerException.
    }

    @Test
    @DisplayName("Должен корректно обрабатывать пустой массив (остается пустым)")
    void shouldHandleEmptyArrayGracefully() {
        int[] emptyArray = new int[]{};
        int[] expectedArray = new int[]{}; // Ожидаем, что он останется пустым
        sorter.mergeSort(emptyArray);
        assertArrayEquals(expectedArray, emptyArray, "Пустой массив должен оставаться пустым");
    }

    @Test
    @DisplayName("Должен корректно обрабатывать массив из одного элемента (остается без изменений)")
    void shouldHandleSingleElementArrayGracefully() {
        int[] singleElementArray = {42};
        int[] expectedArray = {42}; // Ожидаем, что он останется таким же
        sorter.mergeSort(singleElementArray);
        assertArrayEquals(expectedArray, singleElementArray, "Массив из одного элемента должен оставаться неизменным");
    }
}

package com.svedentsov.aqa.tasks.sorting_searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Тесты для пузырьковой сортировки (Bubble Sort)")
class BubbleSortTest {

    private BubbleSort bubbleSort;

    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }

    // Источник тестовых данных: пары {исходный_массив, ожидаемый_отсортированный_массив}
    static Stream<Arguments> provideArraysForSorting() {
        return Stream.of(
                // Стандартный случай
                arguments(new int[]{5, 1, 4, 2, 8}, new int[]{1, 2, 4, 5, 8}, "Стандартный случай"),
                // Уже отсортированный массив
                arguments(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5}, "Уже отсортированный"),
                // Обратно отсортированный массив (худший случай)
                arguments(new int[]{5, 4, 3, 2, 1}, new int[]{1, 2, 3, 4, 5}, "Обратно отсортированный"),
                // С дубликатами
                arguments(new int[]{5, 1, 4, 1, 5, 8}, new int[]{1, 1, 4, 5, 5, 8}, "С дубликатами"),
                // С отрицательными и нулем
                arguments(new int[]{-1, -5, 2, 0, -1}, new int[]{-5, -1, -1, 0, 2}, "С отрицательными и нулем"),
                // Массив из двух элементов
                arguments(new int[]{2, 1}, new int[]{1, 2}, "Два элемента"),
                // Массив из двух одинаковых элементов
                arguments(new int[]{7, 7}, new int[]{7, 7}, "Два одинаковых элемента"),
                // Более длинный массив
                arguments(new int[]{9, 1, 5, 3, 7, 2, 8, 6, 4}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "Более длинный массив")
        );
    }

    @ParameterizedTest(name = "{2}") // Используем 3-й аргумент (описание) как имя теста
    @MethodSource("provideArraysForSorting")
    @DisplayName("Проверка сортировки для различных массивов")
    void testBubbleSort(int[] originalArray, int[] expectedSortedArray, String description) {
        // Важно: сортировка происходит на месте, поэтому передаем копию,
        // если не хотим менять исходный массив из provideArraysForSorting (хотя он и так новый для каждого теста)
        int[] arrayToSort = Arrays.copyOf(originalArray, originalArray.length);

        bubbleSort.bubbleSort(arrayToSort);

        // Проверяем, что отсортированный массив соответствует ожидаемому
        assertArrayEquals(expectedSortedArray, arrayToSort,
                "Массив должен быть отсортирован правильно для случая: " + description);
    }

    @Test
    @DisplayName("Проверка сортировки пустого массива")
    void testBubbleSortEmptyArray() {
        int[] emptyArray = {};
        bubbleSort.bubbleSort(emptyArray);
        // Пустой массив после сортировки должен остаться пустым
        assertArrayEquals(new int[]{}, emptyArray, "Пустой массив должен остаться пустым");
    }

    @Test
    @DisplayName("Проверка сортировки массива с одним элементом")
    void testBubbleSortSingleElementArray() {
        int[] singleElementArray = {42};
        bubbleSort.bubbleSort(singleElementArray);
        // Массив с одним элементом должен остаться неизменным
        assertArrayEquals(new int[]{42}, singleElementArray, "Массив с одним элементом должен остаться неизменным");
    }

    @Test
    @DisplayName("Проверка сортировки null массива")
    void testBubbleSortNullArray() {
        int[] nullArray = null;
        // Метод должен корректно обработать null, не выбрасывая исключение
        assertDoesNotThrow(() -> bubbleSort.bubbleSort(nullArray),
                "Вызов bubbleSort с null массивом не должен выбрасывать исключение");
        // Дополнительно можно проверить, что null остался null (хотя это очевидно)
        assertNull(nullArray, "null массив должен остаться null");
    }
}

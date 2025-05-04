package com.svedentsov.aqa.tasks.sorting_searching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для BinarySearch")
class BinarySearchTest {

    private BinarySearch searcher;

    @BeforeEach
    void setUp() {
        searcher = new BinarySearch();
    }

    // --- Источник данных для тестов ---
    // Формат: массив, ключ, ожидаемый индекс (или -1)
    static Stream<Arguments> provideArraysAndKeys() {
        int[] arr1 = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        int[] arr2 = {2, 2, 2, 2, 2};
        int[] arr3 = {10};
        int[] arr4 = {};
        int[] arrNeg = {-10, -5, 0, 5, 10};

        return Stream.of(
                // Ключ присутствует
                Arguments.of(arr1, 5, 2),   // В середине
                Arguments.of(arr1, 1, 0),   // Первый элемент
                Arguments.of(arr1, 19, 9),  // Последний элемент
                Arguments.of(arr1, 13, 6),  // Другой элемент
                Arguments.of(arrNeg, -5, 1), // Отрицательный ключ
                Arguments.of(arrNeg, 0, 2),  // Ноль
                Arguments.of(arr3, 10, 0), // Один элемент, ключ есть
                // Ключ отсутствует
                Arguments.of(arr1, 6, -1),  // Между элементами
                Arguments.of(arr1, 0, -1),  // Меньше первого
                Arguments.of(arr1, 20, -1), // Больше последнего
                Arguments.of(arrNeg, -11, -1),// Меньше первого (отриц.)
                Arguments.of(arrNeg, 11, -1), // Больше последнего (отриц.)
                Arguments.of(arrNeg, 1, -1), // Между элементами (отриц.)
                Arguments.of(arr3, 5, -1),  // Один элемент, ключа нет
                Arguments.of(arr2, 3, -1),  // Дубликаты, ключа нет
                // Пустой и null массивы
                Arguments.of(arr4, 5, -1),  // Пустой массив
                Arguments.of(null, 5, -1)  // Null массив
                // Случай с дубликатами, где ключ есть - стандартный бин. поиск найдет какой-то индекс
                , Arguments.of(arr2, 2, 2) // Ожидаем, что стандартный поиск найдет средний индекс 2
                // Если бы нужно было найти ПЕРВЫЙ или ПОСЛЕДНИЙ индекс, алгоритм был бы сложнее
        );
    }

    // --- Параметризованный тест для binarySearch ---
    @ParameterizedTest(name = "Массив: {0}, Ключ: {1} -> Ожидаемый индекс: {2}")
    @MethodSource("provideArraysAndKeys")
    @DisplayName("Должен находить индекс ключа или возвращать -1")
    void shouldFindKeyIndexOrReturnMinusOne(int[] sortedArray, int key, int expectedIndex) {
        int actualIndex = searcher.binarySearch(sortedArray, key);

        // Для случая с дубликатами (arr2), где ключ найден, просто проверим,
        // что по найденному индексу действительно находится искомый ключ.
        if (expectedIndex != -1 && sortedArray != null && sortedArray.length > 0 && java.util.Arrays.equals(sortedArray, new int[]{2, 2, 2, 2, 2}) && key == 2) {
            assertTrue(actualIndex >= 0 && actualIndex < sortedArray.length, "Индекс должен быть в пределах массива");
            assertEquals(key, sortedArray[actualIndex], "Элемент по найденному индексу должен быть равен ключу");
        } else {
            // Для всех остальных случаев сравниваем индекс напрямую
            assertEquals(expectedIndex, actualIndex, "Найденный индекс не совпадает с ожидаемым");
        }
    }
}

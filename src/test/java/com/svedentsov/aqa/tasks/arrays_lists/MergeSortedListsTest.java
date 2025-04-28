package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для MergeSortedLists")
class MergeSortedListsTest {

    private MergeSortedLists merger;

    @BeforeEach
    void setUp() {
        merger = new MergeSortedLists();
    }

    // --- Источник данных для тестов ---
    // Формат: list1, list2, expectedMergedList
    static Stream<Arguments> provideListsForMerging() {
        // Используем Arrays.asList для списков с null
        // null для целых списков передаем напрямую
        return Stream.of(
                // Стандартные случаи
                Arguments.of(List.of(1, 3, 5, 7), List.of(2, 4, 6, 8), List.of(1, 2, 3, 4, 5, 6, 7, 8)),
                Arguments.of(List.of(10, 20), List.of(5, 15, 25), List.of(5, 10, 15, 20, 25)),
                // Пустые списки
                Arguments.of(List.of(1, 2, 3), Collections.emptyList(), List.of(1, 2, 3)),
                Arguments.of(Collections.emptyList(), List.of(4, 5), List.of(4, 5)),
                Arguments.of(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()),
                // Null списки
                Arguments.of(null, List.of(1, 5), List.of(1, 5)),
                Arguments.of(List.of(2, 4), null, List.of(2, 4)),
                Arguments.of(null, null, Collections.emptyList()),
                // Списки с null элементами (null должны быть пропущены)
                Arguments.of(Arrays.asList(1, 3, null, 5), Arrays.asList(null, 2, 4, null, 6), List.of(1, 2, 3, 4, 5, 6)),
                Arguments.of(Arrays.asList(null, null), Arrays.asList(null, 1, null), List.of(1)),
                Arguments.of(Arrays.asList(1, 2), Arrays.asList(null, null), List.of(1, 2)),
                // Списки с дубликатами
                Arguments.of(List.of(1, 3, 3, 5), List.of(3, 4, 4, 6), List.of(1, 3, 3, 3, 4, 4, 5, 6)),
                // С отрицательными и нулем
                Arguments.of(List.of(-5, -1, 0, 10), List.of(-2, 0, 3, 10), List.of(-5, -2, -1, 0, 0, 3, 10, 10)),
                // Один список полностью меньше другого
                Arguments.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(1, 2, 3, 4, 5, 6)),
                // Один список полностью больше другого
                Arguments.of(List.of(4, 5, 6), List.of(1, 2, 3), List.of(1, 2, 3, 4, 5, 6))
        );
    }

    // --- Тест для mergeSortedLists ---
    @ParameterizedTest(name = "List1: {0}, List2: {1} -> Merged: {2}")
    @MethodSource("provideListsForMerging")
    @DisplayName("Должен корректно сливать два отсортированных списка")
    void shouldMergeSortedListsCorrectly(List<Integer> list1, List<Integer> list2, List<Integer> expectedMergedList) {
        List<Integer> actualMergedList = merger.mergeSortedLists(list1, list2);
        assertEquals(expectedMergedList, actualMergedList, "Результат слияния списков не совпадает с ожидаемым");
    }
}

package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для FindIntersectionArrays")
class FindIntersectionArraysTest {

    private FindIntersectionArrays finder;

    @BeforeEach
    void setUp() {
        finder = new FindIntersectionArrays();
    }

    // --- Источник данных для тестов ---
    // Формат: arr1, arr2, expectedIntersectionSet
    static Stream<Arguments> provideArraysForIntersection() {
        return Stream.of(
                // Примеры из описания
                Arguments.of(new int[]{1, 2, 2, 1}, new int[]{2, 2}, Set.of(2)),
                Arguments.of(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4}, Set.of(4, 9)),
                // Нет пересечения
                Arguments.of(new int[]{1, 2, 3}, new int[]{4, 5, 6}, Collections.emptySet()),
                // Пустые массивы
                Arguments.of(new int[]{}, new int[]{1, 2}, Collections.emptySet()),
                Arguments.of(new int[]{1, 2}, new int[]{}, Collections.emptySet()),
                Arguments.of(new int[]{}, new int[]{}, Collections.emptySet()),
                // Null массивы
                Arguments.of(null, new int[]{1, 2}, Collections.emptySet()),
                Arguments.of(new int[]{1, 2}, null, Collections.emptySet()),
                Arguments.of(null, null, Collections.emptySet()),
                // Частичное пересечение
                Arguments.of(new int[]{1, 2, 3, 4}, new int[]{2, 4, 6, 8}, Set.of(2, 4)),
                // С нулем и отрицательными
                Arguments.of(new int[]{-1, 0, 1}, new int[]{0, 1, 2, -1}, Set.of(-1, 0, 1)),
                // Дубликаты
                Arguments.of(new int[]{5, 5, 5}, new int[]{5, 0}, Set.of(5)),
                // Большие массивы (для проверки оптимизации, если возможно)
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7}, new int[]{0, 2, 4, 6, 8}, Set.of(2, 4, 6)),
                Arguments.of(new int[]{0, 2, 4, 6, 8}, new int[]{1, 2, 3, 4, 5, 6, 7}, Set.of(2, 4, 6)) // Меняем местами для оптимизации
        );
    }

    // --- Тесты для findIntersection (итеративный с Set) ---
    @Nested
    @DisplayName("Метод findIntersection (итеративный)")
    class IterativeIntersectionTests {

        @ParameterizedTest(name = "arr1={0}, arr2={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindIntersectionArraysTest#provideArraysForIntersection")
        void shouldFindIntersectionCorrectly(int[] arr1, int[] arr2, Set<Integer> expectedIntersection) {
            Set<Integer> actualIntersection = finder.findIntersection(arr1, arr2);
            assertEquals(expectedIntersection, actualIntersection);
        }
    }

    // --- Тесты для findIntersectionStream (Stream API) ---
    @Nested
    @DisplayName("Метод findIntersectionStream (Stream API)")
    class StreamIntersectionTests {

        @ParameterizedTest(name = "arr1={0}, arr2={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindIntersectionArraysTest#provideArraysForIntersection")
        void shouldFindIntersectionCorrectlyUsingStream(int[] arr1, int[] arr2, Set<Integer> expectedIntersection) {
            Set<Integer> actualIntersection = finder.findIntersectionStream(arr1, arr2);
            assertEquals(expectedIntersection, actualIntersection);
        }
    }
}

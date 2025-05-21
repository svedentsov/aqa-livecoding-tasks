package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для KClosestPointsOrigin")
class KClosestPointsOriginTest {

    private KClosestPointsOrigin kClosest;

    @BeforeEach
    void setUp() {
        kClosest = new KClosestPointsOrigin();
    }

    // Хелпер для сравнения массивов точек без учета порядка
    private void assertPointArraysEqualUnordered(int[][] expected, int[][] actual, String message) {
        assertEquals(expected.length, actual.length, message + " - количество точек не совпадает");

        // Преобразуем точки в Set строк "x,y" для сравнения без учета порядка
        // Альтернатива: Set<List<Integer>> или сортировка обоих массивов по каноническому ключу.
        Set<String> expectedSet = Arrays.stream(expected)
                .map(p -> p[0] + "," + p[1])
                .collect(Collectors.toSet());
        Set<String> actualSet = Arrays.stream(actual)
                .map(p -> p[0] + "," + p[1])
                .collect(Collectors.toSet());

        assertEquals(expectedSet, actualSet, message + " - наборы точек не совпадают");
    }

    // Хелпер для сравнения массивов точек С УЧЕТОМ ПОРЯДКА (для метода сортировки)
    private void assertPointArraysEqualOrdered(int[][] expected, int[][] actual, String message) {
        assertEquals(expected.length, actual.length, message + " - количество точек не совпадает");
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], message + " - точка на индексе " + i + " не совпадает");
        }
    }

    static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of(new int[][]{{1, 3}, {-2, 2}}, 1, new int[][]{{-2, 2}}, "Example 1 (k=1)"),
                Arguments.of(new int[][]{{3, 3}, {5, -1}, {-2, 4}}, 2, new int[][]{{3, 3}, {-2, 4}}, "Example 2 (k=2)"), // Ожидаемый для сортировки, для кучи порядок может быть другим
                Arguments.of(new int[][]{{0, 1}, {1, 0}}, 2, new int[][]{{0, 1}, {1, 0}}, "Two points same distance (k=2)"),
                Arguments.of(new int[][]{{1, 1}, {2, 2}, {3, 3}}, 1, new int[][]{{1, 1}}, "Simple case (k=1)"),
                Arguments.of(new int[][]{{1, 1}, {2, 2}, {3, 3}}, 3, new int[][]{{1, 1}, {2, 2}, {3, 3}}, "k equals n"),
                Arguments.of(new int[][]{{1, 1}, {2, 2}, {3, 3}}, 5, new int[][]{{1, 1}, {2, 2}, {3, 3}}, "k greater than n (returns all valid)"),
                Arguments.of(new int[][]{}, 1, new int[0][0], "Empty points array"),
                Arguments.of(new int[][]{{1, 1}}, 0, new int[0][0], "k=0"),
                Arguments.of(null, 2, new int[0][0], "Null points array"),
                Arguments.of(new int[][]{{1, 1}, null, {2, 2}, null, {3, 3}}, 1, new int[][]{{1, 1}}, "k=1, some nulls"),
                Arguments.of(new int[][]{{1, 1}, null, {2, 2}, null, {3, 3}}, 5, new int[][]{{1, 1}, {2, 2}, {3, 3}}, "k>valid points, some nulls"),
                Arguments.of(new int[][]{{1, 1}, {2, 2}, {0, 0}}, 1, new int[][]{{0, 0}}, "Origin point included")
        );
    }

    @Nested
    @DisplayName("Метод kClosestHeap")
    class KClosestHeapTests {
        @ParameterizedTest(name = "{3}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.KClosestPointsOriginTest#provideTestData")
        void testKClosestHeap(int[][] points, int k, int[][] expectedRaw, String description) {
            int[][] actual = kClosest.kClosestHeap(points, k);

            // Ожидаемый результат должен быть отсортирован по расстоянию для сравнения с результатом Heap
            // (если мы хотим сравнивать наборы точек без учета порядка, который возвращает heap)
            // Но так как задача говорит "порядок не важен", используем assertPointArraysEqualUnordered
            // Ожидаемый `expectedRaw` должен содержать правильный набор точек, но их порядок может отличаться.
            assertPointArraysEqualUnordered(expectedRaw, actual, "Heap method: " + description);
        }
    }

    @Nested
    @DisplayName("Метод kClosestSorting")
    class KClosestSortingTests {
        @ParameterizedTest(name = "{3}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.KClosestPointsOriginTest#provideTestData")
        void testKClosestSorting(int[][] points, int k, int[][] expectedRaw, String description) {
            int[][] actual = kClosest.kClosestSorting(points, k);

            // Для метода kClosestSorting результат уже отсортирован по расстоянию.
            // Поэтому expectedRaw должен быть также отсортирован по расстоянию
            // (и по x, y для стабильности, если расстояния одинаковы).
            // Создадим ожидаемый результат, который будет точно отсортирован так, как это делает kClosestSorting.
            int[][] expectedSorted;
            if (points == null || points.length == 0 || k <= 0) {
                expectedSorted = new int[0][0];
            } else {
                int[][] validExpectedPoints = Arrays.stream(expectedRaw)
                        .filter(p -> p != null && p.length == 2)
                        .toArray(int[][]::new);
                Arrays.sort(validExpectedPoints, Comparator.comparingInt(p -> p[0] * p[0] + p[1] * p[1]));
                int finalK = Math.min(k, validExpectedPoints.length);
                if (finalK == 0) expectedSorted = new int[0][0];
                else expectedSorted = Arrays.copyOfRange(validExpectedPoints, 0, finalK);
            }
            assertPointArraysEqualOrdered(expectedSorted, actual, "Sorting method: " + description);
        }
    }
}

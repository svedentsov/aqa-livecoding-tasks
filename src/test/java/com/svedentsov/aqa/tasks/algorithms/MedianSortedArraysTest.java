package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для MedianSortedArrays")
class MedianSortedArraysTest {

    private MedianSortedArrays medianFinder;
    private static final double DELTA = 0.00001; // Дельта для сравнения double

    @BeforeEach
    void setUp() {
        medianFinder = new MedianSortedArrays();
    }

    static Stream<Arguments> provideSortedArraysAndMedians() {
        return Stream.of(
                Arguments.of(new int[]{1, 3}, new int[]{2}, 2.0),
                Arguments.of(new int[]{1, 2}, new int[]{3, 4}, 2.5),
                Arguments.of(new int[]{0, 0}, new int[]{0, 0}, 0.0),
                Arguments.of(new int[]{}, new int[]{1}, 1.0),      // Первый пустой
                Arguments.of(new int[]{1}, new int[]{}, 1.0),      // Второй пустой (будет обмен)
                Arguments.of(new int[]{2}, new int[]{}, 2.0),
                Arguments.of(new int[]{1, 3, 5, 7}, new int[]{2, 4, 6, 8}, 4.5), // Чередующиеся
                Arguments.of(new int[]{100000}, new int[]{100001}, 100000.5), // Большие числа
                Arguments.of(new int[]{1, 2, 3}, new int[]{4, 5, 6}, 3.5),     // Разделенные
                Arguments.of(new int[]{4, 5, 6}, new int[]{1, 2, 3}, 3.5),     // Разделенные, обратный порядок
                // Дополнительные тесты
                Arguments.of(new int[]{1, 2, 3, 4}, new int[]{5, 6, 7, 8}, 4.5),
                Arguments.of(new int[]{1}, new int[]{2, 3, 4, 5}, 3.0),
                Arguments.of(new int[]{1, 5}, new int[]{2, 3, 4}, 3.0),
                Arguments.of(new int[]{1, 2, 3, 4, 5}, new int[]{6}, 3.5), // нечет/нечет -> чет
                Arguments.of(new int[]{1, 2, 3, 4}, new int[]{5}, 3.0),   // чет/нечет -> нечет
                Arguments.of(new int[]{Integer.MIN_VALUE, 0}, new int[]{Integer.MAX_VALUE}, 0.0),
                Arguments.of(new int[]{1, 2, 2, 2, 8}, new int[]{2, 2, 6, 7, 9}, 2.0) // С дубликатами
        );
    }

    // --- Параметризованный тест для findMedianSortedArrays ---
    @ParameterizedTest(name = "nums1={0}, nums2={1} -> Ожидаемая медиана: {2}")
    @MethodSource("provideSortedArraysAndMedians")
    @DisplayName("Должен находить корректную медиану для двух отсортированных массивов")
    void shouldFindMedianOfSortedArrays(int[] nums1, int[] nums2, double expectedMedian) {
        assertEquals(expectedMedian, medianFinder.findMedianSortedArrays(nums1, nums2), DELTA);
    }

    // --- Тесты для случаев с null ---
    @Test
    @DisplayName("Первый массив null, второй не пустой")
    void firstArrayNull_shouldBehaveAsEmpty() {
        assertEquals(2.5, medianFinder.findMedianSortedArrays(null, new int[]{2, 3}), DELTA);
        assertEquals(1.0, medianFinder.findMedianSortedArrays(null, new int[]{1}), DELTA);
    }

    @Test
    @DisplayName("Второй массив null, первый не пустой")
    void secondArrayNull_shouldBehaveAsEmpty() {
        assertEquals(2.5, medianFinder.findMedianSortedArrays(new int[]{2, 3}, null), DELTA);
        assertEquals(1.0, medianFinder.findMedianSortedArrays(new int[]{1}, null), DELTA);
    }


    // --- Тесты для случаев, выбрасывающих исключение ---
    @Test
    @DisplayName("Должен выбросить IllegalArgumentException для двух пустых массивов")
    void shouldThrowIllegalArgumentExceptionForTwoEmptyArrays() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medianFinder.findMedianSortedArrays(new int[]{}, new int[]{});
        });
        assertTrue(exception.getMessage().contains("Cannot find median of two empty arrays"));
    }

    @Test
    @DisplayName("Должен выбросить IllegalArgumentException для двух null массивов")
    void shouldThrowIllegalArgumentExceptionForTwoNullArrays() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            medianFinder.findMedianSortedArrays(null, null);
        });
        assertTrue(exception.getMessage().contains("Cannot find median of two empty arrays"));
    }
}

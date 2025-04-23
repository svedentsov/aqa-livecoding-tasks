package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для SumArrayElements")
class SumArrayElementsTest {

    private SumArrayElements summer;

    @BeforeEach
    void setUp() {
        summer = new SumArrayElements();
    }

    static Stream<Arguments> provideArraysForSummation() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 15L),
                Arguments.of(new int[]{}, 0L),
                Arguments.of(new int[]{-1, 0, 1}, 0L),
                Arguments.of(new int[]{-1, -2, -3}, -6L),
                Arguments.of(new int[]{Integer.MAX_VALUE, 1}, 2147483648L), // Проверка переполнения int
                Arguments.of(new int[]{Integer.MIN_VALUE, -1}, -2147483649L), // Проверка отрицательного переполнения
                Arguments.of(new int[]{10}, 10L)
        );
    }

    static Stream<Arguments> provideListsForSummation() {
        return Stream.of(
                Arguments.of(Arrays.asList(10, 20, null, 30, -5, null), 55L),
                Arguments.of(Collections.emptyList(), 0L), // Пустой список
                Arguments.of(Arrays.asList(Integer.MAX_VALUE, 10), 2147483657L),
                Arguments.of(Arrays.asList(5, 5, 5), 15L),
                Arguments.of(Arrays.asList(null, null), 0L), // Только null
                Arguments.of(Arrays.asList(1, 2, 3), 6L) // Простой список
        );
    }

    @Nested
    @DisplayName("Тесты для метода sumElements (массив int[])")
    class SumArrayTest {

        @ParameterizedTest(name = "Массив: {0}, Ожидаемая сумма: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.SumArrayElementsTest#provideArraysForSummation")
        void shouldCalculateSumForArrayCorrectly(int[] numbers, long expectedSum) {
            assertEquals(expectedSum, summer.sumElements(numbers));
        }

        @Test
        @DisplayName("Должен вернуть 0 для null массива")
        void shouldReturnZeroForNullArray() {
            assertEquals(0L, summer.sumElements(null), "Сумма для null массива должна быть 0L");
        }
    }

    @Nested
    @DisplayName("Тесты для суммирования списка List<Integer>")
    class SumListTest {

        @Nested
        @DisplayName("Метод sumElementsListIterative")
        class IterativeListSum {
            @ParameterizedTest(name = "Список: {0}, Ожидаемая сумма: {1}")
            @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.SumArrayElementsTest#provideListsForSummation")
            void shouldCalculateSumForListIteratively(List<Integer> numbers, long expectedSum) {
                assertEquals(expectedSum, summer.sumElementsListIterative(numbers));
            }

            @Test
            @DisplayName("Должен вернуть 0 для null списка")
            void shouldReturnZeroForNullList() {
                assertEquals(0L, summer.sumElementsListIterative(null), "Итеративная сумма для null списка должна быть 0L");
            }
        }

        @Nested
        @DisplayName("Метод sumElementsListStream")
        class StreamListSum {
            @ParameterizedTest(name = "Список: {0}, Ожидаемая сумма: {1}")
            @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.SumArrayElementsTest#provideListsForSummation")
            void shouldCalculateSumForListUsingStream(List<Integer> numbers, long expectedSum) {
                assertEquals(expectedSum, summer.sumElementsListStream(numbers));
            }

            @Test
            @DisplayName("Должен вернуть 0 для null списка")
            void shouldReturnZeroForNullList() {
                assertEquals(0L, summer.sumElementsListStream(null), "Stream сумма для null списка должна быть 0L");
            }
        }
    }
}

package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тесты для FindMaxMinArray")
class FindMaxMinArrayTest {

    private FindMaxMinArray finder;

    @BeforeEach
    void setUp() {
        finder = new FindMaxMinArray();
    }

    static Stream<Arguments> provideArraysForMaxTest() {
        return Stream.of(
                Arguments.of(new int[]{1, 5, 2, 9, 3}, 9),
                Arguments.of(new int[]{-1, -5, -2, -9, -3}, -1),
                Arguments.of(new int[]{5}, 5),
                Arguments.of(new int[]{0, -10, 5, 0, -2, 3}, 5),
                Arguments.of(new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE}, Integer.MAX_VALUE),
                Arguments.of(new int[]{5, 5, 5, 5}, 5),
                Arguments.of(new int[]{-10, -20, -5}, -5)
        );
    }

    static Stream<Arguments> provideArraysForMinTest() {
        return Stream.of(
                Arguments.of(new int[]{1, 5, 2, 9, 3}, 1),
                Arguments.of(new int[]{-1, -5, -2, -9, -3}, -9),
                Arguments.of(new int[]{5}, 5),
                Arguments.of(new int[]{0, -10, 5, 0, -2, 3}, -10),
                Arguments.of(new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE}, Integer.MIN_VALUE),
                Arguments.of(new int[]{5, 5, 5, 5}, 5),
                Arguments.of(new int[]{10, 20, 5}, 5)
        );
    }

    @Nested
    @DisplayName("Тесты для метода findMax")
    class FindMaxTests {

        @ParameterizedTest(name = "Массив: {0}, Ожидаемый Max: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindMaxMinArrayTest#provideArraysForMaxTest")
        void shouldFindCorrectMaximum(int[] numbers, int expectedMax) {
            assertEquals(expectedMax, finder.findMax(numbers));
        }

        @Test
        @DisplayName("Должен выбросить исключение для пустого массива")
        void shouldThrowExceptionForEmptyArray() {
            int[] emptyArray = {};
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                finder.findMax(emptyArray);
            }, "Должно быть выброшено IllegalArgumentException для пустого массива");
            // Дополнительно можно проверить сообщение исключения
            assertEquals("Input array cannot be null or empty.", exception.getMessage());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Должен выбросить исключение для null массива")
        void shouldThrowExceptionForNullArray(int[] numbers) { // numbers будет null
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                finder.findMax(numbers);
            }, "Должно быть выброшено IllegalArgumentException для null массива");
            assertEquals("Input array cannot be null or empty.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Тесты для метода findMin")
    class FindMinTests {

        @ParameterizedTest(name = "Массив: {0}, Ожидаемый Min: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindMaxMinArrayTest#provideArraysForMinTest")
        void shouldFindCorrectMinimum(int[] numbers, int expectedMin) {
            assertEquals(expectedMin, finder.findMin(numbers));
        }

        @Test
        @DisplayName("Должен выбросить исключение для пустого массива")
        void shouldThrowExceptionForEmptyArray() {
            int[] emptyArray = {};
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                finder.findMin(emptyArray);
            }, "Должно быть выброшено IllegalArgumentException для пустого массива");
            assertEquals("Input array cannot be null or empty.", exception.getMessage());
        }

        @ParameterizedTest
        @NullSource
        @DisplayName("Должен выбросить исключение для null массива")
        void shouldThrowExceptionForNullArray(int[] numbers) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                finder.findMin(numbers);
            }, "Должно быть выброшено IllegalArgumentException для null массива");
            assertEquals("Input array cannot be null or empty.", exception.getMessage());
        }
    }
}

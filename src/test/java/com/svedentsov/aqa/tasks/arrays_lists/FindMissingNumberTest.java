package com.svedentsov.aqa.tasks.arrays_lists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для FindMissingNumber")
class FindMissingNumberTest {

    private FindMissingNumber finder;

    @BeforeEach
    void setUp() {
        finder = new FindMissingNumber();
    }

    // --- Источник данных для валидных тестов ---
    // Формат: массив int[], n, ожидаемое отсутствующее число
    static Stream<Arguments> provideValidTestData() {
        return Stream.of(
                Arguments.of(new int[]{1, 2, 4, 5}, 5, 3), // Стандартный случай
                Arguments.of(new int[]{3, 1, 2, 5}, 5, 4), // Другой порядок
                Arguments.of(new int[]{1}, 2, 2),         // Малый n, пропущено n
                Arguments.of(new int[]{}, 1, 1),          // Малый n, пропущено 1
                Arguments.of(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10}, 10, 1), // Пропущено 1
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 10, 10),// Пропущено n
                Arguments.of(new int[]{1, 3, 4, 5, 6, 2, 8, 7, 10}, 10, 9)  // Большой n, другой порядок
        );
    }

    // --- Тесты для findMissingNumberSum ---
    @Nested
    @DisplayName("Метод findMissingNumberSum")
    class SumMethodTests {

        @ParameterizedTest(name = "Массив: {0}, n={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindMissingNumberTest#provideValidTestData")
        void shouldFindMissingNumberUsingSum(int[] numbers, int n, int expectedMissing) {
            assertEquals(expectedMissing, finder.findMissingNumberSum(numbers, n));
        }

        @Test
        @DisplayName("Должен выбросить исключение для null массива")
        void shouldThrowExceptionForNullArray() {
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberSum(null, 5));
        }

        @Test
        @DisplayName("Должен выбросить исключение для n < 1")
        void shouldThrowExceptionForNLessThan1() {
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberSum(new int[]{}, 0));
        }

        @Test
        @DisplayName("Должен выбросить исключение при неверной длине массива (не n-1)")
        void shouldThrowExceptionForIncorrectArrayLength() {
            // Длина 3, n=5 (должно быть n-1 = 4)
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberSum(new int[]{1, 2, 3}, 5));
            // Длина 3, n=3 (должно быть n-1 = 2) - случай, когда нет пропущенного числа
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberSum(new int[]{1, 2, 3}, 3));
        }
    }

    // --- Тесты для findMissingNumberXor ---
    @Nested
    @DisplayName("Метод findMissingNumberXor")
    class XorMethodTests {

        @ParameterizedTest(name = "Массив: {0}, n={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindMissingNumberTest#provideValidTestData")
        void shouldFindMissingNumberUsingXor(int[] numbers, int n, int expectedMissing) {
            assertEquals(expectedMissing, finder.findMissingNumberXor(numbers, n));
        }

        @Test
        @DisplayName("Должен выбросить исключение для null массива")
        void shouldThrowExceptionForNullArray() {
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberXor(null, 5));
        }

        @Test
        @DisplayName("Должен выбросить исключение для n < 1")
        void shouldThrowExceptionForNLessThan1() {
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberXor(new int[]{}, 0));
        }

        // XOR метод не проверяет длину и наличие дубликатов, поэтому тестов на эти исключения нет
        // Он просто вернет результат XOR операций
    }

    // --- Тесты для findMissingNumberSet ---
    @Nested
    @DisplayName("Метод findMissingNumberSet")
    class SetMethodTests {

        @ParameterizedTest(name = "Массив: {0}, n={1} -> Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.arrays_lists.FindMissingNumberTest#provideValidTestData")
        void shouldFindMissingNumberUsingSet(int[] numbers, int n, int expectedMissing) {
            assertEquals(expectedMissing, finder.findMissingNumberSet(numbers, n));
        }

        @Test
        @DisplayName("Должен выбросить исключение для null массива")
        void shouldThrowExceptionForNullArray() {
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberSet(null, 5));
        }

        @Test
        @DisplayName("Должен выбросить исключение для n < 1")
        void shouldThrowExceptionForNLessThan1() {
            assertThrows(IllegalArgumentException.class, () -> finder.findMissingNumberSet(new int[]{}, 0));
        }

        @Test
        @DisplayName("Должен выбросить исключение, если пропущенное число не найдено")
        void shouldThrowExceptionIfNoNumberIsMissing() {
            // Массив содержит все числа от 1 до n
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> finder.findMissingNumberSet(new int[]{1, 2, 3}, 3));
            assertTrue(exception.getMessage().contains("No missing number found"));
        }
    }
}

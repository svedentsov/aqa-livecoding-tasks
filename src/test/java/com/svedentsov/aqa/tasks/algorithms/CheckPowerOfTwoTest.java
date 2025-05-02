package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для CheckPowerOfTwo")
class CheckPowerOfTwoTest {

    private CheckPowerOfTwo checker;

    @BeforeEach
    void setUp() {
        checker = new CheckPowerOfTwo();
    }

    // --- Тесты для isPowerOfTwoBitwise ---
    @Nested
    @DisplayName("Метод isPowerOfTwoBitwise")
    class BitwiseCheckTests {

        @ParameterizedTest(name = "Число {0} ЯВЛЯЕТСЯ степенью двойки")
        @ValueSource(ints = {1, 2, 4, 8, 16, 32, 1024, 1 << 30 /* 2^30 */})
        @DisplayName("Должен возвращать true для степеней двойки")
        void shouldReturnTrueForPowersOfTwo(int number) {
            assertTrue(checker.isPowerOfTwoBitwise(number), number + " должно быть степенью двойки");
        }

        @ParameterizedTest(name = "Число {0} НЕ является степенью двойки")
        @ValueSource(ints = {
                0, -1, -2, -4, -16, // Ноль и отрицательные
                3, 5, 6, 7, 9, 10, 12, 15, 17, // Положительные не степени двойки
                31, 33, 1023, 1025,
                Integer.MAX_VALUE, Integer.MIN_VALUE
        })
        @DisplayName("Должен возвращать false для не-степеней двойки (включая 0 и отрицательные)")
        void shouldReturnFalseForNonPowersOfTwo(int number) {
            assertFalse(checker.isPowerOfTwoBitwise(number), number + " не должно быть степенью двойки");
        }
    }

    // --- Тесты для isPowerOfTwoIterative ---
    @Nested
    @DisplayName("Метод isPowerOfTwoIterative")
    class IterativeCheckTests {

        @ParameterizedTest(name = "Число {0} ЯВЛЯЕТСЯ степенью двойки")
        @ValueSource(ints = {1, 2, 4, 8, 16, 32, 1024, 1 << 30 /* 2^30 */})
        @DisplayName("Должен возвращать true для степеней двойки")
        void shouldReturnTrueForPowersOfTwo(int number) {
            assertTrue(checker.isPowerOfTwoIterative(number), number + " должно быть степенью двойки");
        }

        @ParameterizedTest(name = "Число {0} НЕ является степенью двойки")
        @ValueSource(ints = {
                0, -1, -2, -4, -16, // Ноль и отрицательные
                3, 5, 6, 7, 9, 10, 12, 15, 17, // Положительные не степени двойки
                31, 33, 1023, 1025,
                Integer.MAX_VALUE, Integer.MIN_VALUE
        })
        @DisplayName("Должен возвращать false для не-степеней двойки (включая 0 и отрицательные)")
        void shouldReturnFalseForNonPowersOfTwo(int number) {
            assertFalse(checker.isPowerOfTwoIterative(number), number + " не должно быть степенью двойки");
        }
    }
}

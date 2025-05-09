package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для PerfectNumberCheck")
class PerfectNumberCheckTest {

    private PerfectNumberCheck checker;

    @BeforeEach
    void setUp() {
        checker = new PerfectNumberCheck();
    }

    // --- Тесты для идеальных чисел ---
    @ParameterizedTest(name = "Число {0} ЯВЛЯЕТСЯ идеальным")
    @ValueSource(ints = {
            6,
            28,
            496,
            8128
            // 33550336 // Следующее идеальное число, можно добавить, если тест не будет слишком долгим
    })
    @DisplayName("Должен возвращать true для идеальных чисел")
    void shouldReturnTrueForPerfectNumbers(int number) {
        assertTrue(checker.isPerfectNumber(number), number + " должно быть идеальным числом");
    }

    // Тест для следующего большого идеального числа с таймаутом
    // Если тест выполняется дольше указанного времени, он будет прерван.
    @Test
    @DisplayName("Проверка большого идеального числа (33550336) с таймаутом")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    // Таймаут в 2 секунды
    void shouldReturnTrueForLargePerfectNumber() {
        assertTrue(checker.isPerfectNumber(33550336), "33550336 должно быть идеальным числом");
    }


    // --- Тесты для неидеальных чисел (включая 0, 1 и отрицательные) ---
    @ParameterizedTest(name = "Число {0} НЕ является идеальным")
    @ValueSource(ints = {
            1, 2, 3, 4, 5, 7, 8, 9, 10, // Маленькие неидеальные
            12, 20, 100, 495, 8127, // Другие неидеальные
            0,                            // Ноль
            -1, -6, -28                   // Отрицательные
    })
    @DisplayName("Должен возвращать false для неидеальных чисел")
    void shouldReturnFalseForNonPerfectNumbers(int number) {
        assertFalse(checker.isPerfectNumber(number), number + " не должно быть идеальным числом");
    }
}

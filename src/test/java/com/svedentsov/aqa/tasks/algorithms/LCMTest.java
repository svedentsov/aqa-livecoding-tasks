package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для LCM (Наименьшее Общее Кратное)")
class LCMTest {

    private LCM lcmCalculator;

    @BeforeEach
    void setUp() {
        lcmCalculator = new LCM();
    }

    // --- Параметризованный тест для lcm ---
    @ParameterizedTest(name = "НОК({0}, {1}) должен быть {2}")
    @CsvSource({
            // Основные случаи
            "4, 6, 12",
            "6, 4, 12",  // Порядок не важен
            "5, 7, 35",  // Взаимно простые
            "7, 5, 35",
            "12, 18, 36",
            "21, 49, 147",
            "100, 75, 300",
            // Случаи с нулем
            "0, 5, 0",
            "5, 0, 0",
            "0, 0, 0",
            // Случаи с единицей
            "1, 1, 1",
            "1, 8, 8",
            "8, 1, 8",
            // Случаи с одинаковыми числами
            "7, 7, 7",
            // Случаи с отрицательными числами
            "-4, 6, 12",
            "4, -6, 12",
            "-4, -6, 12",
            // Случаи, где |a*b| может переполнить int
            "50000, 60000, 300000",
            "46341, 46341, 46341", // ~sqrt(Integer.MAX_VALUE)
            // Случаи с Integer.MAX_VALUE (2147483647 - простое число)
            "2147483647, 1, 2147483647",
            "2147483647, 2, 4294967294" // 2147483647L * 2L
    })
    @DisplayName("Проверка НОК для различных пар чисел")
    void shouldCalculateLcmCorrectly(int a, int b, long expectedLcm) {
        assertEquals(expectedLcm, lcmCalculator.lcm(a, b));
    }
}

package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для SumOfDigits")
class SumOfDigitsTest {

    private SumOfDigits summer;

    @BeforeEach
    void setUp() {
        summer = new SumOfDigits();
    }

    // --- Параметризованный тест для sumDigits ---
    @ParameterizedTest(name = "Сумма цифр числа {0} должна быть {1}")
    @CsvSource({
            "123, 6",
            "49, 13",
            "5, 5",
            "0, 0",
            "-123, 6", // Отрицательное число
            "999, 27",
            "100, 1",
            "8, 8",
            "-5, 5", // Отрицательное однозначное
            "2147483647, 46", // Integer.MAX_VALUE
            "-2147483648, 47" // Integer.MIN_VALUE -> abs(long) = 2147483648 -> 2+1+4+7+4+8+3+6+4+8 = 47
    })
    @DisplayName("Должен корректно вычислять сумму цифр для разных чисел")
    void shouldCalculateSumOfDigitsCorrectly(int number, int expectedSum) {
        assertEquals(expectedSum, summer.sumDigits(number));
    }
}

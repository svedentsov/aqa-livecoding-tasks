package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для IntegerToRoman")
class IntegerToRomanTest {

    private IntegerToRoman converter;

    @BeforeEach
    void setUp() {
        converter = new IntegerToRoman();
    }

    // --- Тесты для корректных чисел в диапазоне [1, 3999] ---
    @ParameterizedTest(name = "Число: {0} -> Римское: \"{1}\"")
    @CsvSource({
            "3, III",
            "58, LVIII",
            "1994, MCMXCIV",
            "1, I",
            "4, IV",
            "9, IX",
            "40, XL",
            "49, XLIX",
            "50, L",
            "90, XC",
            "100, C",
            "400, CD",
            "500, D",
            "888, DCCCLXXXVIII",
            "900, CM",
            "1000, M",
            "1515, MDXV",
            "2025, MMXXV",
            "3999, MMMCMXCIX"  // Максимальное значение
    })
    @DisplayName("Должен корректно конвертировать числа от 1 до 3999 в римские")
    void shouldConvertValidIntegersToRoman(int number, String expectedRoman) {
        assertEquals(expectedRoman, converter.intToRoman(number));
    }

    // --- Тесты для некорректных входов (вне диапазона [1, 3999]) ---
    @ParameterizedTest(name = "Невалидное число: {0} -> IllegalArgumentException")
    @ValueSource(ints = {0, -1, -10, 4000, 5000})
    @DisplayName("Должен выбросить IllegalArgumentException для чисел вне диапазона [1, 3999]")
    void shouldThrowIllegalArgumentExceptionForOutOfRangeNumbers(int invalidNumber) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.intToRoman(invalidNumber);
        });
        assertTrue(exception.getMessage().contains("must be between 1 and 3999"),
                "Сообщение об ошибке должно указывать на неверный диапазон.");
        assertTrue(exception.getMessage().contains(String.valueOf(invalidNumber)),
                "Сообщение об ошибке должно содержать переданное невалидное число.");
    }
}

package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для RomanToInteger")
class RomanToIntegerTest {

    private RomanToInteger converter;

    @BeforeEach
    void setUp() {
        converter = new RomanToInteger();
    }

    // --- Тесты для корректных римских чисел ---
    @ParameterizedTest(name = "Римское: \"{0}\" -> Целое: {1}")
    @CsvSource({
            "III, 3",
            "LVIII, 58",
            "MCMXCIV, 1994",
            "IX, 9",
            "IV, 4",
            "XL, 40",
            "XC, 90",
            "CD, 400",
            "CM, 900",
            "I, 1",
            "V, 5",
            "X, 10",
            "L, 50",
            "C, 100",
            "D, 500",
            "M, 1000",
            "MMXXV, 2025",// 2000 + 20 + 5
            "MMMDCCCLXXXVIII, 3888", // 3000 + 800 + 80 + 8
            // Неканонические, но обрабатываемые текущим алгоритмом
            "IIII, 4", // Алгоритм обработает как 1+1+1+1
            "XXXX, 40", // Алгоритм обработает как 10+10+10+10
            "VV, 10", // Алгоритм обработает как 5+5
            "VX, 5", // Алгоритм обработает как X - V = 10 - 5 = 5
            "LC, 50", // Алгоритм обработает как C - L = 100 - 50 = 50
            "DM, 500" // Алгоритм обработает как M - D = 1000 - 500 = 500
    })
    @DisplayName("Должен корректно конвертировать валидные и некоторые неканонические римские числа")
    void shouldConvertValidRomanNumerals(String roman, int expectedInt) {
        assertEquals(expectedInt, converter.romanToInt(roman));
    }

    // --- Тесты для пустой строки ---
    @Test
    @DisplayName("Должен возвращать 0 для пустой строки")
    void shouldReturnZeroForEmptyString() {
        assertEquals(0, converter.romanToInt(""));
    }

    // --- Тесты для некорректных входов ---
    @Test
    @DisplayName("Должен выбросить NullPointerException для null входа")
    void shouldThrowNullPointerExceptionForNullInput() {
        assertThrows(NullPointerException.class, () -> {
            converter.romanToInt(null);
        });
    }

    @ParameterizedTest(name = "Невалидный символ в: \"{0}\" -> IllegalArgumentException")
    @ValueSource(strings = {
            "MCMA",    // 'A' невалидный
            "IIIX A",  // пробел и 'A' невалидные
            "X J",     // 'J' невалидный
            "test",    // все невалидные
            "IVXLCDM_OTHER" // _OTHER невалидные
    })
    @DisplayName("Должен выбросить IllegalArgumentException для строк с невалидными символами")
    void shouldThrowIllegalArgumentExceptionForInvalidCharacters(String invalidRoman) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            converter.romanToInt(invalidRoman);
        });
        assertTrue(exception.getMessage().contains("Invalid character"), "Сообщение об ошибке должно указывать на невалидный символ");
    }
}

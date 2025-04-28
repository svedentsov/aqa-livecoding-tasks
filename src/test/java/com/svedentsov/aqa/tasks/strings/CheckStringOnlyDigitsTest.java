package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для CheckStringOnlyDigits")
class CheckStringOnlyDigitsTest {

    private CheckStringOnlyDigits checker;

    @BeforeEach
    void setUp() {
        checker = new CheckStringOnlyDigits();
    }

    // --- Источник данных: строки, содержащие ТОЛЬКО цифры (ASCII '0'-'9') ---
    static Stream<String> provideDigitOnlyStrings() {
        return Stream.of(
                "12345",
                "0",
                "9876543210",
                "1"
        );
    }

    // --- Источник данных: строки, содержащие НЕ ТОЛЬКО цифры или невалидные ---
    static Stream<String> provideNonDigitOnlyStrings() {
        return Stream.of(
                "12a45",      // с буквой
                "12 45",      // с пробелом
                "-123",       // с минусом
                "+123",       // с плюсом
                " 123 ",      // с ведущими/завершающими пробелами
                " ",          // только пробел
                "1.23",       // с точкой
                "١٢٣",        // Unicode цифры (не должны проходить)
                "abc",        // только буквы
                "!@#$"        // только символы
        );
    }

    // --- Тесты для containsOnlyDigitsManual ---
    @Nested
    @DisplayName("Метод containsOnlyDigitsManual")
    class ManualCheckTests {

        @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: true")
        @MethodSource("com.svedentsov.aqa.tasks.strings.CheckStringOnlyDigitsTest#provideDigitOnlyStrings")
        @DisplayName("Должен возвращать true для строк только с ASCII цифрами")
        void shouldReturnTrueForDigitOnlyStrings(String input) {
            assertTrue(checker.containsOnlyDigitsManual(input));
        }

        @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: false")
        @MethodSource("com.svedentsov.aqa.tasks.strings.CheckStringOnlyDigitsTest#provideNonDigitOnlyStrings")
        @DisplayName("Должен возвращать false для строк с не-цифровыми символами")
        void shouldReturnFalseForNonDigitStrings(String input) {
            assertFalse(checker.containsOnlyDigitsManual(input));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: false")
        @NullAndEmptySource // Проверяет null и ""
        @DisplayName("Должен возвращать false для null или пустой строки")
        void shouldReturnFalseForNullOrEmpty(String input) {
            assertFalse(checker.containsOnlyDigitsManual(input));
        }
    }

    // --- Тесты для containsOnlyDigitsRegex ---
    @Nested
    @DisplayName("Метод containsOnlyDigitsRegex")
    class RegexCheckTests {

        @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: true")
        @MethodSource("com.svedentsov.aqa.tasks.strings.CheckStringOnlyDigitsTest#provideDigitOnlyStrings")
        @DisplayName("Должен возвращать true для строк только с ASCII цифрами")
        void shouldReturnTrueForDigitOnlyStrings(String input) {
            assertTrue(checker.containsOnlyDigitsRegex(input));
        }

        @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: false")
        @MethodSource("com.svedentsov.aqa.tasks.strings.CheckStringOnlyDigitsTest#provideNonDigitOnlyStrings")
        @DisplayName("Должен возвращать false для строк с не-цифровыми символами")
        void shouldReturnFalseForNonDigitStrings(String input) {
            assertFalse(checker.containsOnlyDigitsRegex(input));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: false")
        @NullAndEmptySource // Проверяет null и ""
        @DisplayName("Должен возвращать false для null или пустой строки")
        void shouldReturnFalseForNullOrEmpty(String input) {
            assertFalse(checker.containsOnlyDigitsRegex(input));
        }
    }
}

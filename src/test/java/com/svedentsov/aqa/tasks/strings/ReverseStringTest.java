package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тесты для ReverseString")
class ReverseStringTest {

    private ReverseString reverser;

    @BeforeEach
    void setUp() {
        reverser = new ReverseString();
    }

    // Используем вложенные классы для группировки тестов по методам
    @Nested
    @DisplayName("Тесты для метода reverseString (StringBuilder)")
    class ReverseStringBuilderTest {

        @ParameterizedTest(name = "Вход: \"{0}\", Ожидание: \"{1}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.ReverseStringTest#provideStringsForReversal")
        void shouldReverseNonEmptyStringsCorrectly(String input, String expected) {
            assertEquals(expected, reverser.reverseString(input));
        }

        @Test
        @DisplayName("Должен вернуть null для null входа")
        void shouldReturnNullForNullInput() {
            assertNull(reverser.reverseString(null), "Для null входа ожидается null");
        }

        @Test
        @DisplayName("Должен вернуть пустую строку для пустой строки")
        void shouldReturnEmptyStringForEmptyInput() {
            assertEquals("", reverser.reverseString(""), "Для пустой строки ожидается пустая строка");
        }

        // Дополнительный тест с NullAndEmptySource для краткости
        @ParameterizedTest(name = "Вход: {0}")
        @NullAndEmptySource
        @DisplayName("Тест с null и пустой строкой")
        void testNullAndEmpty(String input) {
            if (input == null) {
                assertNull(reverser.reverseString(input));
            } else {
                assertEquals("", reverser.reverseString(input));
            }
        }
    }

    @Nested
    @DisplayName("Тесты для метода reverseStringManual (char array)")
    class ReverseStringManualTest {

        @ParameterizedTest(name = "Вход: \"{0}\", Ожидание: \"{1}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.ReverseStringTest#provideStringsForReversal")
        void shouldReverseNonEmptyStringsCorrectly(String input, String expected) {
            assertEquals(expected, reverser.reverseStringManual(input));
        }

        @Test
        @DisplayName("Должен вернуть null для null входа")
        void shouldReturnNullForNullInput() {
            assertNull(reverser.reverseStringManual(null), "Для null входа ожидается null");
        }

        @Test
        @DisplayName("Должен вернуть пустую строку для пустой строки")
        void shouldReturnEmptyStringForEmptyInput() {
            assertEquals("", reverser.reverseStringManual(""), "Для пустой строки ожидается пустая строка");
        }

        // Дополнительный тест с NullAndEmptySource для краткости
        @ParameterizedTest(name = "Вход: {0}")
        @NullAndEmptySource
        @DisplayName("Тест с null и пустой строкой")
        void testNullAndEmpty(String input) {
            if (input == null) {
                assertNull(reverser.reverseStringManual(input));
            } else {
                assertEquals("", reverser.reverseStringManual(input));
            }
        }
    }

    // Статический метод-источник данных для параметризованных тестов
    // Должен быть доступен из вложенных классов, поэтому public static или размещен вне Nested классов
    static Stream<Arguments> provideStringsForReversal() {
        return Stream.of(
                Arguments.of("hello", "olleh"),
                Arguments.of("Java", "avaJ"),
                Arguments.of("a", "a"),
                Arguments.of("madam", "madam"), // Палиндром
                Arguments.of(" ", " "), // Строка из пробела
                Arguments.of("  leading and trailing spaces  ", "  secaps gniliart dna gnidael  "),
                Arguments.of("12345!@#$", "$#@!54321"),
                Arguments.of("Programming", "gnimmargorP")
        );
    }
}

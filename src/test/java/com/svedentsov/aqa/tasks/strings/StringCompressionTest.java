package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тесты для StringCompression")
class StringCompressionTest {

    private StringCompression compressor;

    @BeforeEach
    void setUp() {
        compressor = new StringCompression();
    }

    // --- Источник данных для тестов ---
    // Формат: входная строка, ожидаемая строка
    static Stream<Arguments> provideStringsForCompression() {
        return Stream.of(
                // Случаи, когда сжатие эффективно
                Arguments.of("aabcccccaaa", "a2b1c5a3"),
                Arguments.of("abbbbbbbbbbbb", "a1b12"), // 1 a, 12 b
                Arguments.of("aaaaabbbbbc", "a5b5c1"),
                Arguments.of("WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWB", "W12B1W12B3W24B1"), // Длинный пример
                // Случаи, когда сжатая строка не короче
                Arguments.of("abc", "abc"),          // a1b1c1 длиннее
                Arguments.of("aabbcc", "aabbcc"),     // a2b2c2 та же длина
                Arguments.of("aaAAaa", "aaAAaa"),     // a2A2a2 та же длина (регистр важен)
                Arguments.of("ababab", "ababab"),    // a1b1a1b1a1b1 длиннее
                // Короткие строки (<=2)
                Arguments.of("aa", "aa"),
                Arguments.of("a", "a"),
                Arguments.of("ab", "ab"),
                // Пустая строка
                Arguments.of("", "")

        );
    }

    // --- Параметризованный тест для разных строк ---
    @ParameterizedTest(name = "Вход: \"{0}\" -> Ожидание: \"{1}\"")
    @MethodSource("provideStringsForCompression")
    @DisplayName("Должен сжимать строку или возвращать исходную, если сжатая не короче")
    void shouldCompressOrReturnOriginalString(String input, String expected) {
        assertEquals(expected, compressor.compressString(input));
    }

    // --- Тест для null входа ---
    @Test
    @DisplayName("Должен возвращать null для null входа")
    void shouldReturnNullForNullInput() {
        assertNull(compressor.compressString(null), "Для null входа ожидается null");
    }

    // --- Дополнительный тест для пустой строки (уже покрыт MethodSource, но можно явно) ---
    @Test
    @DisplayName("Должен возвращать пустую строку для пустой строки")
    void shouldReturnEmptyStringForEmptyInput() {
        assertEquals("", compressor.compressString(""));
    }
}

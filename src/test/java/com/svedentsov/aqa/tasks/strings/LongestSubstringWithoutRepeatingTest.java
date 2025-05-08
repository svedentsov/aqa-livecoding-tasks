package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для LongestSubstringWithoutRepeating")
class LongestSubstringWithoutRepeatingTest {

    private LongestSubstringWithoutRepeating solver;

    @BeforeEach
    void setUp() {
        solver = new LongestSubstringWithoutRepeating();
    }

    // --- Источник данных для тестов ---
    // Формат: входная строка, ожидаемая длина
    static Stream<Arguments> provideStringsAndLengths() {
        return Stream.of(
                Arguments.of("abcabcbb", 3),
                Arguments.of("bbbbb", 1),
                Arguments.of("pwwkew", 3),
                Arguments.of(" ", 1),         // Пробел
                Arguments.of("au", 2),         // Два разных
                Arguments.of("dvdf", 3),       // "vdf"
                Arguments.of("tmmzuxt", 5),    // "mzuxt"
                Arguments.of("aab", 2),        // "ab"
                Arguments.of("abcde", 5),      // Все уникальные
                Arguments.of("abcdefa", 6),    // Повтор первого в конце
                Arguments.of("ohomm", 3)       // "hom"
        );
    }

    // --- Тесты для lengthOfLongestSubstringSet ---
    @Nested
    @DisplayName("Метод lengthOfLongestSubstringSet (с HashSet)")
    class SetMethodTests {

        @ParameterizedTest(name = "Строка: \"{0}\" -> Длина: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.strings.LongestSubstringWithoutRepeatingTest#provideStringsAndLengths")
        @DisplayName("Должен находить правильную длину для различных строк")
        void shouldFindLengthCorrectlyUsingSet(String input, int expectedLength) {
            assertEquals(expectedLength, solver.lengthOfLongestSubstringSet(input));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: 0")
        @NullAndEmptySource // Проверяет null и ""
        @DisplayName("Должен возвращать 0 для null или пустой строки")
        void shouldReturnZeroForNullOrEmpty(String input) {
            assertEquals(0, solver.lengthOfLongestSubstringSet(input));
        }
    }

    // --- Тесты для lengthOfLongestSubstringMap ---
    @Nested
    @DisplayName("Метод lengthOfLongestSubstringMap (с HashMap)")
    class MapMethodTests {

        @ParameterizedTest(name = "Строка: \"{0}\" -> Длина: {1}")
        @MethodSource("com.svedentsov.aqa.tasks.strings.LongestSubstringWithoutRepeatingTest#provideStringsAndLengths")
        @DisplayName("Должен находить правильную длину для различных строк")
        void shouldFindLengthCorrectlyUsingMap(String input, int expectedLength) {
            assertEquals(expectedLength, solver.lengthOfLongestSubstringMap(input));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: 0")
        @NullAndEmptySource // Проверяет null и ""
        @DisplayName("Должен возвращать 0 для null или пустой строки")
        void shouldReturnZeroForNullOrEmpty(String input) {
            assertEquals(0, solver.lengthOfLongestSubstringMap(input));
        }
    }
}

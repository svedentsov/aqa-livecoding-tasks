package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для IsSubsequence")
class IsSubsequenceTest {

    private IsSubsequence checker;

    @BeforeEach
    void setUp() {
        checker = new IsSubsequence();
    }

    // --- Источник данных для тестов ---
    // Формат: s, t, expectedResult (boolean)
    static Stream<Arguments> provideSubsequenceCases() {
        return Stream.of(
                Arguments.of("ace", "abcde", true),
                Arguments.of("axc", "ahbgdc", false),
                Arguments.of("", "abcde", true), // Пустая s
                Arguments.of("abc", "", false), // Пустая t (s не пустая)
                Arguments.of("b", "abc", true),
                Arguments.of("aaaaaa", "bbaaaa", false), // s (len=6) vs t (len=6, 4 'a's)
                Arguments.of("abc", "abc", true), // Равные строки
                Arguments.of("z", "abc", false),
                Arguments.of("", "", true), // Обе пустые
                Arguments.of("too long", "short", false), // s длиннее t
                // Дополнительные случаи
                Arguments.of("abc", "ab", false), // t короче s (s не пустая)
                Arguments.of("apple", "apricotple", true),
                Arguments.of("leetc", "leetcode", true),
                Arguments.of("sing", "string", true),
                Arguments.of("sgn", "string", false), // Нарушен порядок символов s в t
                Arguments.of("aaa", "ababa", true)
        );
    }

    // --- Источник данных для тестов с null ---
    static Stream<Arguments> provideNullCases() {
        return Stream.of(
                Arguments.of(null, "abc", false),
                Arguments.of("abc", null, false),
                Arguments.of(null, null, false)
        );
    }

    // --- Параметризованный тест для isSubsequence ---
    @ParameterizedTest(name = "s=\"{0}\", t=\"{1}\" -> Ожидание: {2}")
    @MethodSource("provideSubsequenceCases")
    @DisplayName("Должен корректно проверять подпоследовательность для различных строк")
    void shouldCheckSubsequenceCorrectly(String s, String t, boolean expectedResult) {
        if (expectedResult) {
            assertTrue(checker.isSubsequence(s, t),
                    String.format("Ожидалось, что '%s' является подпоследовательностью '%s'", s, t));
        } else {
            assertFalse(checker.isSubsequence(s, t),
                    String.format("Ожидалось, что '%s' НЕ является подпоследовательностью '%s'", s, t));
        }
    }

    @ParameterizedTest(name = "s={0}, t={1} -> Ожидание: false (null-обработка)")
    @MethodSource("provideNullCases")
    @DisplayName("Должен возвращать false, если хотя бы одна из строк null")
    void shouldReturnFalseIfAnyStringIsNull(String s, String t, boolean ignoredExpectedResult) {
        // ignoredExpectedResult здесь не используется, т.к. всегда ожидаем false
        assertFalse(checker.isSubsequence(s, t),
                String.format("Ожидался false для s=%s, t=%s из-за null", s, t));
    }
}

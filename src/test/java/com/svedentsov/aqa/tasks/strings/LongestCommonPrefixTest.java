package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для LongestCommonPrefix")
class LongestCommonPrefixTest {

    private LongestCommonPrefix prefixFinder;

    @BeforeEach
    void setUp() {
        prefixFinder = new LongestCommonPrefix();
    }

    // --- Источник данных для тестов ---
    // Формат: массив строк, ожидаемый префикс
    static Stream<Arguments> provideStringArraysAndPrefixes() {
        return Stream.of(
                Arguments.of(new String[]{"flower", "flow", "flight"}, "fl"),
                Arguments.of(new String[]{"dog", "racecar", "car"}, ""),
                Arguments.of(new String[]{"interspecies", "interstellar", "interstate"}, "inters"),
                Arguments.of(new String[]{"", "b"}, ""), // Пустая строка в массиве
                Arguments.of(new String[]{"a"}, "a"), // Один элемент
                Arguments.of(new String[]{"abc", null, "abd"}, ""), // Null в массиве
                Arguments.of(new String[]{null, "abc", "abd"}, ""), // Null как первый элемент
                Arguments.of(new String[]{"apple", "apply", "apricot"}, "ap"),
                Arguments.of(new String[]{"", ""}, ""), // Две пустые строки
                Arguments.of(new String[]{"same", "same"}, "same"),
                Arguments.of(new String[]{"prefix", "pref", "pre"}, "pre"),
                Arguments.of(new String[]{"aa", "a"}, "a"), // Первый длиннее второго
                Arguments.of(new String[]{"c", "acc", "ccc"}, ""), // Нет общего префикса, хотя 'c' есть во всех
                Arguments.of(new String[]{"abab", "ab", "abc"}, "ab"),
                // Граничные случаи
                Arguments.of(null, ""), // Null массив
                Arguments.of(new String[]{}, "") // Пустой массив
        );
    }

    // --- Тесты для longestCommonPrefixHorizontal ---
    @Nested
    @DisplayName("Метод longestCommonPrefixHorizontal")
    class HorizontalScanTests {

        @ParameterizedTest(name = "Массив: {0} -> Ожидание: \"{1}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.LongestCommonPrefixTest#provideStringArraysAndPrefixes")
        @DisplayName("Должен находить LCP горизонтальным сканированием")
        void shouldFindLcpUsingHorizontalScan(String[] strs, String expectedPrefix) {
            assertEquals(expectedPrefix, prefixFinder.longestCommonPrefixHorizontal(strs));
        }
    }

    // --- Тесты для longestCommonPrefixVertical ---
    @Nested
    @DisplayName("Метод longestCommonPrefixVertical")
    class VerticalScanTests {

        @ParameterizedTest(name = "Массив: {0} -> Ожидание: \"{1}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.LongestCommonPrefixTest#provideStringArraysAndPrefixes")
        @DisplayName("Должен находить LCP вертикальным сканированием")
        void shouldFindLcpUsingVerticalScan(String[] strs, String expectedPrefix) {
            assertEquals(expectedPrefix, prefixFinder.longestCommonPrefixVertical(strs));
        }
    }
}

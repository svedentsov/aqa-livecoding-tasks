package com.svedentsov.aqa.tasks.maps_sets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для CountCharOccurrences")
class CountCharOccurrencesTest {

    private CountCharOccurrences counter;

    @BeforeEach
    void setUp() {
        counter = new CountCharOccurrences();
    }

    private static Map<Character, Integer> expectedIterative(String key) {
        Map<String, Map<Character, Integer>> expectations = new TreeMap<>();

        expectations.put("hello world", new TreeMap<>(Map.ofEntries(
                entry(' ', 1), entry('d', 1), entry('e', 1), entry('h', 1),
                entry('l', 3), entry('o', 2), entry('r', 1), entry('w', 1)
        )));
        expectations.put("Programming Java", new TreeMap<>(Map.ofEntries(
                entry(' ', 1), entry('J', 1), entry('P', 1), entry('a', 3),
                entry('g', 2), entry('i', 1), entry('m', 2), entry('n', 1),
                entry('o', 1), entry('r', 2), entry('v', 1)
        )));
        expectations.put("a", new TreeMap<>(Map.of('a', 1)));
        expectations.put("aaaaa", new TreeMap<>(Map.of('a', 5)));
        expectations.put("!@#$ %^& *()", new TreeMap<>(Map.ofEntries(
                entry(' ', 2), entry('!', 1), entry('#', 1), entry('$', 1),
                entry('%', 1), entry('&', 1), entry('(', 1), entry(')', 1),
                entry('*', 1), entry('@', 1), entry('^', 1)
        )));
        expectations.put("你好 世界", new TreeMap<>(Map.ofEntries(
                entry(' ', 1), entry('世', 1), entry('界', 1),
                entry('你', 1), entry('好', 1)
        )));

        return expectations.getOrDefault(key, Collections.emptyMap());
    }

    private static Map<Character, Long> expectedStream(String key) {
        Map<String, Map<Character, Long>> expectations = new TreeMap<>();

        expectations.put("hello world", new TreeMap<>(Map.ofEntries(
                entry(' ', 1L), entry('d', 1L), entry('e', 1L), entry('h', 1L),
                entry('l', 3L), entry('o', 2L), entry('r', 1L), entry('w', 1L)
        )));
        expectations.put("Programming Java", new TreeMap<>(Map.ofEntries(
                entry(' ', 1L), entry('J', 1L), entry('P', 1L), entry('a', 3L),
                entry('g', 2L), entry('i', 1L), entry('m', 2L), entry('n', 1L),
                entry('o', 1L), entry('r', 2L), entry('v', 1L)
        )));
        expectations.put("a", new TreeMap<>(Map.of('a', 1L)));
        expectations.put("aaaaa", new TreeMap<>(Map.of('a', 5L)));
        expectations.put("!@#$ %^& *()", new TreeMap<>(Map.ofEntries(
                entry(' ', 2L), entry('!', 1L), entry('#', 1L), entry('$', 1L),
                entry('%', 1L), entry('&', 1L), entry('(', 1L), entry(')', 1L),
                entry('*', 1L), entry('@', 1L), entry('^', 1L)
        )));
        expectations.put("你好 世界", new TreeMap<>(Map.ofEntries(
                entry(' ', 1L), entry('世', 1L), entry('界', 1L),
                entry('你', 1L), entry('好', 1L)
        )));

        return expectations.getOrDefault(key, Collections.emptyMap());
    }


    static Stream<Arguments> provideStringsForCounting() {
        return Stream.of(
                Arguments.of("hello world"),
                Arguments.of("Programming Java"),
                Arguments.of("a"),
                Arguments.of("aaaaa"),
                Arguments.of("!@#$ %^& *()"),
                Arguments.of("你好 世界")
        );
    }


    @Nested
    @DisplayName("Тесты для countCharacters (Iterative, Integer)")
    class CountCharactersIterativeTest {

        @ParameterizedTest(name = "Вход: \"{0}\"")
        @MethodSource("com.svedentsov.aqa.tasks.maps_sets.CountCharOccurrencesTest#provideStringsForCounting")
        void shouldCountCharactersCorrectly(String input) {
            Map<Character, Integer> expected = expectedIterative(input);
            Map<Character, Integer> actual = counter.countCharacters(input);
            assertEquals(expected, actual, "Карта подсчета символов должна совпадать для строки: " + input);
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: пустая карта")
        @NullAndEmptySource
        void shouldReturnEmptyMapForNullOrEmptyInput(String input) {
            Map<Character, Integer> expected = new TreeMap<>(); // Ожидаем пустой TreeMap
            Map<Character, Integer> actual = counter.countCharacters(input);
            assertEquals(expected, actual, "Должна возвращаться пустая карта для null или пустой строки");
        }
    }

    @Nested
    @DisplayName("Тесты для countCharactersStream (Stream, Long)")
    class CountCharactersStreamTest {

        @ParameterizedTest(name = "Вход: \"{0}\"")
        @MethodSource("com.svedentsov.aqa.tasks.maps_sets.CountCharOccurrencesTest#provideStringsForCounting")
        void shouldCountCharactersCorrectlyUsingStream(String input) {
            Map<Character, Long> expected = expectedStream(input);
            Map<Character, Long> actual = counter.countCharactersStream(input);
            assertEquals(expected, actual, "Карта подсчета символов (Stream) должна совпадать для строки: " + input);
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: пустая карта")
        @NullAndEmptySource
            // Проверяет null и ""
        void shouldReturnEmptyMapForNullOrEmptyInputUsingStream(String input) {
            Map<Character, Long> expected = new TreeMap<>(); // Ожидаем пустой TreeMap
            Map<Character, Long> actual = counter.countCharactersStream(input);
            assertEquals(expected, actual, "Должна возвращаться пустая карта (Stream) для null или пустой строки");
        }
    }
}

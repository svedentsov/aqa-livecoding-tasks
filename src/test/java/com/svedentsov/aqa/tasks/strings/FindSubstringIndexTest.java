package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для FindSubstringIndex (аналог indexOf)")
class FindSubstringIndexTest {

    private FindSubstringIndex finder;

    @BeforeEach
    void setUp() {
        finder = new FindSubstringIndex();
    }

    // --- Источник данных для тестов ---
    // Формат: text, pattern, expectedIndex
    static Stream<Arguments> provideStringsAndPatterns() {
        return Stream.of(
                Arguments.of("hello world, welcome to the world!", "world", 6),
                Arguments.of("hello world", "o w", 4),
                Arguments.of("aaaaa", "bba", -1), // Не найден
                Arguments.of("abc", "", 0), // Пустой паттерн
                Arguments.of("", "", 0), // Оба пустые
                Arguments.of("abc", "abc", 0), // Полное совпадение
                Arguments.of("abc", "d", -1), // Не найден
                Arguments.of("mississippi", "issi", 1),
                Arguments.of("mississippi", "issip", 4),
                Arguments.of("abc", "abcd", -1), // Паттерн длиннее текста
                Arguments.of("", "a", -1), // Пустой текст, непустой паттерн
                Arguments.of("a", "a", 0), // Односимвольные
                Arguments.of("ababab", "aba", 0), // Первое вхождение
                Arguments.of("ababab", "bab", 1), // Первое вхождение
                Arguments.of("ababab", "ac", -1),
                // Null случаи
                Arguments.of(null, "a", -1),
                Arguments.of("a", null, -1),
                Arguments.of(null, null, -1),
                // Дополнительные
                Arguments.of("hello", "ll", 2),
                Arguments.of("hello", "lo", 3),
                Arguments.of("hello", "o", 4), // Последний символ
                Arguments.of("hello", "h", 0), // Первый символ
                Arguments.of("abcabc", "bca", 1)
        );
    }

    // --- Параметризованный тест для findSubstringIndex ---
    @ParameterizedTest(name = "Текст: \"{0}\", Паттерн: \"{1}\" -> Ожидаемый индекс: {2}")
    @MethodSource("provideStringsAndPatterns")
    @DisplayName("Должен находить индекс первого вхождения подстроки или возвращать -1")
    void shouldFindSubstringIndex(String text, String pattern, int expectedIndex) {
        assertEquals(expectedIndex, finder.findSubstringIndex(text, pattern));
    }
}

package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для ReverseWordsSentence")
class ReverseWordsSentenceTest {

    private ReverseWordsSentence reverser;

    @BeforeEach
    void setUp() {
        reverser = new ReverseWordsSentence();
    }

    // --- Источник данных для тестов ---
    // Формат: входная строка, ожидаемая строка
    static Stream<Arguments> provideSentencesForReversal() {
        return Stream.of(
                Arguments.of("the sky is blue", "blue is sky the"),
                Arguments.of("  hello world  ", "world hello"), // Внешние пробелы
                Arguments.of("a good   example", "example good a"), // Внутренние пробелы
                Arguments.of(" single ", "single"), // Одно слово
                Arguments.of("word, one.", "one. word,"), // С пунктуацией
                Arguments.of("1 two 3 four", "four 3 two 1"), // С цифрами
                Arguments.of("  leading", "leading"), // Только ведущие
                Arguments.of("trailing  ", "trailing"), // Только завершающие
                Arguments.of("tab\tseparated\nwords", "words separated tab") // Смешанные пробельные символы
        );
    }

    // --- Тесты для reverseWords (итеративный) ---
    @Nested
    @DisplayName("Метод reverseWords (итеративный)")
    class ReverseWordsIterativeTests {

        @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: \"{1}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.ReverseWordsSentenceTest#provideSentencesForReversal")
        @DisplayName("Должен переворачивать слова и убирать лишние пробелы")
        void shouldReverseWordsAndTrimSpaces(String input, String expected) {
            assertEquals(expected, reverser.reverseWords(input));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: \"\"")
        @NullAndEmptySource // Проверяет null и ""
        @ValueSource(strings = {" ", "   ", "\t \n"}) // Строки только из пробелов
        @DisplayName("Должен возвращать пустую строку для null, пустых и содержащих только пробелы строк")
        void shouldReturnEmptyStringForNullEmptyOrWhitespaceOnly(String input) {
            assertEquals("", reverser.reverseWords(input));
        }
    }

    // --- Тесты для reverseWordsStream (Stream API) ---
    @Nested
    @DisplayName("Метод reverseWordsStream (Stream API)")
    class ReverseWordsStreamTests {

        @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: \"{1}\"")
        @MethodSource("com.svedentsov.aqa.tasks.strings.ReverseWordsSentenceTest#provideSentencesForReversal")
        @DisplayName("Должен переворачивать слова и убирать лишние пробелы")
        void shouldReverseWordsAndTrimSpaces(String input, String expected) {
            assertEquals(expected, reverser.reverseWordsStream(input));
        }

        @ParameterizedTest(name = "Вход: {0} -> Ожидание: \"\"")
        @NullAndEmptySource // Проверяет null и ""
        @ValueSource(strings = {" ", "   ", "\t \n"}) // Строки только из пробелов
        @DisplayName("Должен возвращать пустую строку для null, пустых и содержащих только пробелы строк")
        void shouldReturnEmptyStringForNullEmptyOrWhitespaceOnly(String input) {
            assertEquals("", reverser.reverseWordsStream(input));
        }
    }
}

package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для CountWordsString")
class CountWordsStringTest {

    private CountWordsString counter;

    @BeforeEach
    void setUp() {
        counter = new CountWordsString();
    }

    // --- Источник данных для тестов ---
    static Stream<Arguments> provideSentencesAndCounts() {
        return Stream.of(
                Arguments.of("This is a sample sentence.", 5),
                Arguments.of(" Hello   world ", 2), // Ведущие, завершающие, множественные пробелы
                Arguments.of("OneWord", 1),
                Arguments.of("Word1, word2. Word3!", 3), // Знаки препинания прикреплены к словам
                Arguments.of("123 456 789", 3), // Числа как слова
                Arguments.of("Sentence with\ttab", 3), // Табуляция как разделитель
                Arguments.of("Sentence with\nnewline", 3), // Новая строка как разделитель
                Arguments.of("   leading space", 2),
                Arguments.of("trailing space   ", 2),
                Arguments.of("a b c d e", 5)
        );
    }

    // --- Параметризованный тест для разных предложений ---
    @ParameterizedTest(name = "Строка: \"{0}\", Ожидаемое кол-во слов: {1}")
    @MethodSource("provideSentencesAndCounts")
    @DisplayName("Должен корректно считать слова в различных предложениях")
    void shouldCountWordsCorrectly(String sentence, int expectedCount) {
        assertEquals(expectedCount, counter.countWords(sentence));
    }

    // --- Тесты для пустых, null и содержащих только пробелы строк ---
    @ParameterizedTest(name = "Вход: {0} -> Ожидание: 0 слов")
    @NullAndEmptySource // Проверяет null и ""
    @ValueSource(strings = {" ", "   ", "\t", "\n"}) // Дополнительно строки с разными пробелами
    @DisplayName("Должен возвращать 0 для null, пустых и содержащих только пробелы строк")
    void shouldReturnZeroForNullEmptyOrWhitespaceOnlyStrings(String sentence) {
        assertEquals(0, counter.countWords(sentence), "Для null/пустых/пробельных строк ожидается 0 слов");
    }
}

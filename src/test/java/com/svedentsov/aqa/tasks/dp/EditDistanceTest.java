package com.svedentsov.aqa.tasks.dp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для EditDistance (Levenshtein)")
class EditDistanceTest {

    private EditDistance distanceCalculator;

    @BeforeEach
    void setUp() {
        distanceCalculator = new EditDistance();
    }

    // --- Источник данных для тестов ---
    // Формат: word1, word2, expectedDistance
    static Stream<Arguments> provideWordsAndDistances() {
        return Stream.of(
                // Примеры из описания
                Arguments.of("horse", "ros", 3),
                Arguments.of("intention", "execution", 5),
                // Основные случаи
                Arguments.of("abc", "abc", 0),         // Идентичные
                Arguments.of("kitten", "sitting", 3),  // kitten -> sitten (s/k) -> sittin (i/e) -> sitting (g/ )
                Arguments.of("saturday", "sunday", 3), // sat -> sun (u/a), r -> '', day -> day
                Arguments.of("flaw", "lawn", 2),       // f->l, w->n (или insert/delete)
                // Случаи с пустыми строками
                Arguments.of("", "abc", 3),
                Arguments.of("abc", "", 3),
                Arguments.of("", "", 0),
                // Случаи с null (метод обрабатывает их как пустые строки)
                Arguments.of(null, "abc", 3),
                Arguments.of("abc", null, 3),
                Arguments.of(null, null, 0),
                // Другие случаи
                Arguments.of("apple", "apply", 1),    // Замена e на y
                Arguments.of("orange", "banana", 5),  // Много замен/вставок/удалений
                Arguments.of("book", "back", 2),
                Arguments.of("rosettacode", "rssettacde", 2) // 2 удаления 'o'
        );
    }

    // --- Параметризованный тест для minEditDistance ---
    @ParameterizedTest(name = "Distance(\"{0}\", \"{1}\") -> {2}")
    @MethodSource("provideWordsAndDistances")
    @DisplayName("Должен корректно вычислять редакторское расстояние")
    void shouldCalculateEditDistanceCorrectly(String word1, String word2, int expectedDistance) {
        assertEquals(expectedDistance, distanceCalculator.minEditDistance(word1, word2));
    }
}

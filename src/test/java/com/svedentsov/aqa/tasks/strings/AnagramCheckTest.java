package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для AnagramCheck")
class AnagramCheckTest {

    private AnagramCheck checker;

    @BeforeEach
    void setUp() {
        checker = new AnagramCheck();
    }

    static Stream<Arguments> provideAnagramTests() {
        return Stream.of(
                // Положительные случаи (являются анаграммами)
                Arguments.of("listen", "silent", true),
                Arguments.of("Dormitory", "dirty room", true),
                Arguments.of("a b", "b a", true),
                Arguments.of(null, null, true), // null == null
                Arguments.of("aabb", "bbaa", true),
                Arguments.of("Astronomer", "Moon starer", true),
                Arguments.of("School master", "The classroom", true),
                Arguments.of(" ", "   ", true), // Пустые после очистки
                Arguments.of("rail safety!", "fairy tales!", true),
                Arguments.of("", "", true), // Пустые строки
                Arguments.of("Conversation", "Voices rant on", true),
                Arguments.of("текст", "скетт", true), // Кириллица
                Arguments.of("текст с пробелами", "скетт ес илбаморп", true), // Кириллица с пробелами

                // Отрицательные случаи (не являются анаграммами)
                Arguments.of("hello", "world", false),
                Arguments.of("abc", "aabc", false), // Разная длина
                Arguments.of("abc", null, false),   // Один null
                Arguments.of(null, "abc", false),   // Другой null
                Arguments.of("rat", "car", false),
                Arguments.of("aaz", "zza", false),  // Разные символы/количество
                Arguments.of("123", "321", true),   // Становятся пустыми "" -> true // ИЗМЕНЕНО поведение cleanString
                Arguments.of("abc", "abd", false)
        );
    }

    @Nested
    @DisplayName("Тесты для метода areAnagramsSort")
    class AnagramSortTests {

        @ParameterizedTest(name = "Строка 1: \"{0}\", Строка 2: \"{1}\", Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.strings.AnagramCheckTest#provideAnagramTests")
        void testAreAnagramsSort(String str1, String str2, boolean expectedResult) {
            if (expectedResult) {
                assertTrue(checker.areAnagramsSort(str1, str2),
                        String.format("Sort: \"%s\" и \"%s\" должны быть анаграммами", str1, str2));
            } else {
                assertFalse(checker.areAnagramsSort(str1, str2),
                        String.format("Sort: \"%s\" и \"%s\" НЕ должны быть анаграммами", str1, str2));
            }
        }
    }

    @Nested
    @DisplayName("Тесты для метода areAnagramsMap")
    class AnagramMapTests {

        @ParameterizedTest(name = "Строка 1: \"{0}\", Строка 2: \"{1}\", Ожидание: {2}")
        @MethodSource("com.svedentsov.aqa.tasks.strings.AnagramCheckTest#provideAnagramTests")
        void testAreAnagramsMap(String str1, String str2, boolean expectedResult) {
            if (expectedResult) {
                assertTrue(checker.areAnagramsMap(str1, str2),
                        String.format("Map: \"%s\" и \"%s\" должны быть анаграммами", str1, str2));
            } else {
                assertFalse(checker.areAnagramsMap(str1, str2),
                        String.format("Map: \"%s\" и \"%s\" НЕ должны быть анаграммами", str1, str2));
            }
        }
    }
}

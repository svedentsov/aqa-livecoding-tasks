package com.svedentsov.aqa.tasks.maps_sets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тесты для FindFirstNonRepeatingChar")
class FindFirstNonRepeatingCharTest {

    private FindFirstNonRepeatingChar finder;

    @BeforeEach
    void setUp() {
        finder = new FindFirstNonRepeatingChar();
    }

    static Stream<Arguments> provideStringsAndResults() {
        return Stream.of(
                Arguments.of("swiss", 'w'),
                Arguments.of("aabbcc", null), // Все повторяются
                Arguments.of("stress", 't'),
                Arguments.of("aabbccddeeffG", 'G'), // Уникальный в конце
                Arguments.of("a", 'a'), // Один символ
                Arguments.of("Go Google", ' '), // Пробел - первый уникальный
                Arguments.of("loveleetcode", 'v'),
                Arguments.of("aabbccddee", null), // Нет уникальных
                Arguments.of("zabcdeabcde", 'z'), // Первый символ уникален
                Arguments.of("112233455", '4'), // Цифры
                Arguments.of("racecar", 'e'), // Палиндром с уникальным центром
                Arguments.of("teeter", 'r') // Еще пример
        );
    }

    @ParameterizedTest(name = "Вход: \"{0}\", Ожидание: {1}")
    @MethodSource("provideStringsAndResults")
    @DisplayName("Должен находить первый неповторяющийся символ или возвращать null")
    void shouldFindFirstNonRepeatingChar(String input, Character expected) {
        Character actual = finder.findFirstNonRepeatingChar(input);
        assertEquals(expected, actual, String.format("Для строки \"%s\" ожидался результат '%s'", input, expected));
    }

    @ParameterizedTest(name = "Вход: {0} -> Ожидание: null")
    @NullAndEmptySource // Проверяет null и ""
    @DisplayName("Должен возвращать null для null или пустой строки")
    void shouldReturnNullForNullOrEmptyInput(String input) {
        assertNull(finder.findFirstNonRepeatingChar(input),
                "Для null или пустой строки результат должен быть null");
    }

    @Test
    @DisplayName("Тест со строкой, где все символы повторяются, но разный регистр")
    void testAllRepeatingDifferentCase() {
        // Метод чувствителен к регистру, 'a' != 'A'
        assertEquals('a', finder.findFirstNonRepeatingChar("aAbBcC"));
    }
}

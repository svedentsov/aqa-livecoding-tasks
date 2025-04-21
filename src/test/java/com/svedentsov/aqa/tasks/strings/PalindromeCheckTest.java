package com.svedentsov.aqa.tasks.strings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для PalindromeCheck")
class PalindromeCheckTest {

    private PalindromeCheck checker;

    @BeforeEach
    void setUp() {
        checker = new PalindromeCheck();
    }

    // Параметризованный тест для строк, которые ДОЛЖНЫ быть палиндромами
    @ParameterizedTest(name = "Палиндром: \"{0}\" -> true")
    @ValueSource(strings = {
            "A man, a plan, a canal: Panama",
            " ", // Пустая строка после очистки
            ",.", // Пустая строка после очистки
            "level",
            "Noon",
            "Madam, I'm Adam.",
            "121",
            "Was it a car or a cat I saw?",
            "#(%)*", // Пустая строка после очистки
            "a",
            "aba",
            "racecar",
            "step on no pets"
    })
    @EmptySource // Также проверяем пустую строку ""
    @DisplayName("Должен возвращать true для палиндромов (игнорируя регистр/символы)")
    void shouldReturnTrueForPalindromes(String input) {
        assertTrue(checker.isPalindrome(input), "Строка \"" + input + "\" должна быть палиндромом");
    }

    // Параметризованный тест для строк, которые НЕ должны быть палиндромами
    @ParameterizedTest(name = "Не палиндром: \"{0}\" -> false")
    @ValueSource(strings = {
            "race a car",
            "1a2",
            "hello",
            "abc",
            "palindrome"
    })
    @DisplayName("Должен возвращать false для не-палиндромов")
    void shouldReturnFalseForNonPalindromes(String input) {
        assertFalse(checker.isPalindrome(input), "Строка \"" + input + "\" не должна быть палиндромом");
    }

    // Отдельный тест для null, так как он обрабатывается особо
    @Test
    @DisplayName("Должен возвращать false для null входа")
    void shouldReturnFalseForNullInput() {
        assertFalse(checker.isPalindrome(null), "Для null входа ожидается false");
    }

    // Альтернативный вариант с CsvSource для большей наглядности пар "вход-выход"
    @ParameterizedTest(name = "Вход: {0}, Ожидание: {1}")
    @CsvSource({
            "'A man, a plan, a canal: Panama', true",
            "'race a car',                   false",
            "' ',                            true",
            "',.',                           true",
            "null,                           false", // CsvSource не поддерживает null напрямую, используем отдельный тест
            "level,                          true",
            "'',                             true",
            "Noon,                           true",
            "'Madam, I''m Adam.',            true", // Экранирование апострофа в CsvSource
            "121,                            true",
            "1a2,                            false",
            "'Was it a car or a cat I saw?', true",
            "hello,                          false",
            "'#(%)*',                        true",
            "a,                              true",
            "aba,                            true",
            "abc,                            false"
    })
    @DisplayName("Тесты с CsvSource (кроме null)")
    void testVariousInputsWithCsvSource(String input, boolean expected) {
        // Обработка строки "null" из CsvSource, если бы мы ее использовали
        // if ("null".equalsIgnoreCase(input)) {
        //     assertFalse(checker.isPalindrome(null));
        // } else {
        assertEquals(expected, checker.isPalindrome(input));
        // }
    }

    // Тест для null с использованием @NullSource (более идиоматичный способ)
    @ParameterizedTest
    @NullSource
    @DisplayName("Тест для null с @NullSource")
    void testNullWithAnnotation(String input) {
        assertFalse(checker.isPalindrome(input));
    }
}

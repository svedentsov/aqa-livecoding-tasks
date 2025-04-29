package com.svedentsov.aqa.tasks.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для BalancedParenthesesCheck")
class BalancedParenthesesCheckTest {

    private BalancedParenthesesCheck checker;

    @BeforeEach
    void setUp() {
        checker = new BalancedParenthesesCheck();
    }

    // --- Источник данных: Сбалансированные строки ---
    static Stream<String> provideBalancedStrings() {
        return Stream.of(
                "({[]})",
                "()[]{}",
                "abc", // Без скобок
                "(abc)",
                "({[abc]})",
                "{{([][])}()}()", // Сложный случай
                "((((()))))", // Один тип
                "text ( before [ after ] ) text" // С текстом
        );
    }

    // --- Источник данных: Несбалансированные строки ---
    static Stream<String> provideUnbalancedStrings() {
        return Stream.of(
                "(]",      // Неправильный тип закрывающей
                "([)]",    // Неправильный порядок закрытия
                "{[}",     // Не закрыты
                "(",       // Не закрыта
                ")",       // Нет открывающей
                "(()",     // Не закрыта
                "))((",    // Неправильный порядок и тип
                "{[}]",    // Неправильный порядок закрытия
                ")))((("   // Неправильный порядок
        );
    }

    // --- Тесты для сбалансированных строк ---
    @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: true")
    @MethodSource("provideBalancedStrings")
    @DisplayName("Должен возвращать true для сбалансированных строк")
    void shouldReturnTrueForBalancedStrings(String expression) {
        assertTrue(checker.areBracketsBalanced(expression), "Строка должна быть сбалансированной: " + expression);
    }

    @ParameterizedTest(name = "Вход: {0} -> Ожидание: true")
    @NullAndEmptySource // Проверяет null и ""
    @DisplayName("Должен возвращать true для null или пустой строки")
    void shouldReturnTrueForNullOrEmpty(String expression) {
        assertTrue(checker.areBracketsBalanced(expression), "Null или пустая строка считаются сбалансированными");
    }

    // --- Тесты для несбалансированных строк ---
    @ParameterizedTest(name = "Строка: \"{0}\" -> Ожидание: false")
    @MethodSource("provideUnbalancedStrings")
    @DisplayName("Должен возвращать false для несбалансированных строк")
    void shouldReturnFalseForUnbalancedStrings(String expression) {
        assertFalse(checker.areBracketsBalanced(expression), "Строка должна быть несбалансированной: " + expression);
    }
}

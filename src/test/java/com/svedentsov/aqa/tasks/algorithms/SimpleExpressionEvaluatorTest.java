package com.svedentsov.aqa.tasks.algorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Тесты для SimpleExpressionEvaluator")
class SimpleExpressionEvaluatorTest {

    private SimpleExpressionEvaluator evaluator;

    @BeforeEach
    void setUp() {
        evaluator = new SimpleExpressionEvaluator();
    }

    static Stream<Arguments> validExpressionsProvider() {
        return Stream.of(
                Arguments.of("3 + 5 * 2", 13, "Сложение и умножение с приоритетом"),
                Arguments.of(" 10 - 4 / 2 ", 8, "Вычитание и деление с приоритетом и пробелами"),
                Arguments.of("2*3+5/6*3+15", 21, "Смешанные операции, целочисленное деление (5/6=0)"),
                Arguments.of("100 / 10 * 2", 20, "Деление и умножение (слева направо)"),
                Arguments.of("5", 5, "Одно число"),
                Arguments.of("0", 0, "Ноль"),
                Arguments.of("10 / 3", 3, "Целочисленное деление"),
                Arguments.of(" 2 * 2 * 2 ", 8, "Несколько умножений"),
                Arguments.of("10 - 2 + 3", 11, "Вычитание и сложение (слева направо)"),
                Arguments.of("100 / 2 / 5", 10, "Несколько делений"),
                Arguments.of("42", 42, "Одно большое число"),
                Arguments.of("1+2*3-4/2", 5, "Комбинация всех операций"),
                Arguments.of("0 * 10 + 5", 5, "Умножение на ноль в начале"),
                Arguments.of("10 + 0 * 5", 10, "Умножение на ноль в середине")
        );
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("validExpressionsProvider")
    @DisplayName("Корректные выражения")
    void evaluateExpression_validInputs(String expression, int expected, String description) {
        assertEquals(expected, evaluator.evaluateExpression(expression));
    }

    @Nested
    @DisplayName("Тесты на некорректные выражения и ошибки")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Null выражение должно выбрасывать IllegalArgumentException")
        void evaluateExpression_nullInput_throwsIllegalArgumentException() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                evaluator.evaluateExpression(null);
            });
            assertEquals("Expression cannot be null or empty.", exception.getMessage());
        }

        @ParameterizedTest(name = "Пустое или пробельное выражение \"{0}\"")
        @ValueSource(strings = {"", "   "})
        @DisplayName("Пустое или состоящее из пробелов выражение выбрасывает IllegalArgumentException")
        void evaluateExpression_emptyOrBlankInput_throwsIllegalArgumentException(String expression) {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                evaluator.evaluateExpression(expression);
            });
            assertEquals("Expression cannot be null or empty.", exception.getMessage());
        }

        @Test
        @DisplayName("Деление на ноль должно выбрасывать ArithmeticException")
        void evaluateExpression_divisionByZero_throwsArithmeticException() {
            Exception exception = assertThrows(ArithmeticException.class, () -> {
                evaluator.evaluateExpression("10 / 0");
            });
            assertEquals("Division by zero.", exception.getMessage());
        }

        @ParameterizedTest(name = "Невалидный символ: \"{0}\"")
        @ValueSource(strings = {"a + 5", "10 # 3", "5 @ 2"})
        @DisplayName("Невалидный символ в выражении выбрасывает IllegalArgumentException")
        void evaluateExpression_invalidCharacter_throwsIllegalArgumentException(String expression) {
            assertThrows(IllegalArgumentException.class, () -> {
                evaluator.evaluateExpression(expression);
            });
        }

        @ParameterizedTest(name = "Некорректный формат: \"{0}\"")
        @ValueSource(strings = {
                "3 +",          // Заканчивается оператором
                "++3",          // Начинается с двойного оператора
                "10 * * 5",     // Два оператора подряд
                "10 5 + 3",     // Пробел между числами без оператора
                "1 2 3",        // Только числа без операторов
                "* 5",          // Начинается с оператора (кроме унарного минуса, который не поддерживается)
                "(1+2)"         // Скобки не поддерживаются
        })
        @DisplayName("Некорректный формат выражения выбрасывает IllegalArgumentException")
        void evaluateExpression_invalidFormat_throwsIllegalArgumentException(String expression) {
            assertThrows(IllegalArgumentException.class, () -> {
                evaluator.evaluateExpression(expression);
            });
        }

        @Test
        @DisplayName("Переполнение при парсинге числа")
        void evaluateExpression_numberParsingOverflow_throwsArithmeticException() {
            assertThrows(ArithmeticException.class, () -> evaluator.evaluateExpression(String.valueOf(Integer.MAX_VALUE) + "0")); // "21474836470"
            assertThrows(ArithmeticException.class, () -> evaluator.evaluateExpression("1 + " + String.valueOf(Long.MAX_VALUE)));
        }

        @Test
        @DisplayName("Переполнение при арифметической операции (сложение)")
        void evaluateExpression_arithmeticOverflowAddition_throwsArithmeticException() {
            assertThrows(ArithmeticException.class, () -> evaluator.evaluateExpression(Integer.MAX_VALUE + " + 1"));
        }

        @Test
        @DisplayName("Переполнение при арифметической операции (умножение)")
        void evaluateExpression_arithmeticOverflowMultiplication_throwsArithmeticException() {
            assertThrows(ArithmeticException.class, () -> evaluator.evaluateExpression(Integer.MAX_VALUE / 2 + " * 3"));
        }

        @Test
        @DisplayName("Переполнение при арифметической операции (вычитание)")
        void evaluateExpression_arithmeticOverflowSubtraction_throwsArithmeticException() {
            assertThrows(ArithmeticException.class, () -> evaluator.evaluateExpression(Integer.MIN_VALUE + " - 1"));
            assertThrows(ArithmeticException.class, () -> evaluator.evaluateExpression("0 - " + Integer.MAX_VALUE + " - 2"));
        }
    }
}

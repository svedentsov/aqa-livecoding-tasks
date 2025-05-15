package com.svedentsov.aqa.tasks.algorithms;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Решение задачи №100: Вычисление простого математического выражения в строке.
 * Описание: Например, "2+3\*4". (Проверяет: Stack, парсинг строк, приоритет операций)
 * Задание: Напишите метод `int evaluateExpression(String expression)`, который
 * вычисляет значение простого арифметического выражения, заданного строкой.
 * Выражение содержит только неотрицательные целые числа, операторы `+`, `-`, `*`, `/`
 * и пробелы. Учитывайте стандартный приоритет операций (`*`, `/` перед `+`, `-`).
 * Целочисленное деление должно отбрасывать дробную часть.
 * Пример: `evaluateExpression("3 + 5 * 2")` -> `13`.
 * `evaluateExpression(" 10 - 4 / 2 ")` -> `8`.
 * `evaluateExpression("2*3+5/6*3+15")` -> `21`.
 */
public class SimpleExpressionEvaluator {

    /**
     * Вычисляет значение простого арифметического выражения из строки.
     * Поддерживает: неотрицательные целые числа, +, -, *, /, пробелы.
     * Приоритет: * / выше, чем + -. Выполнение слева направо для одного приоритета.
     * Использует два стека: для операндов и для операторов.
     * Не поддерживает скобки и унарный минус.
     *
     * @param expression Строка с арифметическим выражением.
     * @return Результат вычисления.
     * @throws IllegalArgumentException если выражение некорректно.
     * @throws ArithmeticException      при делении на ноль или переполнении числа.
     */
    public int evaluateExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty.");
        }

        Deque<Integer> operandStack = new ArrayDeque<>();
        Deque<Character> operatorStack = new ArrayDeque<>();
        int currentNumber = 0;
        boolean numberInProgress = false;

        // Проходим по выражению, включая виртуальный пробел в конце для обработки последнего числа/операции
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                // Строим число
                // Проверка на переполнение до умножения/сложения
                if (currentNumber > (Integer.MAX_VALUE - (c - '0')) / 10) {
                    throw new ArithmeticException("Number overflow parsing '" + expression + "'");
                }
                currentNumber = currentNumber * 10 + (c - '0');
                numberInProgress = true;
            } else {
                // Если до этого шло число, кладем его в стек
                if (numberInProgress) {
                    operandStack.push(currentNumber);
                    currentNumber = 0;
                    numberInProgress = false;
                }

                // Обрабатываем не-цифру (оператор или пробел)
                if (Character.isWhitespace(c)) {
                    continue; // Игнорируем пробелы
                } else if (isOperator(c)) {
                    // Пока оператор на вершине стека имеет больший или равный приоритет
                    while (!operatorStack.isEmpty() && hasPrecedence(operatorStack.peek(), c)) {
                        evaluateTop(operandStack, operatorStack);
                    }
                    // Кладем текущий оператор в стек
                    operatorStack.push(c);
                } else {
                    throw new IllegalArgumentException("Invalid character in expression: '" + c + "' at index " + i);
                }
            }
        }
        // После цикла добавляем последнее число, если оно было
        if (numberInProgress) {
            operandStack.push(currentNumber);
        }

        // Вычисляем оставшиеся операции в стеке
        while (!operatorStack.isEmpty()) {
            evaluateTop(operandStack, operatorStack);
        }

        // Результат - единственное число, оставшееся в стеке операндов
        if (operandStack.size() == 1) {
            return operandStack.pop();
        } else {
            // Если стек пуст или содержит > 1 элемента -> ошибка формата
            throw new IllegalArgumentException("Invalid expression format (final stack size: " + operandStack.size() + ")");
        }
    }

    /**
     * Точка входа для демонстрации вычисления выражений.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        SimpleExpressionEvaluator sol = new SimpleExpressionEvaluator();
        String[] expressions = {
                "3 + 5 * 2",         // 13
                " 10 - 4 / 2 ",      // 8
                "2*3+5/6*3+15",      // 6 + 0 * 3 + 15 = 21 (Целочисленное 5/6 = 0)
                "100 / 10 * 2",      // 10 * 2 = 20
                "5",                 // 5
                "10 / 3",            // 3
                " 2 * 2 * 2 ",       // 8
                "10 - 2 + 3",        // 8 + 3 = 11
                "100 / 2 / 5",       // 50 / 5 = 10
                "42",                // 42
                "1+2*3-4/2"          // 1+6-2 = 5
        };
        int[] expectedResults = {13, 8, 21, 20, 5, 3, 8, 11, 10, 42, 5};

        System.out.println("--- Evaluating Simple Arithmetic Expressions ---");
        for (int i = 0; i < expressions.length; i++) {
            runEvaluateTest(sol, expressions[i], expectedResults[i]);
        }

        System.out.println("\n--- Error Cases ---");
        String[] errorExpressions = {
                "3 +",          // Неожиданный конец
                "++3",          // Неподдерживаемый оператор
                "10 / 0",       // Деление на ноль
                "a + 5",        // Невалидный символ
                "10 * * 5",     // Два оператора подряд
                "10 5 + 3",     // Нет оператора между числами
                "1 2 3",        // Просто числа
                "",             // Пустая строка
                null            // Null строка
        };
        for (String expr : errorExpressions) {
            runEvaluateTest(sol, expr, Integer.MIN_VALUE); // Используем MIN_VALUE как маркер ожидания ошибки
        }
    }

    /**
     * Проверяет, является ли символ допустимым оператором.
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * Определяет приоритет оператора.
     *
     * @param op Оператор.
     * @return Уровень приоритета (1 для +/-, 2 для * /).
     */
    private int getPrecedence(char op) {
        if (op == '+' || op == '-') {
            return 1;
        }
        if (op == '*' || op == '/') {
            return 2;
        }
        return 0; // Для других символов (не должно происходить)
    }

    /**
     * Проверяет, имеет ли оператор {@code op1} (с вершины стека)
     * больший или равный приоритет, чем оператор {@code op2} (текущий).
     */
    private boolean hasPrecedence(char op1, char op2) {
        return getPrecedence(op1) >= getPrecedence(op2);
    }

    /**
     * Выполняет одну операцию, извлекая оператор и два операнда из стеков.
     *
     * @param operands  Стек операндов.
     * @param operators Стек операторов.
     */
    private void evaluateTop(Deque<Integer> operands, Deque<Character> operators) {
        if (operators.isEmpty()) throw new IllegalArgumentException("Operator stack is empty during evaluation.");
        if (operands.size() < 2)
            throw new IllegalArgumentException("Operand stack requires at least 2 elements for evaluation.");

        char op = operators.pop();
        int rightOperand = operands.pop();
        int leftOperand = operands.pop();
        int result;

        switch (op) {
            case '+':
                result = Math.addExact(leftOperand, rightOperand);
                break; // Используем addExact для проверки переполнения
            case '-':
                result = Math.subtractExact(leftOperand, rightOperand);
                break;
            case '*':
                result = Math.multiplyExact(leftOperand, rightOperand);
                break;
            case '/':
                if (rightOperand == 0) throw new ArithmeticException("Division by zero.");
                result = leftOperand / rightOperand; // Целочисленное деление
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
        operands.push(result); // Кладем результат обратно
    }

    /**
     * Вспомогательный метод для тестирования evaluateExpression.
     *
     * @param sol        Экземпляр решателя.
     * @param expression Выражение для вычисления.
     * @param expected   Ожидаемый результат (Integer.MIN_VALUE для ожидания ошибки).
     */
    private static void runEvaluateTest(SimpleExpressionEvaluator sol, String expression, int expected) {
        String input = (expression == null ? "null" : "'" + expression + "'");
        System.out.print("evaluateExpression(" + input + ") -> ");
        try {
            int result = sol.evaluateExpression(expression);
            boolean match = (expected != Integer.MIN_VALUE && result == expected);
            System.out.printf("%d (Expected: %s) %s%n",
                    result,
                    (expected == Integer.MIN_VALUE ? "Error" : expected),
                    (match ? "" : (expected == Integer.MIN_VALUE ? "<- ERROR NOT CAUGHT!" : "<- MISMATCH!")));
        } catch (IllegalArgumentException | ArithmeticException e) {
            if (expected == Integer.MIN_VALUE) { // Ожидали ошибку
                System.out.println("Caught expected error: " + e.getClass().getSimpleName());
            } else { // Не ожидали ошибку
                System.err.printf("Caught unexpected error: %s (Expected: %d)%n", e.getMessage(), expected);
            }
        } catch (Exception e) { // Ловим другие возможные ошибки
            System.err.printf("Caught unexpected error: %s%n", e);
        }
    }
}

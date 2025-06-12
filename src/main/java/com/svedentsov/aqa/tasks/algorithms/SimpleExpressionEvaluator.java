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
     * @throws IllegalArgumentException если выражение некорректно (формат, символы).
     * @throws ArithmeticException      при делении на ноль или переполнении числа/результата.
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
                    throw new ArithmeticException("Number overflow parsing input near: " + expression.substring(Math.max(0, i - 10), i + 1));
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
            throw new IllegalArgumentException("Invalid expression format: Final operand stack size is " + operandStack.size() +
                    ", expected 1. Operator stack empty: " + operatorStack.isEmpty());
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
        if (operators.isEmpty()) {
              throw new IllegalArgumentException("Operator stack empty during evaluation, but operation was triggered.");
        }
        if (operands.size() < 2) {
            throw new IllegalArgumentException("Operand stack requires at least 2 elements for evaluation. Found: " + operands.size() +
                    ". Operator: " + operators.peek());
        }

        char op = operators.pop();
        int rightOperand = operands.pop();
        int leftOperand = operands.pop();
        int result;

        try {
            result = switch (op) {
                case '+' -> Math.addExact(leftOperand, rightOperand);
                case '-' -> Math.subtractExact(leftOperand, rightOperand);
                case '*' -> Math.multiplyExact(leftOperand, rightOperand);
                case '/' -> {
                    if (rightOperand == 0) {
                        throw new ArithmeticException("Division by zero.");
                    }
                    yield leftOperand / rightOperand;
                }
                default ->
                    // This should not be reached if isOperator is comprehensive
                        throw new IllegalArgumentException("Unknown operator: " + op);
            };
        } catch (ArithmeticException e) {
            // Re-throw with more context if needed, or let it propagate
            // For example: throw new ArithmeticException("Arithmetic overflow/error during operation: " + leftOperand + " " + op + " " + rightOperand, e);
            throw e;
        }
        operands.push(result);
    }
}

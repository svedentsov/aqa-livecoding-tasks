package com.svedentsov.aqa.tasks.algorithms;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Решение задачи №100: Вычисление простого математического выражения в строке.
 */
public class Task100_SimpleExpressionEvaluator {

    /**
     * Вычисляет значение простого арифметического выражения, заданного строкой.
     * Поддерживает неотрицательные целые числа, операторы +, -, *, / и пробелы.
     * Учитывает стандартный приоритет операций (*, / выполняются раньше +, -).
     * Использует два стека: один для операндов (чисел) и один для операторов.
     * Не поддерживает скобки и унарный минус.
     *
     * @param expression Строка с арифметическим выражением.
     * @return Результат вычисления выражения.
     * @throws IllegalArgumentException если выражение null, пустое, содержит
     *                                  недопустимые символы или имеет неверный формат.
     * @throws ArithmeticException      при делении на ноль.
     */
    public int evaluateExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty.");
        }

        // Стеки для чисел и операторов
        Deque<Integer> operandStack = new ArrayDeque<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        int currentNumber = 0;
        boolean numberInProgress = false; // Флаг, что мы сейчас читаем число

        // Добавляем пробел в конец, чтобы обработать последнее число
        expression = expression + " ";

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Если символ - цифра, продолжаем строить число
            if (Character.isDigit(c)) {
                // Проверка на переполнение перед умножением и сложением
                if (currentNumber > Integer.MAX_VALUE / 10 ||
                        (currentNumber == Integer.MAX_VALUE / 10 && (c - '0') > Integer.MAX_VALUE % 10)) {
                    throw new ArithmeticException("Number overflow in expression.");
                }
                currentNumber = currentNumber * 10 + (c - '0');
                numberInProgress = true;
            } else {
                // Если до этого шло число, завершаем его и кладем в стек операндов
                if (numberInProgress) {
                    operandStack.push(currentNumber);
                    currentNumber = 0;
                    numberInProgress = false;
                }

                // Если символ - пробел, игнорируем его
                if (Character.isWhitespace(c)) {
                    continue;
                }

                // Если символ - оператор
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    // Пока на вершине стека операторов есть оператор с большим
                    // или равным приоритетом, чем текущий оператор 'c', вычисляем его.
                    while (!operatorStack.isEmpty() && hasPrecedence(operatorStack.peek(), c)) {
                        evaluateTop(operandStack, operatorStack);
                    }
                    // Помещаем текущий оператор в стек
                    operatorStack.push(c);
                } else {
                    // Если символ не цифра, не пробел и не оператор - ошибка формата
                    throw new IllegalArgumentException("Invalid character in expression: " + c);
                }
            }
        } // конец цикла for

        // После прохода по всей строке вычисляем оставшиеся операции в стеке
        while (!operatorStack.isEmpty()) {
            evaluateTop(operandStack, operatorStack);
        }

        // В конце в стеке операндов должен остаться ровно один элемент - результат
        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression format (stack size != 1).");
        }
        return operandStack.pop();
    }

    /**
     * Проверяет приоритет операторов.
     *
     * @param op1 Оператор с вершины стека.
     * @param op2 Текущий оператор из выражения.
     * @return true, если op1 имеет приоритет не ниже, чем op2.
     */
    private boolean hasPrecedence(char op1, char op2) {
        // Умножение и деление имеют более высокий приоритет, чем сложение и вычитание
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return true; // Выполняем * / перед + -
        }
        // Операторы одного уровня приоритета выполняются слева направо,
        // поэтому если на вершине оператор того же или более высокого уровня,
        // его нужно выполнить сейчас.
        if ((op1 == '*' || op1 == '/') && (op2 == '*' || op2 == '/')) {
            return true;
        }
        if ((op1 == '+' || op1 == '-') && (op2 == '+' || op2 == '-')) {
            return true;
        }
        // В остальных случаях (например, если текущий * / а на вершине + -),
        // приоритет текущего выше, и операцию со стека выполнять не нужно.
        return false;
    }

    /**
     * Выполняет операцию, используя два верхних операнда и верхний оператор из стеков.
     *
     * @param operands  Стек операндов (чисел).
     * @param operators Стек операторов.
     * @throws IllegalArgumentException если недостаточно операндов или операторов.
     * @throws ArithmeticException      при делении на ноль.
     */
    private void evaluateTop(Deque<Integer> operands, Deque<Character> operators) {
        if (operands.size() < 2 || operators.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression: Insufficient operands or operators for evaluation.");
        }
        // Порядок извлечения важен для вычитания и деления: pop() извлекает с вершины
        int rightOperand = operands.pop(); // Второй операнд
        int leftOperand = operands.pop();  // Первый операнд
        char op = operators.pop();         // Оператор

        int result;
        switch (op) {
            case '+':
                result = leftOperand + rightOperand;
                break;
            case '-':
                result = leftOperand - rightOperand;
                break;
            case '*':
                result = leftOperand * rightOperand;
                break;
            case '/':
                if (rightOperand == 0) {
                    throw new ArithmeticException("Division by zero in expression.");
                }
                result = leftOperand / rightOperand; // Целочисленное деление
                break;
            default:
                // Этого не должно произойти, если парсинг был правильным
                throw new IllegalArgumentException("Unknown operator on stack: " + op);
        }
        // Кладем результат операции обратно в стек операндов
        operands.push(result);
    }

    // Пример использования
    public static void main(String[] args) {
        Task100_SimpleExpressionEvaluator sol = new Task100_SimpleExpressionEvaluator();
        String[] expressions = {
                "3 + 5 * 2",         // 13
                " 10 - 4 / 2 ",      // 8
                "2 * 3 + 5 / 6 * 3 + 15", // 6 + 0 * 3 + 15 = 21
                "100 / 10 * 2",      // 10 * 2 = 20
                "5",                 // 5
                "10 / 3",            // 3
                " 2 * 2 * 2 ",       // 8
                "10 - 2 + 3",        // 8 + 3 = 11
                "100 / 2 / 5"        // 50 / 5 = 10
        };
        int[] expectedResults = {13, 8, 21, 20, 5, 3, 8, 11, 10};

        System.out.println("--- Evaluating Expressions ---");
        for (int i = 0; i < expressions.length; i++) {
            try {
                int result = sol.evaluateExpression(expressions[i]);
                System.out.println("'" + expressions[i] + "' = " + result + " (Expected: " + expectedResults[i] + ")");
                if (result != expectedResults[i]) System.err.println("   Mismatch!");
            } catch (Exception e) {
                System.err.println("Error evaluating '" + expressions[i] + "': " + e.getMessage());
                // Если ожидался результат, а получили ошибку
                if (expectedResults[i] != Integer.MIN_VALUE)
                    System.err.println("   Mismatch! Expected result, got error.");
            }
        }

        System.out.println("\n--- Error Cases ---");
        String[] errorExpressions = {"3 +", "++3", "10 / 0", "a + 5", "10 * * 5"};
        for (String expr : errorExpressions) {
            try {
                sol.evaluateExpression(expr);
                System.err.println("Mismatch! Expected error for '" + expr + "', but got result.");
            } catch (Exception e) {
                System.out.println("Caught expected error for '" + expr + "': " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        }
    }
}

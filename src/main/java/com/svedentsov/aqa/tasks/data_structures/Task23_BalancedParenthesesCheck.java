package com.svedentsov.aqa.tasks.data_structures;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Set;

/**
 * Решение задачи №23: Проверка сбалансированности скобок.
 */
public class Task23_BalancedParenthesesCheck {

    // Карта для хранения пар скобок (закрывающая -> открывающая)
    private static final Map<Character, Character> BRACKET_PAIRS = Map.of(
            ')', '(',
            '}', '{',
            ']', '['
    );
    // Множество открывающих скобок для быстрой проверки
    private static final Set<Character> OPENING_BRACKETS = Set.copyOf(BRACKET_PAIRS.values());

    /**
     * Проверяет сбалансированность скобок '()', '{}', '[]' в строке.
     * Сбалансированная строка означает, что каждая открывающая скобка имеет
     * соответствующую закрывающую скобку того же типа, и они вложены правильно.
     * Использует стек (реализованный через Deque) для отслеживания открывающих скобок.
     * Игнорирует все остальные символы в строке.
     *
     * @param expression Строка для проверки. Может быть null.
     * @return {@code true}, если скобки сбалансированы, {@code false} в противном случае.
     * Считает null и пустую строку сбалансированными.
     */
    public boolean areBracketsBalanced(String expression) {
        // null и пустая строка считаются сбалансированными
        if (expression == null || expression.isEmpty()) {
            return true;
        }

        // Используем ArrayDeque как стек (более предпочтителен, чем старый класс Stack)
        Deque<Character> stack = new ArrayDeque<>();

        // Проходим по каждому символу в строке
        for (char c : expression.toCharArray()) {
            // Если символ - открывающая скобка
            if (OPENING_BRACKETS.contains(c)) {
                stack.push(c); // Помещаем ее в стек
            }
            // Если символ - закрывающая скобка
            else if (BRACKET_PAIRS.containsKey(c)) {
                // Проверяем, есть ли в стеке соответствующая открывающая скобка:
                // 1. Если стек пуст, значит для этой закрывающей нет открывающей пары.
                if (stack.isEmpty()) {
                    return false;
                }
                // 2. Если стек не пуст, извлекаем верхний элемент (последнюю открывшую)
                //    и сравниваем с ожидаемой открывающей для текущей закрывающей.
                char lastOpen = stack.pop();
                if (lastOpen != BRACKET_PAIRS.get(c)) {
                    // Если типы скобок не совпадают (например, '(' и ']')
                    return false;
                }
            }
            // Игнорируем все остальные символы (буквы, цифры, пробелы и т.д.)
        }

        // После прохода по всей строке, если стек пуст, значит все скобки были
        // правильно сбалансированы и закрыты. Если в стеке что-то осталось,
        // значит есть незакрытые открывающие скобки.
        return stack.isEmpty();
    }

    /**
     * Точка входа для демонстрации работы метода проверки скобок.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task23_BalancedParenthesesCheck sol = new Task23_BalancedParenthesesCheck();
        String[] testCases = {
                "({[]})",       // true
                "()[]{}",       // true
                "(]",           // false
                "([)]",         // false
                "{[}",          // false
                "abc",          // true
                "(abc)",        // true
                "({[abc]})",    // true
                "(",            // false
                ")",            // false
                "(()",          // false
                "))((",         // false
                "",             // true
                null            // true
        };

        for (String testCase : testCases) {
            System.out.println("'" + testCase + "' -> " + sol.areBracketsBalanced(testCase));
        }
    }
}

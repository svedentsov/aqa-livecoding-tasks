package com.svedentsov.aqa.tasks.data_structures;

import java.util.*;

/**
 * Решение задачи №23: Проверка сбалансированности скобок.
 * <p>
 * Описание: Проверить, правильно ли расставлены скобки `()`, `{}`, `[]` в строке.
 * (Проверяет: Stack, работа со строками, логика)
 * <p>
 * Задание: Напишите метод `boolean areBracketsBalanced(String expression)`, который
 * проверяет, правильно ли сбалансированы скобки `()`, `{}`, `[]` в строке `expression`.
 * <p>
 * Пример: `areBracketsBalanced("({[]})")` -> `true`. `areBracketsBalanced("([)]")` -> `false`.
 * `areBracketsBalanced("{[}")` -> `false`. `areBracketsBalanced("()")` -> `true`.
 */
public class BalancedParenthesesCheck {

    // Константы для скобок, делаем их статическими и неизменяемыми
    private static final Map<Character, Character> BRACKET_PAIRS;
    private static final Set<Character> OPENING_BRACKETS;

    // Статический блок инициализации для констант
    static {
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put('}', '{');
        pairs.put(']', '[');
        BRACKET_PAIRS = Collections.unmodifiableMap(pairs); // Карта: Закрывающая -> Открывающая
        OPENING_BRACKETS = Collections.unmodifiableSet(new HashSet<>(BRACKET_PAIRS.values())); // Множество открывающих
    }

    /**
     * Проверяет сбалансированность скобок '()', '{}', '[]' в строке.
     * Сбалансированная строка означает, что каждая открывающая скобка имеет
     * соответствующую закрывающую скобку того же типа, и они вложены правильно.
     * Использует стек (реализованный через Deque) для отслеживания открывающих скобок.
     * Игнорирует все остальные символы в строке.
     * <p>
     * Алгоритм:
     * 1. Инициализировать пустой стек.
     * 2. Пройти по каждому символу строки:
     * a. Если символ - открывающая скобка, поместить его в стек.
     * b. Если символ - закрывающая скобка:
     * i. Если стек пуст, значит, нет соответствующей открывающей -> несбалансировано.
     * ii. Извлечь верхний элемент из стека (последнюю открывающую).
     * iii. Если извлеченная скобка не является парой для текущей закрывающей -> несбалансировано.
     * c. Если символ - не скобка, игнорировать его.
     * 3. После прохода по всей строке, если стек пуст -> сбалансировано.
     * 4. Если в стеке остались элементы -> есть незакрытые открывающие скобки -> несбалансировано.
     *
     * @param expression Строка для проверки. Может быть null.
     * @return {@code true}, если скобки сбалансированы, {@code false} в противном случае.
     * Считает null и пустую строку сбалансированными.
     */
    public boolean areBracketsBalanced(String expression) {
        // null и пустая строка считаются сбалансированными по умолчанию
        if (expression == null || expression.isEmpty()) {
            return true;
        }

        // Используем Deque (рекомендуется вместо устаревшего Stack)
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : expression.toCharArray()) {
            // Если это открывающая скобка -> в стек
            if (OPENING_BRACKETS.contains(c)) {
                stack.push(c);
            }
            // Если это закрывающая скобка
            else if (BRACKET_PAIRS.containsKey(c)) {
                // Проверяем, соответствует ли она последней открывающей
                // Стек пуст? Несбалансировано.
                if (stack.isEmpty()) {
                    return false;
                }
                // Тип скобок не совпадает? Несбалансировано.
                // BRACKET_PAIRS.get(c) вернет ожидаемую ОТКРЫВАЮЩУЮ для данной ЗАКРЫВАЮЩЕЙ c.
                if (stack.pop() != BRACKET_PAIRS.get(c)) {
                    return false;
                }
            }
            // Другие символы игнорируются
        }
        // В конце стек должен быть пуст для полной сбалансированности
        return stack.isEmpty();
    }

    /**
     * Точка входа для демонстрации работы метода проверки скобок.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        BalancedParenthesesCheck sol = new BalancedParenthesesCheck();

        System.out.println("--- Testing Bracket Balance ---");
        runBracketTest(sol, "({[]})", true);
        runBracketTest(sol, "()[]{}", true);
        runBracketTest(sol, "(]", false);
        runBracketTest(sol, "([)]", false);
        runBracketTest(sol, "{[}", false);
        runBracketTest(sol, "abc", true);
        runBracketTest(sol, "(abc)", true);
        runBracketTest(sol, "({[abc]})", true);
        runBracketTest(sol, "(", false);
        runBracketTest(sol, ")", false);
        runBracketTest(sol, "(()", false);
        runBracketTest(sol, "))((", false);
        runBracketTest(sol, "", true);
        runBracketTest(sol, null, true);
        runBracketTest(sol, "{{([][])}()}()", true); // Сложный вложенный случай
        runBracketTest(sol, "{[}]", false); // Неправильный порядок закрытия
        runBracketTest(sol, "((((()))))", true); // Только один тип скобок
        runBracketTest(sol, ")))(((", false); // Неправильный порядок
        runBracketTest(sol, "text ( before [ after ] ) text", true); // С текстом
    }

    /**
     * Вспомогательный метод для тестирования баланса скобок.
     *
     * @param sol        Экземпляр решателя.
     * @param expression Тестовая строка.
     * @param expected   Ожидаемый результат.
     */
    private static void runBracketTest(BalancedParenthesesCheck sol, String expression, boolean expected) {
        String input = (expression == null ? "null" : "\"" + expression + "\"");
        boolean result = sol.areBracketsBalanced(expression);
        System.out.printf("Expression: %-15s -> Balanced: %-5b (Expected: %-5b) %s%n",
                input, result, expected, (result == expected ? "" : "<- MISMATCH!"));
    }
}

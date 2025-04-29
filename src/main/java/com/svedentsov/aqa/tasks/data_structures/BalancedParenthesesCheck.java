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

    // Карта: Закрывающая скобка -> Ожидаемая открывающая
    private static final Map<Character, Character> BRACKET_PAIRS;
    // Множество всех открывающих скобок для быстрой проверки
    private static final Set<Character> OPENING_BRACKETS;

    // Статический блок инициализации для констант
    static {
        Map<Character, Character> pairsMap = new HashMap<>();
        pairsMap.put(')', '(');
        pairsMap.put('}', '{');
        pairsMap.put(']', '[');
        BRACKET_PAIRS = Collections.unmodifiableMap(pairsMap);
        OPENING_BRACKETS = Collections.unmodifiableSet(new HashSet<>(pairsMap.values()));
    }

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
                // Стек пуст? Значит, нет парной открывающей -> несбалансировано.
                if (stack.isEmpty()) {
                    return false;
                }
                // Тип скобок не совпадает? (pop() возвращает и удаляет верхний элемент)
                // BRACKET_PAIRS.get(c) -> ожидаемая открывающая для текущей закрывающей 'c'.
                if (stack.pop() != BRACKET_PAIRS.get(c)) {
                    return false;
                }
            }
            // Другие символы игнорируются
        }
        // В конце стек должен быть пуст для полной сбалансированности
        // Если остались открывающие скобки - несбалансировано.
        return stack.isEmpty();
    }
}

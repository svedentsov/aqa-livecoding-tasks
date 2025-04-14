package com.svedentsov.aqa.tasks.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Решение задачи №44: Найти длину самой длинной подстроки без повторяющихся символов.
 * <p>
 * Описание: (Проверяет: работа со строками, Set/Map, два указателя/sliding window)
 * <p>
 * Задание: Напишите метод `int lengthOfLongestSubstring(String s)`, который находит
 * длину самой длинной подстроки строки `s` без повторяющихся символов.
 * <p>
 * Пример: `lengthOfLongestSubstring("abcabcbb")` -> `3` (подстрока "abc").
 * `lengthOfLongestSubstring("bbbbb")` -> `1` (подстрока "b").
 * `lengthOfLongestSubstring("pwwkew")` -> `3` (подстрока "wke").
 */
public class LongestSubstringWithoutRepeating {

    /**
     * Находит длину самой длинной подстроки без повторяющихся символов.
     * Использует метод скользящего окна (sliding window) с {@link HashSet}
     * для отслеживания уникальных символов в текущем окне.
     * <p>
     * Сложность: O(n) по времени (каждый символ добавляется и удаляется из Set
     * не более одного раза), O(k) по памяти, где k - размер алфавита/количество
     * уникальных символов (в худшем случае O(min(n, |Sigma|))).
     * <p>
     * Алгоритм:
     * 1. Инициализировать `Set<Character> windowChars`, `maxLength = 0`, `left = 0`.
     * 2. Итерировать правой границей окна `right` от `0` до `n-1`.
     * 3. Для символа `currentChar = s.charAt(right)`:
     * a. `while (windowChars.contains(currentChar))`:
     * i. Удалить `s.charAt(left)` из `windowChars`.
     * ii. Увеличить `left`.
     * b. Добавить `currentChar` в `windowChars`.
     * c. Обновить `maxLength = Math.max(maxLength, right - left + 1)`.
     * 4. Вернуть `maxLength`.
     *
     * @param s Входная строка. Может быть null.
     * @return Длина самой длинной подстроки без повторяющихся символов.
     * Возвращает 0 для null или пустой строки.
     */
    public int lengthOfLongestSubstringSet(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int n = s.length();
        Set<Character> windowChars = new HashSet<>();
        int maxLength = 0;
        int left = 0; // Левая граница окна

        // Двигаем правую границу окна
        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // Если символ уже есть в окне, сдвигаем левую границу,
            // пока дубликат не будет удален
            while (windowChars.contains(currentChar)) {
                windowChars.remove(s.charAt(left));
                left++;
            }

            // Добавляем текущий символ в окно
            windowChars.add(currentChar);

            // Обновляем максимальную длину
            maxLength = Math.max(maxLength, right - left + 1); // Длина текущего окна: right - left + 1
        }

        return maxLength;
    }

    /**
     * Альтернативный метод скользящего окна с использованием {@link HashMap}
     * для хранения последнего индекса каждого символа: {символ: последний_индекс}.
     * Позволяет левой границе "перепрыгивать" сразу за позицию предыдущего вхождения
     * дублирующегося символа, что может быть чуть эффективнее в некоторых случаях.
     * <p>
     * Сложность: O(n) по времени и O(k) по памяти (аналогично методу с Set).
     * <p>
     * Алгоритм:
     * 1. Инициализировать `Map<Character, Integer> charIndexMap`, `maxLength = 0`, `left = 0`.
     * 2. Итерировать правой границей окна `right` от `0` до `n-1`.
     * 3. Для символа `currentChar = s.charAt(right)`:
     * a. Если `charIndexMap` содержит `currentChar`:
     * i. Сдвинуть `left` на `max(left, index_предыдущего_вхождения + 1)`.
     * b. Обновить/добавить `currentChar` и его текущий индекс `right` в `charIndexMap`.
     * c. Обновить `maxLength = Math.max(maxLength, right - left + 1)`.
     * 4. Вернуть `maxLength`.
     *
     * @param s Входная строка.
     * @return Длина самой длинной подстроки без повторяющихся символов.
     */
    public int lengthOfLongestSubstringMap(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int n = s.length();
        Map<Character, Integer> charIndexMap = new HashMap<>(); // {char -> last_index}
        int maxLength = 0;
        int left = 0; // Начало текущего окна (индекс)

        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // Если символ уже встречался И его последнее вхождение >= начала текущего окна 'left'
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= left) {
                // Сдвигаем левую границу окна вправо от предыдущего вхождения этого символа
                left = charIndexMap.get(currentChar) + 1;
            }

            // Обновляем последний известный индекс текущего символа
            charIndexMap.put(currentChar, right);

            // Вычисляем длину текущего окна и обновляем максимум
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    /**
     * Точка входа для демонстрации работы методов поиска самой длинной подстроки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        LongestSubstringWithoutRepeating sol = new LongestSubstringWithoutRepeating();
        String[] testStrings = {"abcabcbb", "bbbbb", "pwwkew", "", " ", "au", "dvdf", "tmmzuxt", "aab", "abcde", null};
        int[] expectedLengths = {3, 1, 3, 0, 1, 2, 3, 5, 2, 5, 0};

        System.out.println("--- Finding Longest Substring Without Repeating Characters ---");
        for (int i = 0; i < testStrings.length; i++) {
            runLongestSubstringTest(sol, testStrings[i], expectedLengths[i]);
        }
    }

    /**
     * Вспомогательный метод для тестирования методов поиска подстроки.
     *
     * @param sol      Экземпляр решателя.
     * @param s        Входная строка.
     * @param expected Ожидаемая длина.
     */
    private static void runLongestSubstringTest(LongestSubstringWithoutRepeating sol, String s, int expected) {
        String input = (s == null ? "null" : "'" + s + "'");
        System.out.println("\nInput: " + input);
        try {
            int lengthSet = sol.lengthOfLongestSubstringSet(s);
            System.out.printf("  Length (Set Method): %d (Expected: %d) %s%n",
                    lengthSet, expected, (lengthSet == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.err.println("  Length (Set Method): Error - " + e.getMessage());
        }
        try {
            int lengthMap = sol.lengthOfLongestSubstringMap(s);
            System.out.printf("  Length (Map Method): %d (Expected: %d) %s%n",
                    lengthMap, expected, (lengthMap == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.err.println("  Length (Map Method): Error - " + e.getMessage());
        }
    }
}

package com.svedentsov.aqa.tasks.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Решение задачи №44: Найти длину самой длинной подстроки без повторяющихся символов.
 */
public class Task44_LongestSubstringWithoutRepeating {

    /**
     * Находит длину самой длинной подстроки без повторяющихся символов.
     * Использует метод скользящего окна (sliding window) с {@link HashSet}
     * для отслеживания уникальных символов в текущем окне.
     * Сложность O(n) по времени (каждый символ добавляется и удаляется из Set не более одного раза),
     * O(k) по памяти, где k - размер алфавита/количество уникальных символов в строке (в худшем случае).
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
        // Множество для хранения символов в текущем окне [left, right]
        Set<Character> windowChars = new HashSet<>();
        int maxLength = 0;
        int left = 0; // Левая граница окна (включительно)

        // Правая граница окна (right) движется по строке
        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // Если текущий символ УЖЕ присутствует в окне, это означает повтор.
            // Нужно сдвигать левую границу окна (left) вправо, удаляя символы
            // из множества `windowChars`, до тех пор, пока дубликат `currentChar`
            // не будет удален из окна.
            while (windowChars.contains(currentChar)) {
                windowChars.remove(s.charAt(left));
                left++; // Сдвигаем левую границу
            }

            // Теперь дубликата `currentChar` в окне [left, right] точно нет.
            // Добавляем текущий символ в окно.
            windowChars.add(currentChar);

            // Обновляем максимальную длину найденной подстроки.
            // Длина текущего окна без повторов: right - left + 1.
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * Альтернативный метод скользящего окна с использованием {@link HashMap}
     * для хранения последнего индекса каждого символа.
     * Позволяет левой границе "перепрыгивать" сразу за позицию дубликата.
     * Сложность также O(n) по времени и O(k) по памяти.
     *
     * @param s Входная строка.
     * @return Длина самой длинной подстроки без повторяющихся символов.
     */
    public int lengthOfLongestSubstringMap(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int n = s.length();
        // Карта для хранения последнего индекса символа: {символ: последний_индекс}
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        // `left` - начало текущего окна (подстроки без повторов). Индекс первого символа окна.
        int left = 0;

        // `right` - правая граница окна (индекс текущего рассматриваемого символа)
        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // Если текущий символ уже встречался РАНЕЕ в строке...
            if (charIndexMap.containsKey(currentChar)) {
                // ...и его последнее вхождение (charIndexMap.get(currentChar))
                // находится ВНУТРИ текущего окна (т.е. >= left),
                // то нам нужно сдвинуть левую границу окна `left`
                // вправо от этого предыдущего вхождения.
                // Берем максимум из текущего `left` и `index + 1`, чтобы `left` не сдвинулся влево.
                left = Math.max(left, charIndexMap.get(currentChar) + 1);
            }

            // Обновляем (или добавляем) последний известный индекс текущего символа.
            charIndexMap.put(currentChar, right);

            // Вычисляем длину текущего окна (right - left + 1) и обновляем максимум.
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
        Task44_LongestSubstringWithoutRepeating sol = new Task44_LongestSubstringWithoutRepeating();
        String[] testStrings = {"abcabcbb", "bbbbb", "pwwkew", "", " ", "au", "dvdf", "tmmzuxt"};
        int[] expectedLengths = {3, 1, 3, 0, 1, 2, 3, 5}; // для tmmzuxt -> mzuxt

        System.out.println("--- Using HashSet ---");
        for (int i = 0; i < testStrings.length; i++) {
            int len = sol.lengthOfLongestSubstringSet(testStrings[i]);
            System.out.println("'" + testStrings[i] + "' -> " + len + " (Expected: " + expectedLengths[i] + ")");
            if (len != expectedLengths[i]) System.err.println("  Mismatch!");
        }

        System.out.println("\n--- Using HashMap ---");
        for (int i = 0; i < testStrings.length; i++) {
            int len = sol.lengthOfLongestSubstringMap(testStrings[i]);
            System.out.println("'" + testStrings[i] + "' -> " + len + " (Expected: " + expectedLengths[i] + ")");
            if (len != expectedLengths[i]) System.err.println("  Mismatch!");
        }
    }
}

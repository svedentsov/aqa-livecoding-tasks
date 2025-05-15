package com.svedentsov.aqa.tasks.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Решение задачи №44: Найти длину самой длинной подстроки без повторяющихся символов.
 * Описание: (Проверяет: работа со строками, Set/Map, два указателя/sliding window)
 * Задание: Напишите метод `int lengthOfLongestSubstring(String s)`, который находит
 * длину самой длинной подстроки строки `s` без повторяющихся символов.
 * Пример: `lengthOfLongestSubstring("abcabcbb")` -> `3` (подстрока "abc").
 * `lengthOfLongestSubstring("bbbbb")` -> `1` (подстрока "b").
 * `lengthOfLongestSubstring("pwwkew")` -> `3` (подстрока "wke").
 */
public class LongestSubstringWithoutRepeating {

    /**
     * Находит длину самой длинной подстроки без повторяющихся символов.
     * Использует метод скользящего окна (sliding window) с {@link HashSet}
     * для отслеживания уникальных символов в текущем окне.
     * Сложность: O(n) по времени, O(k) по памяти (k - размер алфавита).
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
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * Альтернативный метод скользящего окна с использованием {@link HashMap}
     * для хранения последнего индекса каждого символа: {символ: последний_индекс}.
     * Сложность: O(n) по времени и O(k) по памяти.
     *
     * @param s Входная строка. Может быть null.
     * @return Длина самой длинной подстроки без повторяющихся символов.
     * Возвращает 0 для null или пустой строки.
     */
    public int lengthOfLongestSubstringMap(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int n = s.length();
        Map<Character, Integer> charIndexMap = new HashMap<>(); // {char -> last_index}
        int maxLength = 0;
        int left = 0; // Начало текущего окна (индекс НЕ входящего элемента)

        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // Если символ уже встречался И его последнее вхождение >= начала текущего окна 'left'
            if (charIndexMap.containsKey(currentChar)) {
                // Сдвигаем левую границу окна ПРАВЕЕ предыдущего вхождения этого символа
                // Используем max, чтобы left не "откатился" назад
                left = Math.max(left, charIndexMap.get(currentChar) + 1);
            }

            // Обновляем последний известный индекс текущего символа
            charIndexMap.put(currentChar, right);

            // Вычисляем длину текущего окна (right - left + 1) и обновляем максимум
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
}

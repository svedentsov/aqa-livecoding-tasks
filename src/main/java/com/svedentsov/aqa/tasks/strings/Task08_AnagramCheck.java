package com.svedentsov.aqa.tasks.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Решение задачи №8: Проверка на анаграммы.
 */
public class Task08_AnagramCheck {

    /**
     * Проверяет, являются ли две строки анаграммами (состоят из одинаковых символов
     * в разном количестве), игнорируя регистр и пробелы.
     * Метод использует сортировку символов строк.
     * Сложность O(N log N) из-за сортировки, где N - длина большей строки.
     *
     * @param str1 Первая строка.
     * @param str2 Вторая строка.
     * @return {@code true}, если строки являются анаграммами, {@code false} в противном случае.
     * Считает null не анаграммой ничему, кроме null (если требуется иное, изменить).
     */
    public boolean areAnagramsSort(String str1, String str2) {
        if (str1 == null && str2 == null) return true; // Или false по требованию
        if (str1 == null || str2 == null) return false;

        // 1. Очистка: убираем пробельные символы и приводим к нижнему регистру
        String clean1 = str1.replaceAll("\\s+", "").toLowerCase();
        String clean2 = str2.replaceAll("\\s+", "").toLowerCase();

        // 2. Проверка длины (анаграммы должны иметь одинаковую длину после очистки)
        if (clean1.length() != clean2.length()) {
            return false;
        }

        // 3. Сортировка массивов символов
        char[] chars1 = clean1.toCharArray();
        char[] chars2 = clean2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);

        // 4. Сравнение отсортированных массивов
        return Arrays.equals(chars1, chars2);
    }

    /**
     * Проверяет, являются ли две строки анаграммами, используя карту подсчета символов.
     * Игнорирует регистр и пробелы.
     * Сложность O(N) по времени, где N - длина большей строки, и O(K) по памяти,
     * где K - количество уникальных символов (размер алфавита).
     *
     * @param str1 Первая строка.
     * @param str2 Вторая строка.
     * @return {@code true}, если строки являются анаграммами, {@code false} в противном случае.
     */
    public boolean areAnagramsMap(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        if (str1 == null || str2 == null) return false;

        String clean1 = str1.replaceAll("\\s+", "").toLowerCase();
        String clean2 = str2.replaceAll("\\s+", "").toLowerCase();

        if (clean1.length() != clean2.length()) {
            return false;
        }
        // Если строки пустые после очистки, они анаграммы
        if (clean1.isEmpty()) {
            return true;
        }

        // Карта для подсчета символов в первой строке
        Map<Character, Integer> charCounts = new HashMap<>();
        for (char c : clean1.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        // Уменьшаем счетчики для символов второй строки
        for (char c : clean2.toCharArray()) {
            int count = charCounts.getOrDefault(c, 0);
            // Если символа нет в карте или его счетчик уже 0
            if (count == 0) {
                return false; // Не анаграммы
            }
            // Уменьшаем счетчик
            charCounts.put(c, count - 1);
        }

        // Если дошли до сюда, значит, все символы второй строки нашлись
        // в первой с нужной частотой. Так как длины строк равны, это означает,
        // что все счетчики в карте должны были стать нулями. Проверять это не обязательно.
        return true;
    }

    /**
     * Точка входа для демонстрации работы методов проверки анаграмм.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task08_AnagramCheck sol = new Task08_AnagramCheck();
        System.out.println("--- Sort method ---");
        System.out.println("'listen', 'silent' -> " + sol.areAnagramsSort("listen", "silent")); // true
        System.out.println("'Dormitory', 'dirty room' -> " + sol.areAnagramsSort("Dormitory", "dirty room")); // true
        System.out.println("'hello', 'world' -> " + sol.areAnagramsSort("hello", "world")); // false
        System.out.println("'abc', 'aabc' -> " + sol.areAnagramsSort("abc", "aabc")); // false
        System.out.println("'a b', 'b a' -> " + sol.areAnagramsSort("a b", "b a")); // true
        System.out.println("null, null -> " + sol.areAnagramsSort(null, null)); // true
        System.out.println("'abc', null -> " + sol.areAnagramsSort("abc", null)); // false

        System.out.println("\n--- Map method ---");
        System.out.println("'listen', 'silent' -> " + sol.areAnagramsMap("listen", "silent")); // true
        System.out.println("'Dormitory', 'dirty room' -> " + sol.areAnagramsMap("Dormitory", "dirty room")); // true
        System.out.println("'hello', 'world' -> " + sol.areAnagramsMap("hello", "world")); // false
        System.out.println("'rat', 'car' -> " + sol.areAnagramsMap("rat", "car")); // false
        System.out.println("'aabb', 'bbaa' -> " + sol.areAnagramsMap("aabb", "bbaa")); // true
        System.out.println("'aaz', 'zza' -> " + sol.areAnagramsMap("aaz", "zza")); // false
    }
}

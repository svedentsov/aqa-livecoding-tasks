package com.svedentsov.aqa.tasks.strings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Решение задачи №8: Проверка на анаграммы.
 * <p>
 * Описание: Написать функцию, которая проверяет, являются ли две строки анаграммами
 * друг друга (состоят из одних и тех же символов в разном порядке).
 * (Проверяет: работа со строками, сортировка/Map, сравнение)
 * <p>
 * Задание: Напишите метод `boolean areAnagrams(String str1, String str2)`,
 * который возвращает `true`, если строки `str1` и `str2` являются анаграммами
 * (игнорируя регистр и небуквенные символы - см. реализацию cleanString), и `false` иначе.
 * <p>
 * Пример: `areAnagrams("listen", "silent")` -> `true`,
 * `areAnagrams("Dormitory", "dirty room")` -> `true`,
 * `areAnagrams("hello", "world")` -> `false`.
 */
public class AnagramCheck {

    /**
     * Вспомогательный метод для очистки строки: удаление НЕБУКВЕННЫХ символов и приведение к нижнему регистру.
     *
     * @param s Исходная строка.
     * @return Очищенная строка, содержащая только буквы в нижнем регистре.
     */
    private String cleanString(String s) {
        if (s == null) return ""; // Возвращаем пустую строку для null, чтобы избежать NPE в replaceAll
        // Удаляем все, что НЕ является буквой (согласно стандарту Unicode \p{L})
        // и приводим к нижнему регистру.
        return s.replaceAll("[^\\p{L}]", "").toLowerCase();
        // Примечание: [^a-zA-Z] удалит только латинские буквы. [^\p{L}] более универсален.
    }

    /**
     * Проверяет, являются ли две строки анаграммами, игнорируя регистр и небуквенные символы.
     * Метод использует сортировку символов очищенных строк.
     * Сложность O(N log N) из-за сортировки, где N - длина большей очищенной строки.
     *
     * @param str1 Первая строка.
     * @param str2 Вторая строка.
     * @return {@code true}, если строки являются анаграммами, {@code false} в противном случае.
     * Считает null анаграммой null.
     */
    public boolean areAnagramsSort(String str1, String str2) {
        if (str1 == null && str2 == null) return true; // Политика: null анаграмма null
        if (str1 == null || str2 == null) return false; // null не анаграмма не-null

        // 1. Очистка строк
        String clean1 = cleanString(str1);
        String clean2 = cleanString(str2);

        // 2. Проверка длины (анаграммы должны иметь одинаковую длину после очистки)
        if (clean1.length() != clean2.length()) {
            return false;
        }
        // Если обе строки пусты после очистки, считаем их анаграммами
        // (например, "123" и "!@#$")
        if (clean1.isEmpty()) {
            return true;
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
     * Игнорирует регистр и небуквенные символы.
     * Сложность O(N) по времени, где N - длина большей очищенной строки, и O(K) по памяти,
     * где K - количество уникальных символов (размер алфавита).
     *
     * @param str1 Первая строка.
     * @param str2 Вторая строка.
     * @return {@code true}, если строки являются анаграммами, {@code false} в противном случае.
     * Считает null анаграммой null.
     */
    public boolean areAnagramsMap(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        if (str1 == null || str2 == null) return false;

        String clean1 = cleanString(str1);
        String clean2 = cleanString(str2);

        if (clean1.length() != clean2.length()) {
            return false;
        }
        // Если обе строки пусты после очистки, считаем их анаграммами
        if (clean1.isEmpty()) {
            return true;
        }

        // Карта для подсчета символов в первой строке
        Map<Character, Integer> charCounts = new HashMap<>();
        for (char c : clean1.toCharArray()) {
            // Увеличиваем счетчик для символа c
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        // Уменьшаем счетчики для символов второй строки
        for (char c : clean2.toCharArray()) {
            int count = charCounts.getOrDefault(c, 0);
            // Если символа нет в карте или его счетчик уже 0 - не анаграммы
            if (count == 0) {
                return false;
            }
            // Уменьшаем счетчик
            charCounts.put(c, count - 1);
        }

        // Проверка не нужна, если длины равны и все символы второй строки "погасили" счетчики
        return true;
    }
}

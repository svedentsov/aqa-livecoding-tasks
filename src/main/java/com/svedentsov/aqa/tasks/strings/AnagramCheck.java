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
 * (игнорируя регистр и пробелы), и `false` иначе.
 * <p>
 * Пример: `areAnagrams("listen", "silent")` -> `true`,
 * `areAnagrams("Dormitory", "dirty room")` -> `true`,
 * `areAnagrams("hello", "world")` -> `false`.
 */
public class AnagramCheck {

    /**
     * Вспомогательный метод для очистки строки: удаление небуквенных символов и приведение к нижнему регистру.
     * Изменено: теперь удаляем ВСЕ, кроме букв.
     *
     * @param s Исходная строка.
     * @return Очищенная строка.
     */
    private String cleanString(String s) {
        // Удаляем все, что НЕ является буквой (можно изменить на isLetterOrDigit, если цифры тоже важны)
        return s.replaceAll("[^a-zA-Z]", "").toLowerCase();
        // Вариант с удалением только пробелов:
        // return s.replaceAll("\\s+", "").toLowerCase();
    }

    /**
     * Проверяет, являются ли две строки анаграммами, игнорируя регистр и небуквенные символы.
     * Метод использует сортировку символов очищенных строк.
     * Сложность O(N log N) из-за сортировки, где N - длина большей очищенной строки.
     *
     * @param str1 Первая строка.
     * @param str2 Вторая строка.
     * @return {@code true}, если строки являются анаграммами, {@code false} в противном случае.
     * Считает null не анаграммой ничему, кроме null (можно изменить политику).
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
     */
    public boolean areAnagramsMap(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        if (str1 == null || str2 == null) return false;

        String clean1 = cleanString(str1);
        String clean2 = cleanString(str2);

        if (clean1.length() != clean2.length()) {
            return false;
        }
        // Если обе строки пустые после очистки, считаем их анаграммами
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

        // Если все символы второй строки совпали с первой и уменьшили счетчики,
        // и длины строк были равны, то все счетчики должны быть нулями.
        // Проверка на нулевые счетчики не обязательна, т.к. предыдущие шаги это гарантируют.
        return true;
    }

    /**
     * Точка входа для демонстрации работы методов проверки анаграмм.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        AnagramCheck sol = new AnagramCheck();

        runAnagramTest(sol, "listen", "silent");         // true
        runAnagramTest(sol, "Dormitory", "dirty room");  // true (игнорируя регистр и пробел)
        runAnagramTest(sol, "hello", "world");           // false
        runAnagramTest(sol, "abc", "aabc");              // false (разная длина после очистки)
        runAnagramTest(sol, "a b", "b a");               // true (игнорируя пробелы)
        runAnagramTest(sol, null, null);                 // true (по нашей политике)
        runAnagramTest(sol, "abc", null);                // false
        runAnagramTest(sol, null, "abc");                // false
        runAnagramTest(sol, "rat", "car");               // false
        runAnagramTest(sol, "aabb", "bbaa");             // true
        runAnagramTest(sol, "aaz", "zza");               // false (разные символы/количество)
        runAnagramTest(sol, "Astronomer", "Moon starer"); // true
        runAnagramTest(sol, "School master", "The classroom"); // true
        runAnagramTest(sol, " ", "   ");                 // true (пустые после очистки)
        runAnagramTest(sol, "123", "321");               // true (если cleanString удаляет только пробелы) / false (если удаляет и цифры)
        // --> ЗАВИСИТ ОТ РЕАЛИЗАЦИИ cleanString
        // Текущая реализация cleanString удалит цифры -> false
        runAnagramTest(sol, "rail safety!", "fairy tales!"); // true (игнорируя '!' и пробелы)
        runAnagramTest(sol, "", "");                     // true
    }

    /**
     * Вспомогательный метод для тестирования методов проверки анаграмм.
     *
     * @param sol Экземпляр решателя.
     * @param s1  Первая строка.
     * @param s2  Вторая строка.
     */
    private static void runAnagramTest(AnagramCheck sol, String s1, String s2) {
        String input1 = (s1 == null ? "null" : "\"" + s1 + "\"");
        String input2 = (s2 == null ? "null" : "\"" + s2 + "\"");
        System.out.println("\n--- Testing Anagrams ---");
        System.out.println("Input 1: " + input1);
        System.out.println("Input 2: " + input2);
        try {
            boolean resultSort = sol.areAnagramsSort(s1, s2);
            System.out.println("areAnagrams (Sort): " + resultSort);
        } catch (Exception e) {
            System.out.println("areAnagrams (Sort): Error - " + e.getMessage());
        }
        try {
            boolean resultMap = sol.areAnagramsMap(s1, s2);
            System.out.println("areAnagrams (Map):  " + resultMap);
        } catch (Exception e) {
            System.out.println("areAnagrams (Map):  Error - " + e.getMessage());
        }
    }
}

package com.svedentsov.aqa.tasks.strings;

import java.util.Arrays;

/**
 * Решение задачи №52: Найти самый длинный общий префикс для массива строк.
 */
public class Task52_LongestCommonPrefix {

    /**
     * Находит самую длинную строку, которая является общим префиксом
     * для всех строк в заданном массиве.
     * Использует подход "горизонтального сканирования": начинает с первой строки
     * как потенциального префикса и укорачивает его, сравнивая с остальными строками.
     *
     * @param strs Массив строк. Может быть null или содержать null/пустые строки.
     * @return Самый длинный общий префикс. Возвращает пустую строку "", если общего
     * префикса нет, массив null/пуст, или первая строка null/пуста.
     */
    public String longestCommonPrefixHorizontal(String[] strs) {
        // Если массив null или пуст, нет префикса.
        if (strs == null || strs.length == 0) {
            return "";
        }

        // Берем первую строку как начальный префикс.
        String prefix = strs[0];

        // Если первая строка null или пуста, префикс пустой.
        if (prefix == null || prefix.isEmpty()) {
            return "";
        }


        // Итерируем по остальным строкам массива, начиная со второй (индекс 1).
        for (int i = 1; i < strs.length; i++) {
            String currentStr = strs[i];

            // Если текущая строка null, общего префикса быть не может.
            if (currentStr == null) {
                return "";
            }

            // Уменьшаем текущий префикс до тех пор, пока он не станет
            // префиксом для currentStr (проверяем с помощью startsWith()).
            // indexOf() != 0 тоже работает, но startsWith() семантически яснее.
            while (!currentStr.startsWith(prefix)) {
                // Укорачиваем префикс на один символ с конца.
                prefix = prefix.substring(0, prefix.length() - 1);

                // Если префикс стал пустым, значит, общего префикса для всех строк нет.
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        // Возвращаем префикс, который подошел для всех строк.
        return prefix;
    }

    /**
     * Находит самый длинный общий префикс, используя "вертикальное сканирование".
     * Сравнивает символы на одних и тех же позициях (вертикально) во всех строках.
     *
     * @param strs Массив строк.
     * @return Самый длинный общий префикс.
     */
    public String longestCommonPrefixVertical(String[] strs) {
        if (strs == null || strs.length == 0) return "";
        String firstStr = strs[0];
        // Если первая строка null или пуста, префикс пустой.
        if (firstStr == null || firstStr.isEmpty()) return "";

        // Идем по индексам символов первой строки (потенциального префикса)
        for (int i = 0; i < firstStr.length(); i++) {
            char currentChar = firstStr.charAt(i); // Текущий символ из первой строки

            // Проверяем этот символ во всех ОСТАЛЬНЫХ строках на той же позиции i
            for (int j = 1; j < strs.length; j++) {
                String otherStr = strs[j];
                // Условия остановки и возврата текущего префикса (до символа i):
                // 1. Другая строка null.
                // 2. Длина другой строки меньше текущего индекса i (символа нет).
                // 3. Символ на позиции i в другой строке не совпадает с currentChar.
                if (otherStr == null || i >= otherStr.length() || otherStr.charAt(i) != currentChar) {
                    // Общий префикс - это часть первой строки до индекса i
                    return firstStr.substring(0, i);
                }
            }
            // Если внутренний цикл завершился без выхода, значит, символ currentChar
            // присутствует на позиции i во всех строках. Продолжаем проверку следующего символа.
        }
        // Если внешний цикл завершился полностью, значит, вся первая строка
        // является общим префиксом для всех строк массива.
        return firstStr;
    }

    /**
     * Точка входа для демонстрации работы методов поиска LCP.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task52_LongestCommonPrefix sol = new Task52_LongestCommonPrefix();
        String[][] testCases = {
                {"flower", "flow", "flight"}, // fl
                {"dog", "racecar", "car"},   // ""
                {"interspecies", "interstellar", "interstate"}, // inters
                {"", "b"},                   // ""
                {"a"},                       // a
                {"abc", null, "abd"},       // ""
                {"apple", "apply", "apricot"}, // ap
                null,                       // ""
                {}                          // ""
        };

        System.out.println("--- Horizontal Scan ---");
        for (String[] strs : testCases) {
            String prefix = sol.longestCommonPrefixHorizontal(strs);
            System.out.println(Arrays.toString(strs) + " -> '" + prefix + "'");
        }
        System.out.println("\n--- Vertical Scan ---");
        for (String[] strs : testCases) {
            String prefix = sol.longestCommonPrefixVertical(strs);
            System.out.println(Arrays.toString(strs) + " -> '" + prefix + "'");
        }
    }
}

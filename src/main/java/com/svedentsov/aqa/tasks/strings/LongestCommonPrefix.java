package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №52: Найти самый длинный общий префикс для массива строк.
 * Описание: Найти самую длинную строку, которая является префиксом всех строк в массиве.
 * (Проверяет: работа со строками, циклы)
 * Задание: Напишите метод `String longestCommonPrefix(String[] strs)`, который
 * находит самую длинную строку, являющуюся префиксом всех строк в массиве `strs`.
 * Если общего префикса нет, верните пустую строку `""`.
 * Пример: `longestCommonPrefix(new String[]{"flower", "flow", "flight"})` -> `"fl"`.
 * `longestCommonPrefix(new String[]{"dog", "racecar", "car"})` -> `""`.
 */
public class LongestCommonPrefix {

    /**
     * Находит самый длинный общий префикс для массива строк, используя
     * подход "горизонтального сканирования".
     * Начинает с первой строки как потенциального префикса и укорачивает его,
     * последовательно сравнивая с каждой следующей строкой в массиве.
     * Сложность: O(S), где S - суммарная длина всех символов во всех строках.
     * В худшем случае (все строки равны) это O(n*m), где n - кол-во строк, m - длина строки.
     *
     * @param strs Массив строк. Может быть null или содержать null/пустые строки.
     * @return Самый длинный общий префикс. Возвращает пустую строку "", если общего
     * префикса нет, массив null/пуст, или содержит null/пустую строку в начале.
     */
    public String longestCommonPrefixHorizontal(String[] strs) {
        // Крайние случаи: null или пустой массив
        if (strs == null || strs.length == 0) {
            return "";
        }
        // Берем первую строку как начальный кандидат в префиксы
        String prefix = strs[0];
        // Если первый кандидат null или пуст, то и префикс пуст
        if (prefix == null || prefix.isEmpty()) {
            return "";
        }
        // Сравниваем текущий префикс с остальными строками
        for (int i = 1; i < strs.length; i++) {
            String currentStr = strs[i];
            // Если встретили null строку, общего префикса быть не может
            if (currentStr == null) {
                return "";
            }
            // Уменьшаем префикс, пока он не станет префиксом текущей строки currentStr
            // Используем startsWith() для проверки префикса
            while (!currentStr.startsWith(prefix)) {
                // Укорачиваем префикс на 1 символ с конца
                prefix = prefix.substring(0, prefix.length() - 1);
                // Если префикс стал пустым, значит общего префикса нет
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        // Если дошли до конца, то `prefix` является общим для всех
        return prefix;
    }

    /**
     * Находит самый длинный общий префикс, используя "вертикальное сканирование".
     * Сложность: O(S) = O(n*m) в худшем случае.
     *
     * @param strs Массив строк. Может быть null или содержать null/пустые строки.
     * @return Самый длинный общий префикс.
     */
    public String longestCommonPrefixVertical(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String firstStr = strs[0];
        if (firstStr == null || firstStr.isEmpty()) {
            return "";
        }
        // Итерируем по символам первой строки (потенциального префикса)
        for (int i = 0; i < firstStr.length(); i++) {
            char currentChar = firstStr.charAt(i);
            // Проверяем этот символ во всех остальных строках (j=1 до n-1)
            for (int j = 1; j < strs.length; j++) {
                String otherStr = strs[j];
                // Если другая строка короче текущего индекса `i` ИЛИ
                // символ на позиции `i` в другой строке не совпадает
                if (otherStr == null || i >= otherStr.length() || otherStr.charAt(i) != currentChar) {
                    // Общий префикс заканчивается перед индексом `i`
                    return firstStr.substring(0, i);
                }
            }
            // Если внутренний цикл прошел, значит символ `currentChar` общий для всех
        }
        // Если внешний цикл завершился, вся первая строка является общим префиксом
        return firstStr;
    }
}

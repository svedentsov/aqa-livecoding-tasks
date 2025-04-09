package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №2: Перевернуть строку.
 */
public class Task02_ReverseString {

    /**
     * Переворачивает заданную строку с использованием StringBuilder.
     *
     * @param str Исходная строка. Может быть null.
     * @return Перевернутая строка или null, если на вход подан null.
     */
    public String reverseString(String str) {
        if (str == null) {
            return null;
        }
        // Использование StringBuilder для эффективности
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Переворачивает заданную строку вручную с использованием массива символов.
     *
     * @param str Исходная строка. Может быть null.
     * @return Перевернутая строка или null, если на вход подан null.
     */
    public String reverseStringManual(String str) {
        if (str == null) {
            return null;
        }
        char[] chars = str.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            // Обмен символов
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    /**
     * Точка входа для демонстрации работы методов переворота строки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task02_ReverseString sol = new Task02_ReverseString();
        System.out.println("reverseString(\"hello\"): " + sol.reverseString("hello")); // olleh
        System.out.println("reverseStringManual(\"Java\"): " + sol.reverseStringManual("Java")); // avaJ
        System.out.println("reverseString(null): " + sol.reverseString(null)); // null
        System.out.println("reverseString(\"\"): " + sol.reverseString("")); // ""
    }
}

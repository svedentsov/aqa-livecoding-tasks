package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №2: Перевернуть строку.
 * <p>
 * Описание: Написать функцию, которая принимает строку и возвращает её в перевернутом виде.
 * (Проверяет: работа со строками, циклы/рекурсия/StringBuilder)
 * <p>
 * Задание: Напишите метод `String reverseString(String str)`, который принимает строку
 * и возвращает новую строку, являющуюся перевернутой версией исходной.
 * <p>
 * Пример: `reverseString("hello")` -> `"olleh"`, `reverseString("Java")` -> `"avaJ"`.
 */
public class ReverseString {

    /**
     * Переворачивает заданную строку с использованием встроенного метода reverse() класса StringBuilder.
     * Этот метод является наиболее простым и часто предпочтительным в Java.
     *
     * @param str Исходная строка. Может быть null или пустой.
     * @return Перевернутая строка, null если на вход подан null, или пустая строка, если на вход подана пустая строка.
     */
    public String reverseString(String str) {
        if (str == null) {
            return null; // Обработка случая с null
        }
        // StringBuilder эффективен для модификации строк
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Переворачивает заданную строку вручную с использованием цикла и массива символов.
     * Этот метод демонстрирует понимание работы со строками на более низком уровне.
     *
     * @param str Исходная строка. Может быть null или пустой.
     * @return Перевернутая строка, null если на вход подан null, или пустая строка, если на вход подана пустая строка.
     */
    public String reverseStringManual(String str) {
        if (str == null) {
            return null; // Обработка случая с null
        }
        char[] chars = str.toCharArray(); // Преобразование строки в массив символов
        int left = 0; // Указатель на начало
        int right = chars.length - 1; // Указатель на конец
        while (left < right) {
            // Обмен символов местами
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            // Сдвиг указателей навстречу друг другу
            left++;
            right--;
        }
        // Создание новой строки из измененного массива символов
        return new String(chars);
    }

    /**
     * Точка входа для демонстрации работы методов переворота строки с различными примерами.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ReverseString sol = new ReverseString();

        String[] testStrings = {
                "hello",
                "Java",
                null,
                "",
                "a",
                "madam", // Палиндром
                " ",
                "  leading and trailing spaces  ",
                "12345!@#$"
        };

        System.out.println("--- Тестирование reverseString (StringBuilder) ---");
        for (String test : testStrings) {
            System.out.printf("Input: \"%s\" -> Output: \"%s\"%n", test, sol.reverseString(test));
        }

        System.out.println("\n--- Тестирование reverseStringManual (char array) ---");
        for (String test : testStrings) {
            System.out.printf("Input: \"%s\" -> Output: \"%s\"%n", test, sol.reverseStringManual(test));
        }

        // Сравнение результатов обоих методов на примере
        String example = "Programming";
        System.out.println("\n--- Сравнение методов ---");
        System.out.println("Original: " + example);
        System.out.println("reverseString: " + sol.reverseString(example));
        System.out.println("reverseStringManual: " + sol.reverseStringManual(example));
    }
}

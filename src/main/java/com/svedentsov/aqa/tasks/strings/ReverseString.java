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
        if (str.isEmpty()) {
            return ""; // Для пустой строки можно сразу вернуть пустую строку
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
}

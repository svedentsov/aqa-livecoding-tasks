package com.svedentsov.aqa.tasks.strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Решение задачи №42: Конвертация римских чисел в целые.
 * <p>
 * Описание: Написать функцию для преобразования строки с римским числом в `int`.
 * (Проверяет: работа со строками, Map, логика)
 * <p>
 * Задание: Напишите метод `int romanToInt(String s)`, который конвертирует строку `s`,
 * содержащую римское число, в целое число. (I=1, V=5, X=10, L=50, C=100, D=500, M=1000).
 * Учитывайте правила вычитания (IV=4, IX=9, XL=40, XC=90, CD=400, CM=900).
 * <p>
 * Пример: `romanToInt("III")` -> `3`. `romanToInt("LVIII")` -> `58`.
 * `romanToInt("MCMXCIV")` -> `1994`.
 */
public class RomanToInteger {

    // Карта для хранения значений римских цифр.
    // Делаем ее static final и инициализируем в статическом блоке.
    private static final Map<Character, Integer> ROMAN_MAP;

    static {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        ROMAN_MAP = Collections.unmodifiableMap(map); // Делаем карту неизменяемой
    }

    /**
     * Конвертирует строку с римским числом в целое число.
     * Учитывает правила сложения и вычитания (например, IV = 4, VI = 6).
     * Обрабатывает строку справа налево для упрощения логики вычитания.
     * <p>
     * Алгоритм:
     * 1. Инициализировать `result = 0` и `prevValue = 0`.
     * 2. Итерировать по строке `s` справа налево (от `s.length() - 1` до `0`).
     * 3. Для каждого символа `currentChar`:
     * a. Получить его числовое значение `currentValue` из `ROMAN_MAP`.
     * b. Если символ невалидный (нет в карте), выбросить исключение.
     * c. Если `currentValue < prevValue` (случай вычитания, например 'I' в "IV"),
     * вычесть `currentValue` из `result`.
     * d. Иначе (если `currentValue >= prevValue`), прибавить `currentValue` к `result`.
     * e. Обновить `prevValue = currentValue` для следующей итерации.
     * 4. Вернуть `result`.
     *
     * @param s Строка с римским числом. Предполагается валидной согласно стандартным правилам.
     * @return Целочисленное представление римского числа.
     * @throws IllegalArgumentException если строка содержит символы, не являющиеся римскими цифрами.
     * @throws NullPointerException     если строка s равна null.
     */
    public int romanToInt(String s) {
        Objects.requireNonNull(s, "Input string cannot be null");
        if (s.isEmpty()) {
            return 0; // Пустая строка
        }

        int result = 0;
        int prevValue = 0; // Значение предыдущего (правого) символа

        for (int i = s.length() - 1; i >= 0; i--) {
            char currentChar = s.charAt(i);
            Integer currentValue = ROMAN_MAP.get(currentChar);

            if (currentValue == null) {
                throw new IllegalArgumentException("Invalid character '" + currentChar + "' in Roman numeral string.");
            }

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue; // Сохраняем текущее значение для следующей итерации
        }

        return result;
    }

    /**
     * Точка входа для демонстрации работы метода конвертации римских чисел в целые.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        RomanToInteger sol = new RomanToInteger();
        String[] romanNumerals = {"III", "LVIII", "MCMXCIV", "IX", "IV", "XL", "XC", "CD", "CM", "I", "MMXXV", "", "MMMDCCCLXXXVIII", "V", "X"};
        int[] expectedInts = {3, 58, 1994, 9, 4, 40, 90, 400, 900, 1, 2025, 0, 3888, 5, 10};

        System.out.println("--- Converting Roman to Integer ---");
        for (int i = 0; i < romanNumerals.length; i++) {
            runRomanToIntTest(sol, romanNumerals[i], expectedInts[i]);
        }

        // Тесты на ошибки
        runRomanToIntTest(sol, "MCMA", -1); // Ожидается ошибка
        runRomanToIntTest(sol, null, -1); // Ожидается ошибка
        runRomanToIntTest(sol, "IIII", 4); // Не каноническая запись, но метод обработает как 4
        runRomanToIntTest(sol, "VX", 5);   // Не каноническая запись, но метод обработает как 5 (5-10 не бывает)
    }

    /**
     * Вспомогательный метод для тестирования romanToInt.
     *
     * @param sol      Экземпляр решателя.
     * @param roman    Входное римское число (строка).
     * @param expected Ожидаемое целое число (-1 для ожидания ошибки).
     */
    private static void runRomanToIntTest(RomanToInteger sol, String roman, int expected) {
        String input = (roman == null ? "null" : "'" + roman + "'");
        System.out.print("romanToInt(" + input + ") -> ");
        try {
            int result = sol.romanToInt(roman);
            boolean match = (result == expected);
            System.out.printf("%d (Expected: %d) %s%n",
                    result, expected, (match ? "" : "<- MISMATCH!"));
        } catch (IllegalArgumentException | NullPointerException e) {
            if (expected == -1) { // Если ожидалась ошибка
                System.out.println("Caught expected error: " + e.getClass().getSimpleName());
            } else {
                System.err.printf("Caught unexpected error: %s (Expected: %d)%n", e.getMessage(), expected);
            }
        } catch (Exception e) {
            System.err.printf("Caught unexpected error: %s%n", e.getMessage());
        }
    }
}

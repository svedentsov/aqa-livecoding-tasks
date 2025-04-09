package com.svedentsov.aqa.tasks.strings;

import java.util.Map;
import java.util.Objects;

/**
 * Решение задачи №42: Конвертация римских чисел в целые.
 */
public class Task42_RomanToInteger {

    // Статическая карта для хранения значений римских цифр
    // Map.of создает неизменяемую карту (с Java 9+)
    private static final Map<Character, Integer> ROMAN_MAP = Map.of(
            'I', 1,
            'V', 5,
            'X', 10,
            'L', 50,
            'C', 100,
            'D', 500,
            'M', 1000
    );

    /**
     * Конвертирует строку с римским числом в целое число.
     * Учитывает правила сложения и вычитания (например, IV = 4, VI = 6).
     * Обрабатывает строку справа налево для упрощения логики вычитания.
     *
     * @param s Строка с римским числом. Предполагается валидной согласно стандартным правилам
     *          (например, символы M, C, X, I не повторяются более 3 раз подряд, V, L, D не повторяются,
     *          вычитание подчиняется правилам и т.д.).
     * @return Целочисленное представление римского числа.
     * @throws IllegalArgumentException если строка содержит символы, не являющиеся римскими цифрами.
     * @throws NullPointerException     если строка s равна null.
     */
    public int romanToInt(String s) {
        Objects.requireNonNull(s, "Input string cannot be null");
        if (s.isEmpty()) {
            return 0; // Пустая строка соответствует 0
        }

        int result = 0;
        int prevValue = 0; // Значение предыдущего символа (справа)

        // Итерация по строке справа налево
        for (int i = s.length() - 1; i >= 0; i--) {
            char currentChar = s.charAt(i);
            // Получаем значение текущей римской цифры
            Integer currentValue = ROMAN_MAP.get(currentChar);

            // Если символ не найден в карте, он невалидный
            if (currentValue == null) {
                throw new IllegalArgumentException("Invalid character in Roman numeral string: '" + currentChar + "'");
            }

            // Логика вычитания/сложения:
            // Если значение текущей цифры МЕНЬШЕ значения следующей (справа, prevValue),
            // то мы должны вычесть текущее значение (например, 'I' в "IV").
            if (currentValue < prevValue) {
                result -= currentValue;
            }
            // Иначе (если текущее значение БОЛЬШЕ или РАВНО предыдущему),
            // мы прибавляем текущее значение (например, 'V' и 'I' в "VI").
            else {
                result += currentValue;
            }

            // Обновляем prevValue для следующей итерации
            prevValue = currentValue;
        }

        // Здесь можно добавить валидацию результата, если строка могла быть невалидной
        // (например, "IIII" или "VX"), но по условиям LeetCode строка обычно валидна.

        return result;
    }

    /**
     * Точка входа для демонстрации работы метода конвертации римских чисел в целые.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task42_RomanToInteger sol = new Task42_RomanToInteger();
        String[] romanNumerals = {"III", "LVIII", "MCMXCIV", "IX", "IV", "XL", "XC", "CD", "CM", "I", "MMXXV", ""};
        int[] expectedInts = {3, 58, 1994, 9, 4, 40, 90, 400, 900, 1, 2025, 0};

        for (int i = 0; i < romanNumerals.length; i++) {
            try {
                int result = sol.romanToInt(romanNumerals[i]);
                System.out.println("'" + romanNumerals[i] + "' -> " + result + " (Expected: " + expectedInts[i] + ")");
                if (result != expectedInts[i]) {
                    System.err.println("   Mismatch!");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Error processing '" + romanNumerals[i] + "': " + e.getMessage());
            }
        }

        // Пример с невалидным символом
        try {
            System.out.print("\nProcessing 'MCMA': ");
            sol.romanToInt("MCMA");
        } catch (IllegalArgumentException e) {
            System.err.println("Caught expected error: " + e.getMessage());
        }
        // Пример с null
        try {
            System.out.print("Processing null: ");
            sol.romanToInt(null);
        } catch (NullPointerException e) { // Ожидается NPE из-за Objects.requireNonNull
            System.err.println("Caught expected error: " + e.getMessage());
        }
    }
}

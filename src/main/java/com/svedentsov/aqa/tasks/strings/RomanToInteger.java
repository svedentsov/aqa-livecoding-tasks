package com.svedentsov.aqa.tasks.strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Решение задачи №42: Конвертация римских чисел в целые.
 * Описание: Написать функцию для преобразования строки с римским числом в `int`.
 * (Проверяет: работа со строками, Map, логика)
 * Задание: Напишите метод `int romanToInt(String s)`, который конвертирует строку `s`,
 * содержащую римское число, в целое число. (I=1, V=5, X=10, L=50, C=100, D=500, M=1000).
 * Учитывайте правила вычитания (IV=4, IX=9, XL=40, XC=90, CD=400, CM=900).
 * Пример: `romanToInt("III")` -> `3`. `romanToInt("LVIII")` -> `58`.
 * `romanToInt("MCMXCIV")` -> `1994`.
 */
public class RomanToInteger {

    // Карта для хранения значений римских цифр.
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
     *
     * @param s Строка с римским числом. Предполагается, что строка состоит из валидных
     *          римских символов, но может быть не канонически сформирована.
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
}

package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №20: Проверить, содержит ли строка только цифры.
 * <p>
 * Описание: Написать функцию для такой проверки.
 * (Проверяет: работа со строками, циклы/регулярные выражения)
 * <p>
 * Задание: Напишите метод `boolean containsOnlyDigits(String str)`, который
 * возвращает `true`, если строка `str` состоит только из цифр (0-9),
 * и `false` в противном случае. Пустая строка или `null` должны возвращать `false`.
 * <p>
 * Пример: `containsOnlyDigits("12345")` -> `true`,
 * `containsOnlyDigits("12a45")` -> `false`,
 * `containsOnlyDigits("")` -> `false`.
 */
public class CheckStringOnlyDigits {

    /**
     * Проверяет, состоит ли строка только из ASCII цифр ('0'-'9') итеративно.
     *
     * @param str Строка для проверки. Может быть null.
     * @return {@code true}, если строка не null, не пуста и каждый её символ
     * находится в диапазоне от '0' до '9', {@code false} в противном случае.
     */
    public boolean containsOnlyDigitsManual(String str) {
        // Шаг 1: Проверка на null или пустоту
        if (str == null || str.isEmpty()) {
            return false;
        }
        // Шаг 2: Проход по символам
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Шаг 3: Строгая проверка на ASCII '0'-'9'
            if (c < '0' || c > '9') {
                return false;
            }
            // Альтернатива для поддержки Unicode цифр:
            // if (!Character.isDigit(c)) {
            //     return false;
            // }
        }
        // Шаг 4: Если цикл завершился, все символы - ASCII цифры
        return true;
    }

    /**
     * Проверяет, состоит ли строка только из цифр ('0'-'9') с помощью регулярного выражения.
     * Использует {@code String.matches()}. В стандартной реализации Java \d соответствует [0-9].
     *
     * @param str Строка для проверки. Может быть null.
     * @return {@code true}, если строка не null, не пуста и полностью соответствует
     * регулярному выражению {@code ^\d+$}, {@code false} в противном случае.
     */
    public boolean containsOnlyDigitsRegex(String str) {
        // Шаг 1: Проверка на null или пустоту
        if (str == null || str.isEmpty()) {
            return false;
        }
        // Шаг 2: Применение регулярного выражения
        // ^\d+$ : строка должна состоять из одной или более цифр от начала до конца.
        return str.matches("^\\d+$");
    }
}

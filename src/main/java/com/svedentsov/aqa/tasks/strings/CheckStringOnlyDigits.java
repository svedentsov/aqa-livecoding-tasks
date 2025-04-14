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
     * Проверяет, состоит ли строка только из цифр ('0'-'9') итеративно.
     * Использует {@link Character#isDigit(char)} для проверки каждого символа.
     * Этот метод корректно обрабатывает разные представления цифр в Unicode,
     * если это требуется. Для строгой проверки только на ASCII '0'-'9',
     * можно использовать сравнение `c >= '0' && c <= '9'`.
     *
     * @param str Строка для проверки. Может быть null.
     * @return {@code true}, если строка не null, не пуста и каждый её символ
     * является цифрой (согласно Character.isDigit), {@code false} в противном случае.
     */
    public boolean containsOnlyDigitsManual(String str) {
        // Шаг 1: Проверка на null или пустоту
        if (str == null || str.isEmpty()) {
            return false;
        }
        // Шаг 2: Проход по символам
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // Шаг 3: Проверка, является ли символ цифрой
            if (!Character.isDigit(c)) {
                // Если хотя бы один символ не цифра, возвращаем false
                return false;
            }
            // Альтернатива для строгой проверки ASCII '0'-'9':
            // if (c < '0' || c > '9') {
            //     return false;
            // }
        }
        // Шаг 4: Если цикл завершился, все символы - цифры
        return true;
    }

    /**
     * Проверяет, состоит ли строка только из цифр ('0'-'9') с помощью регулярного выражения.
     * Использует {@code String.matches()}.
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
        // ^     - якорь начала строки
        // \d    - соответствует любой цифре (эквивалент [0-9])
        // +     - квантификатор "один или более раз"
        // $     - якорь конца строки
        // Таким образом, выражение означает: строка должна состоять из одной или более цифр от начала до конца.
        return str.matches("^\\d+$");
        // Альтернатива без anchors (проверяет, содержит ли строка ХОТЯ БЫ одну цифру):
        // return str.matches(".*\\d.*"); // Не подходит по заданию
    }

    // --- Точка входа и Тестирование ---

    /**
     * Вспомогательный метод для тестирования проверки на цифры.
     *
     * @param sol         Экземпляр решателя.
     * @param str         Тестовая строка.
     * @param description Описание теста.
     */
    private static void runDigitsTest(CheckStringOnlyDigits sol, String str, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (str == null ? "null" : "\"" + str + "\"");
        System.out.println("Input string: " + input);
        try {
            System.out.println("Manual check: " + sol.containsOnlyDigitsManual(str));
        } catch (Exception e) {
            System.out.println("Manual check: Error - " + e.getMessage());
        }
        try {
            System.out.println("Regex check:  " + sol.containsOnlyDigitsRegex(str));
        } catch (Exception e) {
            System.out.println("Regex check:  Error - " + e.getMessage());
        }
    }

    /**
     * Точка входа для демонстрации работы методов проверки строки на цифры.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        CheckStringOnlyDigits sol = new CheckStringOnlyDigits();

        runDigitsTest(sol, "12345", "Только цифры"); // true / true
        runDigitsTest(sol, "12a45", "С буквой"); // false / false
        runDigitsTest(sol, "12 45", "С пробелом"); // false / false
        runDigitsTest(sol, "-123", "С минусом"); // false / false
        runDigitsTest(sol, "+123", "С плюсом"); // false / false
        runDigitsTest(sol, "", "Пустая строка"); // false / false
        runDigitsTest(sol, null, "Null строка"); // false / false
        runDigitsTest(sol, "0", "Только ноль"); // true / true
        runDigitsTest(sol, "9876543210", "Длинная строка цифр"); // true / true
        runDigitsTest(sol, " 123 ", "С ведущими/завершающими пробелами"); // false / false
        runDigitsTest(sol, " ", "Только пробел"); // false / false
        runDigitsTest(sol, "1.23", "С точкой"); // false / false
        // Пример с Unicode цифрами (зависит от реализации Character.isDigit)
        // Восточно-арабские цифры ١٢٣ (123)
        runDigitsTest(sol, "١٢٣", "Unicode цифры (Eastern Arabic)"); // true / false (regex \d обычно не включает их)

    }
}

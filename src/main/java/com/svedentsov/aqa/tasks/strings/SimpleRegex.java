package com.svedentsov.aqa.tasks.strings;

import java.util.regex.Pattern;

/**
 * Решение задачи №80: Регулярное выражение для поиска/валидации.
 * Описание: Написать простое регулярное выражение (например, для поиска
 * телефонного номера). (Проверяет: основы регулярных выражений)
 * Задание: Напишите метод `boolean isValidPhoneNumber(String number)`, который
 * использует регулярное выражение для проверки, соответствует ли строка `number`
 * простому формату телефонного номера, например, `+7-XXX-XXX-XX-XX`
 * (где X - цифра).
 * Пример: `isValidPhoneNumber("+7-911-123-45-67")` -> `true`.
 * `isValidPhoneNumber("89111234567")` -> `false`.
 */
public class SimpleRegex {

    // Регулярное выражение для формата +7-XXX-XXX-XX-XX
    // ^       : Начало строки
    // \+      : Буквальный символ '+'
    // 7       : Цифра '7'
    // -       : Буквальный дефис
    // \d{3}   : Ровно 3 цифры (эквивалентно [0-9]{3})
    // -       : Буквальный дефис
    // \d{3}   : Ровно 3 цифры
    // -       : Буквальный дефис
    // \d{2}   : Ровно 2 цифры
    // -       : Буквальный дефис
    // \d{2}   : Ровно 2 цифры
    // $       : Конец строки
    // Pattern потокобезопасен и может быть скомпилирован один раз
    private static final Pattern PHONE_PATTERN_RU_MOBILE =
            Pattern.compile("^\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}$");

    /**
     * Проверяет, соответствует ли строка {@code number} формату
     * российского мобильного номера +7-XXX-XXX-XX-XX с использованием
     * регулярного выражения.
     *
     * @param number Строка для проверки. Может быть null.
     * @return {@code true}, если строка не null и соответствует формату,
     * {@code false} в противном случае.
     */
    public boolean isValidPhoneNumber(String number) {
        if (number == null) {
            return false;
        }
        // Используем Matcher для проверки соответствия строки шаблону
        return PHONE_PATTERN_RU_MOBILE.matcher(number).matches();
    }

    /**
     * Точка входа для демонстрации валидации телефонного номера.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        SimpleRegex sol = new SimpleRegex();
        String[] numbers = {
                "+7-911-123-45-67",  // true
                "+7-000-000-00-00",  // true
                "89111234567",       // false (неверный префикс, нет дефисов)
                "+7-91-123-45-67",   // false (не 3 цифры в коде оператора)
                "+7-911-123-45-6",   // false (не 2 цифры в конце)
                "+7-911-123-45-678", // false (лишняя цифра в конце)
                "+7-911-123-45-6A",  // false (буква вместо цифры)
                " +7-911-123-45-67", // false (пробел в начале)
                "+7-911-123-45-67 ", // false (пробел в конце)
                "+79111234567",      // false (нет дефисов)
                "+8-911-123-45-67",  // false (неверный код страны)
                "abc",               // false
                "",                  // false
                null                 // false
        };

        System.out.println("--- Simple Phone Number Regex Validation (+7-XXX-XXX-XX-XX) ---");
        for (String number : numbers) {
            runPhoneValidationTest(sol, number);
        }
    }

    /**
     * Вспомогательный метод для тестирования isValidPhoneNumber.
     *
     * @param sol    Экземпляр решателя.
     * @param number Номер телефона для проверки.
     */
    private static void runPhoneValidationTest(SimpleRegex sol, String number) {
        String input = (number == null ? "null" : "'" + number + "'");
        try {
            boolean result = sol.isValidPhoneNumber(number);
            // Ожидаемый результат можно вычислить здесь или передать
            // boolean expected = calculateExpected(number); // Например
            System.out.printf("isValidPhoneNumber(%s) -> %b%n", input, result);
            // if (result != expected) System.err.println("   Mismatch!");
        } catch (Exception e) {
            System.err.printf("Error processing %s: %s%n", input, e.getMessage());
        }
    }
}

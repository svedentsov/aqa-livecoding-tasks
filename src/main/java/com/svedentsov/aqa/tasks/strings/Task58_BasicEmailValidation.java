package com.svedentsov.aqa.tasks.strings;

import java.util.regex.Pattern;

/**
 * Решение задачи №58: Базовая валидация формата Email.
 */
public class Task58_BasicEmailValidation {

    /**
     * Выполняет очень базовую проверку формата email-адреса с использованием {@code indexOf}.
     * Проверяет:
     * 1. Строка не null и не пуста.
     * 2. Содержит ровно один символ '@'.
     * 3. Символ '@' не является первым или последним.
     * 4. Содержит хотя бы одну точку '.' после символа '@'.
     * 5. Точка '.' не идет сразу после '@'.
     * 6. Точка '.' не является последним символом.
     * **Предупреждение:** Этот метод НЕ ЯВЛЯЕТСЯ надежным валидатором email
     * для реальных приложений, так как он пропускает множество невалидных адресов
     * и может отвергнуть некоторые валидные. Используйте его только для демонстрации
     * базовой логики проверки строк.
     *
     * @param email Строка для проверки. Может быть null.
     * @return {@code true}, если строка проходит базовую проверку, {@code false} в противном случае.
     */
    public boolean isValidEmailBasicIndexOf(String email) {
        // 1. Проверка на null и пустоту
        if (email == null || email.isEmpty()) {
            return false;
        }

        // 2. Проверка наличия ровно одного символа '@'
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || email.lastIndexOf('@') != atIndex) {
            return false; // Нет '@' или их больше одной
        }

        // 3. Проверка, что '@' не первый и не последний символ
        if (atIndex == 0 || atIndex == email.length() - 1) {
            return false;
        }

        // 4. Проверка наличия точки '.' после '@'
        int dotIndex = email.indexOf('.', atIndex + 1);
        if (dotIndex == -1) {
            return false; // Нет точки после '@'
        }

        // 5. Проверка, что точка не идет сразу после '@'
        if (dotIndex == atIndex + 1) {
            return false; // Случай "user@.domain"
        }

        // 6. Проверка, что точка не является последним символом
        if (dotIndex == email.length() - 1) {
            return false; // Случай "user@domain."
        }

        // Если все примитивные проверки пройдены
        return true;
    }

    /**
     * Валидация с использованием простого регулярного выражения.
     * Этот шаблон немного лучше, чем просто indexOf, но все еще не соответствует
     * полностью стандартам RFC 5322 и может ошибаться.
     *
     * @param email Строка для проверки.
     * @return {@code true}, если строка похожа на email согласно простому regex, {@code false} иначе.
     */
    // Простой шаблон: [символы]@[символы].[2+ буквы]
    private static final Pattern SIMPLE_EMAIL_REGEX = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    public boolean isValidEmailBasicRegex(String email) {
        if (email == null) {
            return false;
        }
        // trim() убирает пробелы по краям перед проверкой
        return SIMPLE_EMAIL_REGEX.matcher(email.trim()).matches();
    }

    /**
     * Точка входа для демонстрации работы методов базовой валидации email.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task58_BasicEmailValidation sol = new Task58_BasicEmailValidation();
        String[] emails = {
                "test@example.com",        // true / true
                "user.name+tag@domain.co.uk",// true / true
                "test.example.com",       // false / false (нет @)
                "test@examplecom",        // false / false (нет точки в домене для regex)
                "test@.com",              // false / false
                "@example.com",           // false / false
                "test@",                  // false / false
                "test@example.",          // false / false
                "test@@example.com",      // false / false
                " test@example.com ",     // false / true (regex с trim сработает)
                "user@localhost",         // false / false (нет точки в домене для regex)
                "a@b.c",                  // false / false (домен < 2 букв для regex)
                "a@b.cde",                // true / true
                null,                     // false / false
                ""                        // false / false
        };

        System.out.println("--- Basic Check (indexOf) ---");
        for (String email : emails) {
            System.out.println("'" + email + "' -> " + sol.isValidEmailBasicIndexOf(email));
        }

        System.out.println("\n--- Basic Regex Check ---");
        for (String email : emails) {
            System.out.println("'" + email + "' -> " + sol.isValidEmailBasicRegex(email));
        }
        System.out.println("\nПримечание: Для надежной валидации email рекомендуется использовать специализированные библиотеки или более полные регулярные выражения.");
    }
}

package com.svedentsov.aqa.tasks.strings;

import java.util.regex.Pattern;

/**
 * Решение задачи №58: Базовая валидация формата Email.
 * <p>
 * Описание: Очень упрощенная проверка наличия `@` и `.`.
 * (Проверяет: работа со строками, `contains`/`indexOf`)
 * <p>
 * Задание: Напишите метод `boolean isValidEmailBasic(String email)`, который
 * выполняет очень простую проверку формата email: строка должна содержать один
 * символ `@` и хотя бы одну точку `.` после символа `@`.
 * <p>
 * Пример: `isValidEmailBasic("test@example.com")` -> `true`.
 * `isValidEmailBasic("test.example.com")` -> `false`.
 * `isValidEmailBasic("test@examplecom")` -> `false`.
 * `isValidEmailBasic("test@@example.com")` -> `false`.
 */
public class BasicEmailValidation {

    /**
     * Выполняет очень базовую проверку формата email-адреса с использованием `indexOf`.
     * Проверяет основные структурные элементы, но не гарантирует валидность по RFC.
     * <p>
     * Проверки:
     * 1. Не null и не пустая строка.
     * 2. Ровно один символ '@'.
     * 3. '@' не первый и не последний символ.
     * 4. Есть точка '.' после '@'.
     * 5. Точка '.' не идет сразу после '@'.
     * 6. Точка '.' не последний символ.
     *
     * @param email Строка для проверки. Может быть null.
     * @return {@code true}, если строка проходит базовую проверку, {@code false} в противном случае.
     */
    public boolean isValidEmailBasicIndexOf(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf('@');

        // Проверка наличия и единственности '@'
        if (atIndex == -1 || email.lastIndexOf('@') != atIndex) {
            return false;
        }

        // Проверка положения '@'
        if (atIndex == 0 || atIndex == email.length() - 1) {
            return false;
        }

        // Проверка наличия точки после '@'
        int dotIndexAfterAt = email.indexOf('.', atIndex + 1);
        if (dotIndexAfterAt == -1) {
            return false;
        }

        // Проверка, что точка не сразу после '@'
        if (dotIndexAfterAt == atIndex + 1) {
            return false;
        }

        // Проверка, что точка не последний символ
        if (dotIndexAfterAt == email.length() - 1) {
            return false;
        }

        return true;
    }

    // --- Валидация с использованием простого Regex ---

    // Простой шаблон: [некоторые символы]@[некоторые символы].[2+ буквы на конце]
    // Этот шаблон лучше, чем indexOf, но все еще далек от полноты RFC.
    private static final Pattern SIMPLE_EMAIL_REGEX = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    /**
     * Валидация с использованием простого регулярного выражения.
     * Убирает пробелы по краям перед проверкой.
     *
     * @param email Строка для проверки.
     * @return {@code true}, если строка похожа на email согласно простому regex, {@code false} иначе.
     */
    public boolean isValidEmailBasicRegex(String email) {
        if (email == null) {
            return false;
        }
        return SIMPLE_EMAIL_REGEX.matcher(email.trim()).matches();
    }


    /**
     * Точка входа для демонстрации работы методов базовой валидации email.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        BasicEmailValidation sol = new BasicEmailValidation();
        String[] emails = {
                "test@example.com",        // true / true
                "user.name+tag@domain.co.uk",// true / true
                "test.example.com",       // false (no @) / false
                "test@examplecom",        // false (no dot after @) / false (no TLD)
                "test@.com",              // false (dot right after @) / false
                "@example.com",           // false (@ at start) / false
                "test@",                  // false (@ at end) / false
                "test@example.",          // false (dot at end) / false
                "test@@example.com",      // false (multiple @) / false
                " test@example.com ",     // false (spaces) / true (regex trims)
                "user@localhost",         // false (no dot after @) / false (no TLD)
                "a@b.c",                  // false (dot at end) / false (TLD < 2 chars)
                "a@b.cde",                // true / true
                null,                     // false / false
                "",                       // false / false
                "user@d-.com",            // true / true (hyphen ok in domain part)
                "user@.d.com"             // false / false
        };

        System.out.println("--- Basic Email Validation ---");
        for (String email : emails) {
            runEmailTest(sol, email);
        }
        System.out.println("\nNote: These are basic checks. For robust validation, use libraries or comprehensive regex.");
    }

    /**
     * Вспомогательный метод для тестирования валидации email.
     *
     * @param sol   Экземпляр решателя.
     * @param email Email для проверки.
     */
    private static void runEmailTest(BasicEmailValidation sol, String email) {
        String input = (email == null ? "null" : "'" + email + "'");
        System.out.println("\nInput: " + input);
        try {
            boolean resultIndexOf = sol.isValidEmailBasicIndexOf(email);
            System.out.println("  Result (indexOf): " + resultIndexOf);
        } catch (Exception e) {
            System.err.println("  Result (indexOf): Error - " + e.getMessage());
        }
        try {
            boolean resultRegex = sol.isValidEmailBasicRegex(email);
            System.out.println("  Result (Regex)  : " + resultRegex);
        } catch (Exception e) {
            System.err.println("  Result (Regex)  : Error - " + e.getMessage());
        }
    }
}

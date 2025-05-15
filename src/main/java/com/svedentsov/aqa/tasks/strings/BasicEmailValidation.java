package com.svedentsov.aqa.tasks.strings;

import java.util.regex.Pattern;

/**
 * Решение задачи №58: Базовая валидация формата Email.
 * Описание: Очень упрощенная проверка наличия `@` и `.`.
 * (Проверяет: работа со строками, `contains`/`indexOf`)
 * Задание: Напишите метод `boolean isValidEmailBasic(String email)`, который
 * выполняет очень простую проверку формата email: строка должна содержать один
 * символ `@` и хотя бы одну точку `.` после символа `@`.
 * Пример: `isValidEmailBasic("test@example.com")` -> `true`.
 * `isValidEmailBasic("test.example.com")` -> `false`.
 * `isValidEmailBasic("test@examplecom")` -> `false`.
 * `isValidEmailBasic("test@@example.com")` -> `false`.
 */
public class BasicEmailValidation {

    // Новый упрощённый regex: хотя бы одна метка + точка, TLD ≥2, без пустых меток
    private static final Pattern SIMPLE_EMAIL_REGEX = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$"
    );

    /**
     * Базовая проверка через indexOf со строгими правилами:
     * - ровно один '@'
     * - точка после '@', не сразу и не в конце
     * - минимум 2 символа после точки (TLD ≥2)
     * - никаких пробелов
     */
    public boolean isValidEmailBasicIndexOf(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Запрещаем любые пробелы
        if (email.contains(" ")) {
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
        // Точка не должна быть последним символом
        if (dotIndexAfterAt == email.length() - 1) {
            return false;
        }
        // После первой точки в домене минимум 2 символа (TLD ≥2)
        if (email.length() - dotIndexAfterAt - 1 < 2) {
            return false;
        }

        return true;
    }

    /**
     * Валидация через простой регулярный шаблон.
     * Убираем пробелы по краям (trim), но внутри строки регекс уже не даст пройти email с пробелом.
     */
    public boolean isValidEmailBasicRegex(String email) {
        if (email == null) {
            return false;
        }
        String trimmed = email.trim();
        return SIMPLE_EMAIL_REGEX.matcher(trimmed).matches();
    }
}

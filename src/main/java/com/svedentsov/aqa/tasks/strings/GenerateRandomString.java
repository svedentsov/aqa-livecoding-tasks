package com.svedentsov.aqa.tasks.strings;

import java.security.SecureRandom;

/**
 * Решение задачи №57: Сгенерировать случайную строку заданной длины.
 * Описание: (Проверяет: работа с `Random`, `StringBuilder`/`char[]`)
 * Задание: Напишите метод `String generateRandomString(int length)`, который
 * генерирует и возвращает случайную строку, состоящую из букв (верхнего и
 * нижнего регистра) и цифр, указанной длины `length`.
 * Пример: `generateRandomString(10)` может вернуть `"aK8s2ZpQ1v"`.
 */
public class GenerateRandomString {

    // Набор допустимых символов для генерации строки
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";

    // Используем SecureRandom - предпочтительнее для генерации случайных данных
    private static final SecureRandom random = new SecureRandom();

    /**
     * Генерирует случайную строку указанной длины, состоящую из
     * латинских букв (верхнего и нижнего регистра) и цифр.
     * Использует {@link SecureRandom} для выбора случайных символов из
     * предопределенного набора {@code ALLOWED_CHARACTERS}.
     *
     * @param length Требуемая длина строки. Должна быть >= 0.
     * @return Случайная строка указанной длины. Возвращает пустую строку, если length == 0.
     * @throws IllegalArgumentException если length отрицательная.
     */
    public String generateRandomString(int length) {
        // Проверка на корректность длины
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative: " + length);
        }
        if (length == 0) {
            return ""; // Пустая строка для длины 0
        }

        // StringBuilder для эффективного построения строки
        StringBuilder sb = new StringBuilder(length);
        int alphabetLength = ALLOWED_CHARACTERS.length();

        // Генерация каждого символа
        for (int i = 0; i < length; i++) {
            // Генерируем случайный индекс в пределах длины набора символов
            int randomIndex = random.nextInt(alphabetLength);
            // Добавляем символ по этому индексу
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }
}

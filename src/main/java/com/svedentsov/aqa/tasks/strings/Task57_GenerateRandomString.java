package com.svedentsov.aqa.tasks.strings;

import java.security.SecureRandom;

/**
 * Решение задачи №57: Сгенерировать случайную строку заданной длины.
 */
public class Task57_GenerateRandomString {

    // Набор символов, из которых будет генерироваться строка
    // Можно легко модифицировать, добавив/убрав символы
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";

    // Используем SecureRandom для генерации криптографически стойких случайных чисел.
    // Создаем один экземпляр для повторного использования (создание SecureRandom может быть долгим).
    private static final SecureRandom RANDOM_GENERATOR = new SecureRandom();

    /**
     * Генерирует случайную строку указанной длины, состоящую из
     * латинских букв (верхнего и нижнего регистра) и цифр.
     * Использует {@link SecureRandom} для генерации случайных индексов.
     *
     * @param length Требуемая длина строки. Должна быть >= 0.
     * @return Случайная строка указанной длины. Возвращает пустую строку, если length <= 0.
     * @throws IllegalArgumentException если length отрицательная.
     */
    public String generateRandomString(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative: " + length);
        }
        if (length == 0) {
            return ""; // Возвращаем пустую строку для длины 0
        }

        // Используем StringBuilder для эффективного построения строки
        StringBuilder sb = new StringBuilder(length);
        int allowedCharsLength = ALLOWED_CHARACTERS.length();

        // Генерируем каждый символ строки
        for (int i = 0; i < length; i++) {
            // Получаем случайный индекс из диапазона [0, allowedCharsLength - 1]
            int randomIndex = RANDOM_GENERATOR.nextInt(allowedCharsLength);
            // Добавляем символ по этому индексу из нашего набора
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        return sb.toString();
    }

    /**
     * Точка входа для демонстрации генерации случайных строк.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task57_GenerateRandomString sol = new Task57_GenerateRandomString();

        int[] lengths = {10, 5, 20, 1, 0};

        for (int len : lengths) {
            try {
                System.out.println("Random string (length " + len + "): " + sol.generateRandomString(len));
            } catch (IllegalArgumentException e) {
                System.err.println("Error for length " + len + ": " + e.getMessage());
            }
        }

        // Тест на отрицательную длину
        try {
            sol.generateRandomString(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("\nCaught expected error for negative length: " + e.getMessage());
        }

        // Демонстрация разных результатов
        System.out.println("\nMultiple calls for length 8:");
        System.out.println("Call 1: " + sol.generateRandomString(8));
        System.out.println("Call 2: " + sol.generateRandomString(8));
        System.out.println("Call 3: " + sol.generateRandomString(8));
    }
}

package com.svedentsov.aqa.tasks.strings;

import java.security.SecureRandom;

/**
 * Решение задачи №57: Сгенерировать случайную строку заданной длины.
 * <p>
 * Описание: (Проверяет: работа с `Random`, `StringBuilder`/`char[]`)
 * <p>
 * Задание: Напишите метод `String generateRandomString(int length)`, который
 * генерирует и возвращает случайную строку, состоящую из букв (верхнего и
 * нижнего регистра) и цифр, указанной длины `length`.
 * <p>
 * Пример: `generateRandomString(10)` может вернуть `"aK8s2ZpQ1v"`.
 */
public class GenerateRandomString {

    // Набор допустимых символов для генерации строки
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";

    // Используем SecureRandom - предпочтительнее для генерации случайных данных,
    // особенно если важна непредсказуемость.
    // Создаем один экземпляр и используем его повторно.
    private static final SecureRandom random = new SecureRandom();

    // Альтернатива: обычный Random (быстрее, но менее безопасный)
    // private static final Random random = new Random();

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

    /**
     * Точка входа для демонстрации генерации случайных строк.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        GenerateRandomString sol = new GenerateRandomString();
        int[] lengths = {10, 5, 20, 1, 0, 62}; // Длина 62 = длина алфавита

        System.out.println("--- Generating Random Strings ---");
        for (int len : lengths) {
            runGenerateStringTest(sol, len);
        }

        // Тест на отрицательную длину
        runGenerateStringTest(sol, -5);

        // Демонстрация разных результатов для одной длины
        System.out.println("\n--- Multiple Calls for Length 12 ---");
        runGenerateStringTest(sol, 12);
        runGenerateStringTest(sol, 12);
        runGenerateStringTest(sol, 12);
    }

    /**
     * Вспомогательный метод для тестирования генерации строки.
     *
     * @param sol    Экземпляр решателя.
     * @param length Требуемая длина.
     */
    private static void runGenerateStringTest(GenerateRandomString sol, int length) {
        System.out.print("generateRandomString(" + length + "): ");
        try {
            String randomStr = sol.generateRandomString(length);
            System.out.println("'" + randomStr + "'");
            // Проверка длины (если не было исключения)
            if (length >= 0 && randomStr.length() != length) {
                System.err.println("   Mismatch! Actual length: " + randomStr.length());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error - " + e.getMessage());
        }
    }
}

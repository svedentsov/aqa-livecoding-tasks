package com.svedentsov.aqa.tasks.files_io_formats;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Решение задачи №77: Конвертация Base64 (Encode/Decode).
 * <p>
 * Описание: Использовать встроенные классы Java.
 * (Проверяет: знание стандартной библиотеки)
 * <p>
 * Задание: Напишите метод `String encodeBase64(String input)` и
 * `String decodeBase64(String encodedInput)`, используя класс `java.util.Base64`,
 * для кодирования строки в Base64 и декодирования обратно.
 * <p>
 * Пример: `encodeBase64("hello")` -> `"aGVsbG8="`.
 * `decodeBase64("aGVsbG8=")` -> `"hello"`.
 */
public class Base64 {

    /**
     * Кодирует входную строку в формат Base64.
     * Использует стандартный кодировщик Base64 из {@code java.util.Base64}.
     * Строка преобразуется в байты с использованием кодировки UTF-8.
     *
     * @param input Исходная строка для кодирования. Может быть null.
     * @return Строка в формате Base64 или null, если входная строка была null.
     */
    public String encodeBase64(String input) {
        if (input == null) {
            return null;
        }
        // Преобразуем строку в байты (UTF-8)
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        // Получаем кодировщик и кодируем байты в строку Base64
        return java.util.Base64.getEncoder().encodeToString(inputBytes);
    }

    /**
     * Декодирует строку из формата Base64 обратно в исходную строку.
     * Использует стандартный декодировщик Base64 из {@code java.util.Base64}.
     * Результат декодирования (массив байт) преобразуется в строку с использованием UTF-8.
     *
     * @param encodedInput Строка в формате Base64 для декодирования. Может быть null.
     * @return Исходная декодированная строка или null, если входная строка была null
     * или не является валидной строкой Base64.
     * @throws IllegalArgumentException если encodedInput содержит невалидные символы Base64.
     */
    public String decodeBase64(String encodedInput) throws IllegalArgumentException {
        if (encodedInput == null) {
            return null;
        }
        // Получаем декодировщик
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        // Декодируем строку в байты (может выбросить IllegalArgumentException)
        byte[] decodedBytes = decoder.decode(encodedInput);
        // Преобразуем байты в строку (UTF-8)
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Точка входа для демонстрации работы методов кодирования/декодирования Base64.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Base64 sol = new Base64();
        String[] originals = {
                "hello world",
                "Привет мир!", // Кириллица
                "1234567890",
                "~!@#$%^&*()_+=-`{}[]|\\:;\"'<>,.?/", // Спецсимволы
                "Эта строка содержит\nперенос строки.", // Перенос строки
                "", // Пустая строка
                "a", // Короткая строка 1
                "ab", // Короткая строка 2
                "abc", // Короткая строка 3
                null // Null строка
        };

        System.out.println("--- Base64 Encode/Decode Demonstration ---");
        for (String original : originals) {
            runBase64Test(sol, original);
        }

        // Пример с невалидной строкой Base64
        System.out.println("\n--- Invalid Input Test ---");
        runBase64Test(sol, "aGVsbG8g.d29ybGQh"); // Содержит '.'
        runBase64Test(sol, "aGVsbG8gd29ybGQh==="); // Неправильный padding
    }

    /**
     * Вспомогательный метод для тестирования encode/decode Base64.
     *
     * @param sol      Экземпляр решателя.
     * @param original Исходная строка.
     */
    private static void runBase64Test(Base64 sol, String original) {
        String origStr = (original == null ? "null" : "'" + original + "'");
        System.out.println("\nOriginal: " + origStr);
        String encoded = null;
        String decoded = null;
        boolean matches = false;

        try {
            // Кодирование
            encoded = sol.encodeBase64(original);
            String encodedStr = (encoded == null ? "null" : "'" + encoded + "'");
            System.out.println("Encoded:  " + encodedStr);

            // Декодирование
            decoded = sol.decodeBase64(encoded);
            String decodedStr = (decoded == null ? "null" : "'" + decoded + "'");
            System.out.println("Decoded:  " + decodedStr);

            // Сравнение
            matches = Objects.equals(original, decoded);
            System.out.println("Matches original? " + matches);

        } catch (IllegalArgumentException e) {
            System.err.println("Error during decoding: " + e.getMessage());
            // Если ошибка произошла при декодировании строки, которую мы сами не кодировали
            // (например, невалидный тестовый пример), это ожидаемо.
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

package com.svedentsov.aqa.tasks.files_io_formats;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * Решение задачи №77: Конвертация Base64 (Encode/Decode).
 */
public class Task77_Base64 {

    /**
     * Кодирует входную строку в формат Base64.
     * Использует стандартный кодировщик Base64 из {@code java.util.Base64}.
     * Строка преобразуется в байты с использованием кодировки UTF-8,
     * которая является стандартной для веба и текстовых данных.
     *
     * @param input Исходная строка для кодирования. Может быть null.
     * @return Строка в формате Base64 или null, если входная строка была null.
     */
    public String encodeBase64(String input) {
        if (input == null) {
            return null;
        }
        // 1. Получаем байты строки в кодировке UTF-8
        byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
        // 2. Получаем стандартный кодировщик Base64
        Base64.Encoder encoder = Base64.getEncoder();
        // 3. Кодируем массив байт в строку Base64
        return encoder.encodeToString(inputBytes);
    }

    /**
     * Декодирует строку из формата Base64 обратно в исходную строку.
     * Использует стандартный декодировщик Base64 из {@code java.util.Base64}.
     * Результат декодирования (массив байт) преобразуется в строку с использованием UTF-8.
     *
     * @param encodedInput Строка в формате Base64 для декодирования. Может быть null.
     * @return Исходная декодированная строка или null, если входная строка была null
     * или если произошла ошибка декодирования (невалидный формат Base64).
     * Сообщения об ошибках выводятся в System.err.
     */
    public String decodeBase64(String encodedInput) {
        if (encodedInput == null) {
            return null;
        }
        try {
            // 1. Получаем стандартный декодировщик Base64
            Base64.Decoder decoder = Base64.getDecoder();
            // 2. Декодируем строку Base64 в массив байт
            byte[] decodedBytes = decoder.decode(encodedInput);
            // 3. Преобразуем массив байт обратно в строку, используя ту же кодировку UTF-8
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            // Исключение IllegalArgumentException выбрасывается декодером,
            // если входная строка не является валидной строкой в кодировке Base64.
            System.err.println("Ошибка декодирования Base64: невалидный формат входной строки. " + e.getMessage());
            // Возвращаем null в случае ошибки
            return null;
        }
    }

    /**
     * Точка входа для демонстрации работы методов кодирования/декодирования Base64.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task77_Base64 sol = new Task77_Base64();
        String[] originals = {
                "hello world",
                "Привет мир!", // Кириллица
                "1234567890",
                "~!@#$%^&*()_+=-`{}[]|\\:;\"'<>,.?/", // Спецсимволы
                "Эта строка содержит\nперенос строки.", // Перенос строки
                "", // Пустая строка
                null // Null строка
        };

        for (String original : originals) {
            System.out.println("\nOriginal:  '" + original + "'");
            String encoded = sol.encodeBase64(original);
            System.out.println("Encoded:   '" + encoded + "'");
            // Декодируем обратно для проверки
            String decoded = sol.decodeBase64(encoded);
            System.out.println("Decoded:   '" + decoded + "'");
            // Сравниваем оригинал и декодированный результат
            System.out.println("Matches:   " + Objects.equals(original, decoded));
        }

        // Пример с невалидной строкой Base64
        System.out.println("\n--- Invalid Input Test ---");
        String invalidBase64_1 = "aGVsbG8g.d29ybGQh"; // Содержит невалидный символ '.'
        String invalidBase64_2 = "aGVsbG8gd29ybGQh==="; // Неправильный padding

        System.out.println("Decoding '" + invalidBase64_1 + "': " + sol.decodeBase64(invalidBase64_1)); // Ожидается null и ошибка в stderr
        System.out.println("Decoding '" + invalidBase64_2 + "': " + sol.decodeBase64(invalidBase64_2)); // Ожидается null и ошибка в stderr
    }
}

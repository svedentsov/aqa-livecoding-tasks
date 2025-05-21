package com.svedentsov.aqa.tasks.files_io_formats;

import java.nio.charset.StandardCharsets;

/**
 * Решение задачи №77: Конвертация Base64 (Encode/Decode).
 * Описание: Использовать встроенные классы Java.
 * (Проверяет: знание стандартной библиотеки)
 * Задание: Напишите метод `String encodeBase64(String input)` и
 * `String decodeBase64(String encodedInput)`, используя класс `java.util.Base64`,
 * для кодирования строки в Base64 и декодирования обратно.
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
     * @return Исходная декодированная строка или null, если входная строка была null.
     * @throws IllegalArgumentException если encodedInput содержит невалидные символы Base64
     *                                  или имеет некорректный padding.
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
}

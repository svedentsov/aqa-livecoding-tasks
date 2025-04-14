package com.svedentsov.aqa.tasks.strings;

import java.util.Objects;

/**
 * Решение задачи №31: Сжатие строки.
 * <p>
 * Описание: "aabcccccaaa" -> "a2b1c5a3". Если "сжатая" строка не короче
 * исходной, вернуть исходную. (Проверяет: работа со строками, StringBuilder, циклы)
 * <p>
 * Задание: Напишите метод `String compressString(String str)`, который выполняет
 * базовое сжатие строки, заменяя последовательности повторяющихся символов
 * на символ и количество повторений. Если "сжатая" строка не становится
 * короче исходной, метод должен вернуть исходную строку.
 * <p>
 * Пример: `compressString("aabcccccaaa")` -> `"a2b1c5a3"`.
 * `compressString("abc")` -> `"abc"`.
 * `compressString("aabbcc")` -> `"a2b2c2"` (но вернет "aabbcc").
 */
public class StringCompression {

    /**
     * Выполняет базовое сжатие строки путем замены последовательностей
     * повторяющихся символов на символ и количество повторений.
     * Например, "aabcccccaaa" преобразуется в "a2b1c5a3".
     * Если сжатая строка не короче исходной, возвращается исходная строка.
     * Учитывает регистр символов.
     * <p>
     * Алгоритм:
     * 1. Обработать null и короткие строки (<= 2 символов), вернуть их как есть.
     * 2. Использовать StringBuilder для построения сжатой строки.
     * 3. Итерировать по строке с i=1, сравнивая str[i] с str[i-1].
     * 4. Поддерживать счетчик `count` для текущей последовательности.
     * 5. Если символ изменился (str[i] != str[i-1]):
     * a. Добавить предыдущий символ (str[i-1]) в StringBuilder.
     * b. Добавить его счетчик `count` в StringBuilder.
     * c. Сбросить `count` в 1.
     * d. (Опционально) Проверить, не стала ли сжатая строка уже >= исходной.
     * 6. Если символ не изменился, увеличить `count`.
     * 7. После цикла добавить последний символ (str[length-1]) и его `count`.
     * 8. Сравнить длину сжатой строки с исходной. Вернуть сжатую, если она строго короче, иначе исходную.
     *
     * @param str Исходная строка для сжатия. Может быть null.
     * @return Сжатая строка или исходная строка, если сжатие не уменьшило длину
     * или если исходная строка null/пуста/имеет длину 1 или 2.
     */
    public String compressString(String str) {
        // Шаг 1: Обработка null и коротких строк
        if (str == null || str.length() <= 2) {
            return str;
        }

        // Шаг 2: Инициализация StringBuilder и счетчика
        StringBuilder compressed = new StringBuilder();
        int count = 1; // Счетчик текущей последовательности

        // Шаг 3-6: Итерация и подсчет
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                count++; // Символ повторяется
            } else {
                // Символ изменился, добавляем предыдущий символ и его счетчик
                compressed.append(str.charAt(i - 1));
                compressed.append(count);
                count = 1; // Сбрасываем счетчик для нового символа

                // Оптимизация: если сжатая строка уже не короче, нет смысла продолжать
                if (compressed.length() >= str.length()) {
                    return str;
                }
            }
        }

        // Шаг 7: Добавление последнего символа и его счетчика
        compressed.append(str.charAt(str.length() - 1));
        compressed.append(count);

        // Шаг 8: Финальное сравнение длин и возврат результата
        return compressed.length() < str.length() ? compressed.toString() : str;
    }

    /**
     * Точка входа для демонстрации работы метода сжатия строки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        StringCompression sol = new StringCompression();
        String[] testCases = {
                "aabcccccaaa", // a2b1c5a3
                "abc",         // abc (сжатая a1b1c1 длиннее)
                "aabbcc",      // aabbcc (сжатая a2b2c2 не короче)
                "aa",          // aa (сжатая a2 не короче)
                "a",           // a (<=2)
                "",            // "" (<=2)
                null,          // null (null)
                "abbbbbbbbbbbb", // a1b12 (13 -> 5, короче)
                "aaAAaa",      // aaAAaa (сжатая a2A2a2 не короче)
                "aaaaabbbbbc"  // a5b5c1 (11 -> 6, короче)
        };
        String[] expected = {
                "a2b1c5a3",
                "abc",
                "aabbcc",
                "aa",
                "a",
                "",
                null,
                "a1b12",
                "aaAAaa",
                "a5b5c1"
        };

        System.out.println("--- Testing String Compression ---");
        for (int i = 0; i < testCases.length; i++) {
            runCompressionTest(sol, testCases[i], expected[i]);
        }
    }

    /**
     * Вспомогательный метод для тестирования сжатия строки.
     *
     * @param sol      Экземпляр решателя.
     * @param input    Тестовая строка.
     * @param expected Ожидаемый результат.
     */
    private static void runCompressionTest(StringCompression sol, String input, String expected) {
        String inputStr = (input == null ? "null" : "'" + input + "'");
        String expectedStr = (expected == null ? "null" : "'" + expected + "'");
        try {
            String result = sol.compressString(input);
            String resultStr = (result == null ? "null" : "'" + result + "'");
            System.out.printf("compressString(%s) -> %s (Expected: %s) %s%n",
                    inputStr, resultStr, expectedStr,
                    (Objects.equals(result, expected) ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.err.printf("Error processing %s: %s%n", inputStr, e.getMessage());
        }
    }
}

package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №31: Сжатие строки.
 */
public class Task31_StringCompression {

    /**
     * Выполняет базовое сжатие строки путем замены последовательностей
     * повторяющихся символов на символ и количество повторений.
     * Например, "aabcccccaaa" преобразуется в "a2b1c5a3".
     * Если сжатая строка не короче исходной, возвращается исходная строка.
     * Учитывает регистр символов.
     *
     * @param str Исходная строка для сжатия. Может быть null.
     * @return Сжатая строка или исходная строка, если сжатие не уменьшило длину
     * или если исходная строка null/пуста/имеет длину 1.
     */
    public String compressString(String str) {
        // Обработка null и коротких строк, для которых сжатие не имеет смысла
        // или не может уменьшить длину (aa -> a2 - длина та же).
        if (str == null || str.length() <= 2) {
            return str;
        }

        StringBuilder compressed = new StringBuilder();
        int count = 1; // Счетчик текущей последовательности символов

        // Проходим по строке, сравнивая текущий символ (i) с предыдущим (i-1)
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                // Символ повторяется, увеличиваем счетчик
                count++;
            } else {
                // Символ изменился:
                // 1. Добавляем предыдущий символ.
                compressed.append(str.charAt(i - 1));
                // 2. Добавляем его счетчик.
                compressed.append(count);
                // 3. Сбрасываем счетчик для нового символа.
                count = 1;
            }

            // Оптимизация (опционально): Если на каком-то этапе сжатая строка
            // уже стала длиннее или равна исходной, дальнейшее сжатие бессмысленно.
            // if (compressed.length() >= str.length()) {
            //     return str;
            // }
        }

        // После окончания цикла нужно добавить последний символ и его счетчик
        compressed.append(str.charAt(str.length() - 1));
        compressed.append(count);

        // Сравниваем длину сжатой и исходной строк
        // Возвращаем сжатую только если она СТРОГО короче исходной
        return compressed.length() < str.length() ? compressed.toString() : str;
    }

    /**
     * Точка входа для демонстрации работы метода сжатия строки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task31_StringCompression sol = new Task31_StringCompression();
        String[] testCases = {
                "aabcccccaaa", // a2b1c5a3
                "abc",         // abc (сжатая a1b1c1 длиннее)
                "aabbcc",      // aabbcc (сжатая a2b2c2 не короче)
                "a",           // a
                "",            // ""
                null,          // null
                "abbbbbbbbbbbb", // a1b12
                "aaAAaa",      // aaAAaa (сжатая a2A2a2 не короче)
                "aaaaabbbbbc"  // a5b5c1
        };
        String[] expected = {
                "a2b1c5a3",
                "abc",
                "aabbcc",
                "a",
                "",
                null,
                "a1b12",
                "aaAAaa",
                "a5b5c1"
        };

        for (int i = 0; i < testCases.length; i++) {
            String result = sol.compressString(testCases[i]);
            System.out.println("compressString('" + testCases[i] + "') -> '" + result + "' (Expected: '" + expected[i] + "')");
            // Используем Objects.equals для корректного сравнения с null
            if (!java.util.Objects.equals(result, expected[i])) {
                System.err.println("   Mismatch!");
            }
        }
    }
}

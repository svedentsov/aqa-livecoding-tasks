package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №31: Сжатие строки.
 * Описание: "aabcccccaaa" -> "a2b1c5a3". Если "сжатая" строка не короче
 * исходной, вернуть исходную. (Проверяет: работа со строками, StringBuilder, циклы)
 * Задание: Напишите метод `String compressString(String str)`, который выполняет
 * базовое сжатие строки, заменяя последовательности повторяющихся символов
 * на символ и количество повторений. Если "сжатая" строка не становится
 * короче исходной, метод должен вернуть исходную строку.
 * Пример: `compressString("aabcccccaaa")` -> `"a2b1c5a3"`.
 * `compressString("abc")` -> `"abc"`.
 * `compressString("aabbcc")` -> `"aabbcc"` (т.к. "a2b2c2" не короче).
 */
public class StringCompression {

    /**
     * Выполняет базовое сжатие строки путем замены последовательностей
     * повторяющихся символов на символ и количество повторений.
     * Если сжатая строка не короче исходной, возвращается исходная строка.
     * Учитывает регистр символов.
     *
     * @param str Исходная строка для сжатия. Может быть null.
     * @return Сжатая строка или исходная строка, если сжатие не уменьшило длину
     * или если исходная строка null/пуста/имеет длину 1 или 2.
     */
    public String compressString(String str) {
        // Шаг 1: Обработка null и коротких строк (длина <= 2 не может стать короче)
        if (str == null || str.length() <= 2) {
            return str;
        }

        // Шаг 2: Инициализация StringBuilder и счетчика
        // Начальная емкость может быть оценена, но стандартной обычно достаточно
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
}

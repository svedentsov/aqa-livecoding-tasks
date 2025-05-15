package com.svedentsov.aqa.tasks.strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Решение задачи №21: Перевернуть порядок слов в предложении.
 * Описание: "hello world" -> "world hello". (Проверяет: работа со строками, `split()`, `join()`/StringBuilder)
 * Задание: Напишите метод `String reverseWords(String sentence)`, который переворачивает
 * порядок слов в предложении `sentence`, сохраняя сами слова неизменными.
 * Разделители - пробелы. Лишние пробелы в начале/конце/между словами должны быть убраны,
 * оставляя ровно один пробел между словами в результате.
 * Пример: `reverseWords("the sky is blue")` -> `"blue is sky the"`.
 * `reverseWords("  hello world  ")` -> `"world hello"`.
 */
public class ReverseWordsSentence {

    /**
     * Переворачивает порядок слов в предложении итеративно.
     * Удаляет лишние пробелы в начале, конце и между словами,
     * оставляя ровно один пробел между перевернутыми словами.
     *
     * @param sentence Исходное предложение. Может быть null.
     * @return Предложение с перевернутым порядком слов или пустая строка,
     * если исходная строка null, пуста или содержит только пробелы.
     */
    public String reverseWords(String sentence) {
        // Шаг 1: Проверка на null
        if (sentence == null) {
            return "";
        }
        // Шаг 2: Удаление внешних пробелов
        String trimmedSentence = sentence.trim();
        // Шаг 3: Проверка на пустоту
        if (trimmedSentence.isEmpty()) {
            return "";
        }

        // Шаг 4: Разделение на слова
        String[] words = trimmedSentence.split("\\s+");

        // Шаг 5: Переворот массива слов (in-place)
        int left = 0;
        int right = words.length - 1;
        while (left < right) {
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }

        // Шаг 6: Сборка строки обратно
        return String.join(" ", words);
    }

    /**
     * Переворачивает порядок слов в предложении с использованием Stream API и Collections.reverse.
     * Также удаляет лишние пробелы.
     *
     * @param sentence Исходное предложение. Может быть null.
     * @return Предложение с перевернутым порядком слов или пустая строка.
     */
    public String reverseWordsStream(String sentence) {
        if (sentence == null) {
            return "";
        }
        String trimmedSentence = sentence.trim();
        if (trimmedSentence.isEmpty()) {
            return "";
        }
        // Разбиваем на слова, игнорируя лишние пробелы
        List<String> words = Arrays.stream(trimmedSentence.split("\\s+"))
                // Фильтрация пустых строк не нужна при использовании split("\\s+") на непустой строке
                .collect(Collectors.toList());
        // Переворачиваем список
        Collections.reverse(words);
        // Соединяем обратно с одним пробелом
        return String.join(" ", words);
    }
}

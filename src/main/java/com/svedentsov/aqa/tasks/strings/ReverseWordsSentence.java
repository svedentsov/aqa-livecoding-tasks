package com.svedentsov.aqa.tasks.strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Решение задачи №21: Перевернуть порядок слов в предложении.
 * <p>
 * Описание: "hello world" -> "world hello". (Проверяет: работа со строками, `split()`, `join()`/StringBuilder)
 * <p>
 * Задание: Напишите метод `String reverseWords(String sentence)`, который переворачивает
 * порядок слов в предложении `sentence`, сохраняя сами слова неизменными.
 * Разделители - пробелы. Лишние пробелы в начале/конце/между словами должны быть убраны,
 * оставляя ровно один пробел между словами в результате.
 * <p>
 * Пример: `reverseWords("the sky is blue")` -> `"blue is sky the"`.
 * `reverseWords("  hello world  ")` -> `"world hello"`.
 */
public class ReverseWordsSentence {

    /**
     * Переворачивает порядок слов в предложении итеративно.
     * Удаляет лишние пробелы в начале, конце и между словами,
     * оставляя ровно один пробел между перевернутыми словами.
     * <p>
     * Алгоритм:
     * 1. Обработать null: вернуть пустую строку.
     * 2. Убрать ведущие/завершающие пробелы (`trim()`).
     * 3. Если строка пуста после trim, вернуть пустую строку.
     * 4. Разделить строку на слова по одному или нескольким пробелам (`split("\\s+")`).
     * 5. Перевернуть порядок элементов в полученном массиве слов.
     * 6. Соединить слова обратно в строку с одним пробелом в качестве разделителя (`String.join()`).
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

        // Шаг 5: Переворот массива слов
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
                // .filter(s -> !s.isEmpty()) // Не обязательно, т.к. split("\\s+") не создает пустых строк при таком использовании
                .collect(Collectors.toList());
        // Переворачиваем список
        Collections.reverse(words);
        // Соединяем обратно с одним пробелом
        return String.join(" ", words);
    }

    /**
     * Точка входа для демонстрации работы методов переворота слов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ReverseWordsSentence sol = new ReverseWordsSentence();

        runReverseWordsTest(sol, "the sky is blue", "Стандартное предложение"); // "blue is sky the"
        runReverseWordsTest(sol, "  hello world  ", "С внешними пробелами"); // "world hello"
        runReverseWordsTest(sol, "a good   example", "С множественными внутренними пробелами"); // "example good a"
        runReverseWordsTest(sol, " single ", "Одно слово с пробелами"); // "single"
        runReverseWordsTest(sol, "   ", "Только пробелы"); // ""
        runReverseWordsTest(sol, "", "Пустая строка"); // ""
        runReverseWordsTest(sol, null, "Null строка"); // ""
        runReverseWordsTest(sol, "word, one.", "С пунктуацией (остается при словах)"); // "one. word,"
        runReverseWordsTest(sol, "1 two 3 four", "С цифрами"); // "four 3 two 1"
        runReverseWordsTest(sol, "  leading", "Только ведущие пробелы"); // "leading"
        runReverseWordsTest(sol, "trailing  ", "Только завершающие пробелы"); // "trailing"
    }

    /**
     * Вспомогательный метод для тестирования переворота слов.
     *
     * @param sol         Экземпляр решателя.
     * @param sentence    Тестовая строка.
     * @param description Описание теста.
     */
    private static void runReverseWordsTest(ReverseWordsSentence sol, String sentence, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (sentence == null ? "null" : "\"" + sentence + "\"");
        System.out.println("Input: " + input);
        try {
            String resultIterative = sol.reverseWords(sentence);
            System.out.println("Reversed (Iterative): \"" + resultIterative + "\"");
        } catch (Exception e) {
            System.out.println("Reversed (Iterative): Error - " + e.getMessage());
        }
        try {
            String resultStream = sol.reverseWordsStream(sentence);
            System.out.println("Reversed (Stream):    \"" + resultStream + "\"");
        } catch (Exception e) {
            System.out.println("Reversed (Stream):    Error - " + e.getMessage());
        }
    }
}

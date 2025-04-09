package com.svedentsov.aqa.tasks.strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Решение задачи №21: Перевернуть порядок слов в предложении.
 */
public class Task21_ReverseWordsSentence {

    /**
     * Переворачивает порядок слов в предложении.
     * Удаляет лишние пробелы в начале, конце и между словами,
     * оставляя ровно один пробел между перевернутыми словами.
     * Использует ручной переворот массива слов.
     *
     * @param sentence Исходное предложение. Может быть null.
     * @return Предложение с перевернутым порядком слов или пустая строка,
     * если исходная строка null, пуста или содержит только пробелы.
     */
    public String reverseWords(String sentence) {
        if (sentence == null) {
            return ""; // Возвращаем пустую строку для null
        }
        // 1. trim() убирает пробелы в начале/конце
        String trimmedSentence = sentence.trim();
        // Если после trim строка пуста
        if (trimmedSentence.isEmpty()) {
            return "";
        }

        // 2. split("\\s+") разделяет по одному или нескольким пробелам
        String[] words = trimmedSentence.split("\\s+");

        // 3. Переворачиваем массив слов "на месте"
        int left = 0;
        int right = words.length - 1;
        while (left < right) {
            String temp = words[left];
            words[left] = words[right];
            words[right] = temp;
            left++;
            right--;
        }

        // 4. Собираем обратно в строку с одним пробелом между словами
        return String.join(" ", words);
    }

    /**
     * Переворачивает порядок слов в предложении с использованием Stream API и Collections.reverse.
     * Удаляет лишние пробелы.
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
        // Используем stream для разделения, сбора в List, реверса и соединения
        List<String> words = Arrays.stream(trimmedSentence.split("\\s+"))
                .collect(Collectors.toList());
        Collections.reverse(words);
        return String.join(" ", words);
    }

    /**
     * Точка входа для демонстрации работы методов переворота слов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task21_ReverseWordsSentence sol = new Task21_ReverseWordsSentence();
        String s1 = "the sky is blue";
        System.out.println("'" + s1 + "' -> '" + sol.reverseWords(s1) + "'"); // "blue is sky the"
        System.out.println("'" + s1 + "' -> '" + sol.reverseWordsStream(s1) + "'");

        String s2 = "  hello world  ";
        System.out.println("'" + s2 + "' -> '" + sol.reverseWords(s2) + "'"); // "world hello"
        System.out.println("'" + s2 + "' -> '" + sol.reverseWordsStream(s2) + "'");

        String s3 = "a good   example";
        System.out.println("'" + s3 + "' -> '" + sol.reverseWords(s3) + "'"); // "example good a"
        System.out.println("'" + s3 + "' -> '" + sol.reverseWordsStream(s3) + "'");

        String s4 = " single ";
        System.out.println("'" + s4 + "' -> '" + sol.reverseWords(s4) + "'"); // "single"
        System.out.println("'" + s4 + "' -> '" + sol.reverseWordsStream(s4) + "'");

        String s5 = "   ";
        System.out.println("'" + s5 + "' -> '" + sol.reverseWords(s5) + "'"); // ""
        System.out.println("'" + s5 + "' -> '" + sol.reverseWordsStream(s5) + "'"); // ""

        String s6 = null;
        System.out.println("null -> '" + sol.reverseWords(s6) + "'"); // ""
        System.out.println("null -> '" + sol.reverseWordsStream(s6) + "'"); // ""
    }
}

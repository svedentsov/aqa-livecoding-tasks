package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №18: Посчитать количество слов в строке.
 */
public class Task18_CountWordsString {

    /**
     * Подсчитывает количество слов в строке.
     * Словами считаются последовательности непробельных символов,
     * разделенные одним или несколькими пробелами или другими пробельными символами (табы, переводы строк и т.д.).
     * Пробелы в начале и конце строки игнорируются.
     *
     * @param sentence Строка для подсчета слов. Может быть null.
     * @return Количество слов в строке. Возвращает 0, если строка null,
     * пустая или состоит только из пробельных символов.
     */
    public int countWords(String sentence) {
        if (sentence == null) {
            return 0;
        }
        // trim() убирает пробельные символы в начале и конце строки.
        String trimmedSentence = sentence.trim();
        // Если строка после trim пустая, значит слов нет.
        if (trimmedSentence.isEmpty()) {
            return 0;
        }
        // split("\\s+") разделяет строку по одному или нескольким пробельным символам (\s).
        // \\s включает пробел, таб \t, перевод строки \n, \r, \f и т.д.
        // + означает "один или более".
        String[] words = trimmedSentence.split("\\s+");
        // Количество элементов в полученном массиве - это количество слов.
        return words.length;
    }
    /**
     * Точка входа для демонстрации работы метода подсчета слов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task18_CountWordsString sol = new Task18_CountWordsString();
        String s1 = "This is a sample sentence.";
        System.out.println("'" + s1 + "' -> words: " + sol.countWords(s1)); // 5

        String s2 = "   Hello \t world  \n next line ";
        System.out.println("'" + s2 + "' -> words: " + sol.countWords(s2)); // 4 (Hello, world, next, line)

        String s3 = " OneWord ";
        System.out.println("'" + s3 + "' -> words: " + sol.countWords(s3)); // 1

        String s4 = "  "; // Только пробелы
        System.out.println("'" + s4 + "' -> words: " + sol.countWords(s4)); // 0

        String s5 = ""; // Пустая строка
        System.out.println("'" + s5 + "' -> words: " + sol.countWords(s5)); // 0

        String s6 = null; // null
        System.out.println("null -> words: " + sol.countWords(s6)); // 0

        String s7 = " leading and trailing\t";
        System.out.println("'" + s7 + "' -> words: " + sol.countWords(s7)); // 3
    }
}

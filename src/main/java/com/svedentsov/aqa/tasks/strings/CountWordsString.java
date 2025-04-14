package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №18: Посчитать количество слов в строке.
 * <p>
 * Описание: Написать функцию, которая подсчитывает количество слов в предложении.
 * (Проверяет: работа со строками, `split()`)
 * <p>
 * Задание: Напишите метод `int countWords(String sentence)`, который подсчитывает
 * количество слов в строке `sentence`. Слова разделены одним или несколькими
 * пробелами или другими пробельными символами.
 * <p>
 * Пример: `countWords("This is a sample sentence.")` -> `5`,
 * `countWords(" Hello   world ")` -> `2`.
 */
public class CountWordsString {

    /**
     * Подсчитывает количество слов в строке.
     * Словами считаются последовательности непробельных символов,
     * разделенные одним или несколькими пробельными символами (пробел, таб,
     * перевод строки и т.д. - `\\s`).
     * Пробельные символы в начале и конце строки игнорируются благодаря `trim()`.
     * <p>
     * Алгоритм:
     * 1. Проверить на null. Если null, вернуть 0.
     * 2. Убрать ведущие и завершающие пробельные символы с помощью `trim()`.
     * 3. Если строка после `trim()` пуста, вернуть 0.
     * 4. Разделить строку по одному или нескольким пробельным символам (`split("\\s+")`).
     * 5. Вернуть длину полученного массива строк.
     *
     * @param sentence Строка для подсчета слов. Может быть null.
     * @return Количество слов в строке. Возвращает 0, если строка null,
     * пустая или состоит только из пробельных символов.
     */
    public int countWords(String sentence) {
        // Шаг 1: Проверка на null
        if (sentence == null) {
            return 0;
        }
        // Шаг 2: Удаление ведущих/завершающих пробелов
        String trimmedSentence = sentence.trim();
        // Шаг 3: Проверка на пустую строку после trim
        if (trimmedSentence.isEmpty()) {
            return 0;
        }
        // Шаг 4 & 5: Разделение по пробелам и возврат длины массива
        // Регулярное выражение \\s+ означает "один или более пробельных символов"
        return trimmedSentence.split("\\s+").length;
    }

    /**
     * Точка входа для демонстрации работы метода подсчета слов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        CountWordsString sol = new CountWordsString();

        runWordCountTest(sol, "This is a sample sentence.", "Стандартное предложение"); // 5
        runWordCountTest(sol, "   Hello \t world  \n next line ", "Разные пробельные символы"); // 4
        runWordCountTest(sol, " OneWord ", "Одно слово с пробелами"); // 1
        runWordCountTest(sol, "  ", "Только пробелы"); // 0
        runWordCountTest(sol, "", "Пустая строка"); // 0
        runWordCountTest(sol, null, "Null строка"); // 0
        runWordCountTest(sol, " leading and trailing\t", "Ведущие/завершающие пробелы"); // 3
        runWordCountTest(sol, "word, another.", "Пунктуация прикреплена к словам"); // 2
        runWordCountTest(sol, "1 2 three 4", "Слова и цифры"); // 4
        runWordCountTest(sol, " , . ! ", "Только пунктуация (и пробелы)"); // 3 (считаются как слова)
        runWordCountTest(sol, "word  another   word", "Множественные пробелы между словами"); // 3
        runWordCountTest(sol, "end.", "Слово с точкой"); // 1
    }

    /**
     * Вспомогательный метод для тестирования подсчета слов.
     *
     * @param sol         Экземпляр решателя.
     * @param sentence    Тестовая строка.
     * @param description Описание теста.
     */
    private static void runWordCountTest(CountWordsString sol, String sentence, String description) {
        System.out.println("\n--- " + description + " ---");
        String input = (sentence == null ? "null" : "\"" + sentence + "\"");
        System.out.println("Input sentence: " + input);
        try {
            int count = sol.countWords(sentence);
            System.out.println("Word count: " + count);
        } catch (Exception e) {
            System.out.println("Word count: Error - " + e.getMessage());
        }
    }
}

package com.svedentsov.aqa.tasks.strings;

/**
 * Решение задачи №59: Найти индекс первого вхождения подстроки (аналог indexOf).
 * <p>
 * Описание: Реализовать аналог `indexOf`.
 * (Проверяет: работа со строками, циклы)
 * <p>
 * Задание: Напишите метод `int findSubstringIndex(String text, String pattern)`
 * без использования `String.indexOf()`, который находит индекс первого вхождения
 * строки `pattern` в строку `text`. Если `pattern` не найден, верните -1.
 * <p>
 * Пример: `findSubstringIndex("hello world", "world")` -> `6`.
 * `findSubstringIndex("aaaaa", "bba")` -> `-1`.
 * `findSubstringIndex("abc", "")` -> `0`.
 */
public class FindSubstringIndex {

    /**
     * Находит индекс первого вхождения строки {@code pattern} (шаблон) в строку {@code text},
     * не используя встроенный метод {@code String.indexOf()}.
     * Реализует наивный алгоритм поиска подстроки путем сравнения символов ("в лоб").
     * <p>
     * Сложность: O(n * m) в худшем случае, где n - длина text, m - длина pattern.
     * (Например, text="aaaaaaaaab", pattern="aab").
     * <p>
     * Алгоритм:
     * 1. Обработать null аргументы -> вернуть -1.
     * 2. Если `pattern` пуст -> вернуть 0 (поведение `indexOf`).
     * 3. Если `pattern` длиннее `text` -> вернуть -1.
     * 4. Итерировать по `text` с индексом `i` от 0 до `n-m`. `i` - потенциальное начало вхождения.
     * 5. Для каждого `i`, итерировать по `pattern` с индексом `j` от 0 до `m-1`.
     * 6. Сравнивать `text.charAt(i + j)` с `pattern.charAt(j)`.
     * 7. Если символы не совпадают, прервать внутренний цикл (по `j`) и перейти к следующему `i`.
     * 8. Если внутренний цикл (по `j`) завершился успешно (дошли до `j == m`), значит,
     * полное совпадение найдено. Вернуть `i`.
     * 9. Если внешний цикл (по `i`) завершен, а совпадение не найдено, вернуть -1.
     *
     * @param text    Строка, в которой осуществляется поиск. Может быть null.
     * @param pattern Подстрока (шаблон), которую нужно найти. Может быть null.
     * @return Индекс первого символа первого вхождения {@code pattern} в {@code text}.
     * Возвращает 0, если {@code pattern} пуст.
     * Возвращает -1, если {@code pattern} не найден, или если {@code text} или
     * {@code pattern} равен null, или если {@code pattern} длиннее {@code text}.
     */
    public int findSubstringIndex(String text, String pattern) {
        // Шаг 1: Обработка null
        if (text == null || pattern == null) {
            return -1;
        }

        int n = text.length();
        int m = pattern.length();

        // Шаг 2: Пустой паттерн
        if (m == 0) {
            return 0;
        }
        // Шаг 3: Паттерн длиннее текста
        if (m > n) {
            return -1;
        }

        // Шаг 4: Внешний цикл по тексту
        // i <= n - m : последняя возможная стартовая позиция для паттерна
        for (int i = 0; i <= n - m; i++) {
            // Шаг 5: Внутренний цикл по паттерну (сравнение)
            int j = 0;
            while (j < m && text.charAt(i + j) == pattern.charAt(j)) {
                j++;
            }

            // Шаг 8: Проверка, найдено ли полное совпадение
            if (j == m) {
                return i; // Найдено вхождение, начиная с индекса i
            }
            // Иначе продолжаем внешний цикл (следующее значение i)
        }

        // Шаг 9: Паттерн не найден после полного прохода по тексту
        return -1;
    }

    /**
     * Точка входа для демонстрации работы метода поиска подстроки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindSubstringIndex sol = new FindSubstringIndex();

        // Тестовые пары [text, pattern]
        String[][] testCases = {
                {"hello world, welcome to the world!", "world"}, // 6
                {"hello world", "o w"},                          // 4
                {"aaaaa", "bba"},                               // -1
                {"abc", ""},                                    // 0
                {"abc", "abc"},                                 // 0
                {"abc", "d"},                                   // -1
                {"mississippi", "issi"},                        // 1
                {"mississippi", "issip"},                       // 4
                {"abc", "abcd"},                                // -1
                {"ababab", "aba"},                              // 0 (первое вхождение)
                {"ababab", "bab"},                              // 1 (первое вхождение)
                {"ababab", "ac"},                               // -1
                {"", "a"},                                      // -1
                {"a", "a"},                                     // 0
                {null, "a"},                                    // -1
                {"a", null},                                    // -1
                {null, null},                                   // -1
                {"", ""}                                        // 0
        };

        System.out.println("--- Finding Substring Index (Manual indexOf) ---");
        for (String[] testCase : testCases) {
            runSubstringTest(sol, testCase[0], testCase[1]);
        }
    }

    /**
     * Вспомогательный метод для тестирования findSubstringIndex.
     *
     * @param sol     Экземпляр решателя.
     * @param text    Текст для поиска.
     * @param pattern Искомый паттерн.
     */
    private static void runSubstringTest(FindSubstringIndex sol, String text, String pattern) {
        String textStr = (text == null ? "null" : "'" + text + "'");
        String patternStr = (pattern == null ? "null" : "'" + pattern + "'");
        System.out.println("\nInput: text=" + textStr + ", pattern=" + patternStr);
        try {
            int index = sol.findSubstringIndex(text, pattern);
            // Сравним с результатом стандартного indexOf для проверки
            int expected = (text == null || pattern == null) ? -1 : text.indexOf(pattern);
            System.out.printf("  Result Index: %d (Expected: %d) %s%n",
                    index, expected, (index == expected ? "" : "<- MISMATCH!"));
        } catch (Exception e) {
            System.err.println("  Result Index: Error - " + e.getMessage());
        }
    }
}

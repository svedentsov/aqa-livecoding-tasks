package com.svedentsov.aqa.tasks.dp;

/**
 * Решение задачи №64: Редакторское расстояние (Edit Distance / Levenshtein Distance).
 * <p>
 * Описание: Найти минимальное количество операций (вставка, удаление, замена)
 * для превращения одной строки в другую. (Проверяет: динамическое программирование)
 * <p>
 * Задание: Напишите метод `int minEditDistance(String word1, String word2)`, который
 * вычисляет минимальное количество операций (вставка, удаление, замена символа),
 * необходимых для преобразования `word1` в `word2`.
 * <p>
 * Пример: `minEditDistance("horse", "ros")` -> `3`.
 * `minEditDistance("intention", "execution")` -> `5`.
 */
public class EditDistance {

    /**
     * Вычисляет минимальное редакторское расстояние (расстояние Левенштейна)
     * между двумя строками {@code word1} и {@code word2}.
     * Редакторское расстояние - это минимальное количество односимвольных операций
     * (вставка символа, удаление символа, замена символа), необходимых
     * для преобразования одной строки в другую.
     * Использует метод динамического программирования с построением таблицы DP.
     * <p>
     * Сложность: O(m * n) по времени и O(m * n) по памяти, где m и n - длины строк.
     * (Память можно оптимизировать до O(min(m, n))).
     *
     * @param word1 Первая строка. Может быть null.
     * @param word2 Вторая строка. Может быть null.
     * @return Минимальное редакторское расстояние между строками.
     * Расстояние между строкой и null/пустой строкой равно длине непустой строки.
     */
    public int minEditDistance(String word1, String word2) {
        // Обработка null как пустых строк
        word1 = (word1 == null) ? "" : word1;
        word2 = (word2 == null) ? "" : word2;

        int m = word1.length();
        int n = word2.length();

        // Базовый случай: если одна из строк пуста
        if (m == 0) return n;
        if (n == 0) return m;

        // DP таблица: dp[i][j] = расстояние между word1[0..i-1] и word2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];

        // Инициализация первой строки и первого столбца
        // dp[i][0] = i (i удалений из word1 чтобы получить "")
        // dp[0][j] = j (j вставок в "" чтобы получить word2)
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        // Заполнение таблицы
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char char1 = word1.charAt(i - 1);
                char char2 = word2.charAt(j - 1);

                // Если символы совпадают, стоимость операции = 0, берем результат из dp[i-1][j-1]
                if (char1 == char2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Если символы не совпадают, выбираем минимум из трех операций + 1
                    int costReplace = dp[i - 1][j - 1]; // Стоимость замены
                    int costDelete = dp[i - 1][j];      // Стоимость удаления из word1
                    int costInsert = dp[i][j - 1];      // Стоимость вставки в word1

                    dp[i][j] = 1 + Math.min(costReplace, Math.min(costDelete, costInsert));
                }
            }
        }

        // Результат в правом нижнем углу
        return dp[m][n];
    }

    /**
     * Точка входа для демонстрации работы метода вычисления редакторского расстояния.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        EditDistance sol = new EditDistance();
        String[][] wordPairs = {
                {"horse", "ros"},           // 3
                {"intention", "execution"},// 5
                {"abc", "abc"},            // 0
                {"", "abc"},              // 3
                {"abc", ""},              // 3
                {"saturday", "sunday"},    // 3
                {"kitten", "sitting"},     // 3
                {"flaw", "lawn"},          // 2
                {"", ""},                 // 0
                {null, "abc"},            // 3
                {"abc", null},            // 3
                {null, null}              // 0
        };

        System.out.println("--- Calculating Edit Distance (Levenshtein) ---");
        for (String[] pair : wordPairs) {
            runEditDistanceTest(sol, pair[0], pair[1]);
        }
    }

    /**
     * Вспомогательный метод для тестирования minEditDistance.
     *
     * @param sol   Экземпляр решателя.
     * @param word1 Первое слово.
     * @param word2 Второе слово.
     */
    private static void runEditDistanceTest(EditDistance sol, String word1, String word2) {
        String w1Str = (word1 == null ? "null" : "'" + word1 + "'");
        String w2Str = (word2 == null ? "null" : "'" + word2 + "'");
        System.out.print("Distance(" + w1Str + ", " + w2Str + ") = ");
        try {
            int dist = sol.minEditDistance(word1, word2);
            System.out.println(dist);
            // Можно добавить сравнение с ожидаемым значением, если оно передано
        } catch (Exception e) {
            System.err.println("Error - " + e.getMessage());
        }
    }
}

package com.svedentsov.aqa.tasks.dp;

/**
 * Решение задачи №64: Редакторское расстояние (Edit Distance / Levenshtein Distance).
 * Описание: Найти минимальное количество операций (вставка, удаление, замена)
 * для превращения одной строки в другую. (Проверяет: динамическое программирование)
 * Задание: Напишите метод `int minEditDistance(String word1, String word2)`, который
 * вычисляет минимальное количество операций (вставка, удаление, замена символа),
 * необходимых для преобразования `word1` в `word2`.
 * Пример: `minEditDistance("horse", "ros")` -> `3`.
 * `minEditDistance("intention", "execution")` -> `5`.
 */
public class EditDistance {

    /**
     * Вычисляет минимальное редакторское расстояние (расстояние Левенштейна)
     * между двумя строками {@code word1} и {@code word2}.
     * Использует метод динамического программирования.
     * Сложность: O(m * n) по времени и O(m * n) по памяти.
     *
     * @param word1 Первая строка. Может быть null.
     * @param word2 Вторая строка. Может быть null.
     * @return Минимальное редакторское расстояние между строками.
     */
    public int minEditDistance(String word1, String word2) {
        // Обработка null как пустых строк
        word1 = (word1 == null) ? "" : word1;
        word2 = (word2 == null) ? "" : word2;

        int m = word1.length();
        int n = word2.length();

        // Базовый случай: если одна из строк пуста
        if (m == 0) return n; // Нужно n вставок
        if (n == 0) return m; // Нужно m удалений

        // DP таблица: dp[i][j] = расстояние между word1[0..i-1] и word2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];

        // Инициализация первой строки и первого столбца
        for (int i = 0; i <= m; i++) dp[i][0] = i; // Расстояние от word1[0..i-1] до "" это i удалений
        for (int j = 0; j <= n; j++) dp[0][j] = j; // Расстояние от "" до word2[0..j-1] это j вставок

        // Заполнение таблицы
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char char1 = word1.charAt(i - 1);
                char char2 = word2.charAt(j - 1);

                // Если символы совпадают, стоимость операции = 0 от предыдущего состояния
                if (char1 == char2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Если символы не совпадают, выбираем минимум из трех операций + 1
                    int costReplace = dp[i - 1][j - 1]; // Замена char1 на char2
                    int costDelete = dp[i - 1][j];      // Удаление char1 из word1
                    int costInsert = dp[i][j - 1];      // Вставка char2 в word1

                    dp[i][j] = 1 + Math.min(costReplace, Math.min(costDelete, costInsert));
                }
            }
        }
        // Результат в правом нижнем углу
        return dp[m][n];
    }
}

package com.svedentsov.aqa.tasks.dp;

/**
 * Решение задачи №64: Редакторское расстояние (Edit Distance / Levenshtein Distance).
 */
public class Task64_EditDistance {

    /**
     * Вычисляет минимальное редакторское расстояние (расстояние Левенштейна)
     * между двумя строками {@code word1} и {@code word2}.
     * Редакторское расстояние - это минимальное количество односимвольных операций
     * (вставка символа, удаление символа, замена символа), необходимых
     * для преобразования одной строки в другую.
     * Использует метод динамического программирования с построением таблицы DP.
     * Сложность O(m * n) по времени и O(m * n) по памяти, где m и n - длины строк.
     * Память можно оптимизировать до O(min(m, n)), храня только две строки таблицы.
     *
     * @param word1 Первая строка. Может быть null.
     * @param word2 Вторая строка. Может быть null.
     * @return Минимальное редакторское расстояние между строками.
     * Расстояние между строкой и null/пустой строкой равно длине непустой строки.
     */
    public int minEditDistance(String word1, String word2) {
        // Обработка null как пустых строк
        if (word1 == null) word1 = "";
        if (word2 == null) word2 = "";

        int m = word1.length();
        int n = word2.length();

        // Если одна из строк пуста, расстояние равно длине другой строки
        // (количество вставок/удалений).
        if (m == 0) return n;
        if (n == 0) return m;

        // Таблица DP: dp[i][j] - расстояние между первыми i символами word1
        //                       и первыми j символами word2.
        // Размеры (m+1) x (n+1) для учета пустых префиксов.
        int[][] dp = new int[m + 1][n + 1];

        // Инициализация базовых случаев:
        // Расстояние от префикса длины i до пустой строки ("") равно i (i удалений).
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        // Расстояние от пустой строки ("") до префикса длины j равно j (j вставок).
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Заполнение остальной части таблицы DP
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Получаем символы, соответствующие индексам i и j в таблице DP
                // (индексы в строках на 1 меньше).
                char char1 = word1.charAt(i - 1);
                char char2 = word2.charAt(j - 1);

                // Если последние символы совпадают, стоимость последней операции 0.
                // Расстояние равно расстоянию для подстрок без этих последних символов.
                if (char1 == char2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Если символы не совпадают, рассматриваем три возможные операции
                    // для получения word2[0..j-1] из word1[0..i-1]:
                    // 1. Замена: Преобразовать word1[0..i-2] в word2[0..j-2] (стоимость dp[i-1][j-1]),
                    //            затем заменить word1[i-1] на word2[j-1] (стоимость +1).
                    int costReplace = dp[i - 1][j - 1];
                    // 2. Удаление: Преобразовать word1[0..i-2] в word2[0..j-1] (стоимость dp[i-1][j]),
                    //             затем удалить word1[i-1] (стоимость +1).
                    int costDelete = dp[i - 1][j];
                    // 3. Вставка: Преобразовать word1[0..i-1] в word2[0..j-2] (стоимость dp[i][j-1]),
                    //            затем вставить word2[j-1] (стоимость +1).
                    int costInsert = dp[i][j - 1];

                    // Выбираем операцию с минимальной стоимостью и добавляем 1 (стоимость самой операции).
                    dp[i][j] = 1 + Math.min(costReplace, Math.min(costDelete, costInsert));
                }
            }
        }

        // Результат находится в правом нижнем углу таблицы: расстояние между word1 и word2.
        return dp[m][n];
    }

    /**
     * Точка входа для демонстрации работы метода вычисления редакторского расстояния.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task64_EditDistance sol = new Task64_EditDistance();
        String[][] wordPairs = {
                {"horse", "ros"},           // 3 (h->r, o->o, r->s, s-> , e-> ) remove e, change h->r, remove s
                {"intention", "execution"},// 5
                {"abc", "abc"},            // 0
                {"", "abc"},              // 3
                {"abc", ""},              // 3
                {"saturday", "sunday"},    // 3
                {"kitten", "sitting"},     // 3
                {"flaw", "lawn"},          // 2
                {"", ""},                 // 0
                {null, "abc"},            // 3
                {"abc", null}             // 3
        };
        int[] expectedDistances = {3, 5, 0, 3, 3, 3, 3, 2, 0, 3, 3};

        for (int i = 0; i < wordPairs.length; i++) {
            String w1 = wordPairs[i][0];
            String w2 = wordPairs[i][1];
            int dist = sol.minEditDistance(w1, w2);
            System.out.println("Distance('" + w1 + "', '" + w2 + "') = " + dist + " (Expected: " + expectedDistances[i] + ")");
            if (dist != expectedDistances[i]) System.err.println("   Mismatch!");
        }
    }
}

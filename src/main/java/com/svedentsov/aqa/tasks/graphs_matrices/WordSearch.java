package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;

/**
 * Решение задачи №81: Поиск слова в матрице символов (Word Search).
 * Описание: Найти слово в 2D сетке букв (горизонтально/вертикально).
 * (Проверяет: обход матриц, рекурсия/DFS)
 * Задание: Напишите метод `boolean exist(char[][] board, String word)`, который
 * проверяет, можно ли найти слово `word` в 2D матрице символов `board`, двигаясь
 * по горизонтали или вертикали к соседним клеткам. Один и тот же символ нельзя
 * использовать дважды в одном слове.
 * Пример: `board = {{'A','B','C','E'}, {'S','F','C','S'}, {'A','D','E','E'}}, word = "ABCCED"` -> `true`.
 * `word = "SEE"` -> `true`. `word = "ABCB"` -> `false`.
 */
public class WordSearch {

    /**
     * Проверяет, существует ли заданное слово {@code word} в 2D сетке символов {@code board}.
     * Слово может быть сформировано путем последовательного перехода к смежным
     * (горизонтально или вертикально) ячейкам. Одна и та же ячейка (символ)
     * не может использоваться более одного раза при формировании одного слова.
     * Использует Поиск в Глубину (DFS) с возвратом (backtracking).
     *
     * @param board 2D массив символов (доска).
     * @param word  Слово для поиска.
     * @return {@code true}, если слово найдено на доске, {@code false} в противном случае.
     */
    public boolean exist(char[][] board, String word) {
        // 1. Проверки на невалидный вход
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }
        if (word == null || word.isEmpty()) {
            // Считаем, что пустую строку найти нельзя (согласно большинству реализаций)
            return false;
        }

        int rows = board.length;
        int cols = board[0].length;
        char[] wordChars = word.toCharArray();

        // Оптимизация: если слово длиннее числа ячеек
        if (word.length() > rows * cols) {
            return false;
        }

        // 2. Поиск стартовой ячейки и запуск DFS
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Если нашли первую букву и DFS из этой точки вернул true
                if (board[i][j] == wordChars[0] && dfs(board, i, j, wordChars, 0)) {
                    return true; // Слово найдено
                }
            }
        }
        // Слово не найдено после обхода всей доски
        return false;
    }

    /**
     * Точка входа для демонстрации работы метода поиска слова.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        WordSearch sol = new WordSearch();

        System.out.println("--- Word Search in 2D Board ---");

        char[][] board1 = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        runWordSearchTest(sol, board1, "ABCCED", "Test 1.1"); // true
        runWordSearchTest(sol, board1, "SEE", "Test 1.2");    // true
        runWordSearchTest(sol, board1, "ABCB", "Test 1.3");   // false (B используется дважды)
        runWordSearchTest(sol, board1, "SFDA", "Test 1.4");   // true
        runWordSearchTest(sol, board1, "XYZ", "Test 1.5");    // false
        runWordSearchTest(sol, board1, "ADEE", "Test 1.6");   // true
        runWordSearchTest(sol, board1, "S", "Test 1.7");     // true

        char[][] board2 = {{'a', 'a'}};
        runWordSearchTest(sol, board2, "aaa", "Test 2.1"); // false (не хватает 'a')
        runWordSearchTest(sol, board2, "aa", "Test 2.2");  // true

        char[][] board3 = {{'a'}};
        runWordSearchTest(sol, board3, "a", "Test 3.1");  // true
        runWordSearchTest(sol, board3, "b", "Test 3.2");  // false

        char[][] board4 = {{'C', 'A', 'A'}, {'A', 'A', 'A'}, {'B', 'C', 'D'}};
        runWordSearchTest(sol, board4, "AAB", "Test 4.1"); // true

        // Тесты с невалидным входом
        runWordSearchTest(sol, null, "ABC", "Test 5.1 (Null board)"); // false
        runWordSearchTest(sol, board1, null, "Test 5.2 (Null word)"); // false
        runWordSearchTest(sol, board1, "", "Test 5.3 (Empty word)"); // false
    }

    /**
     * Рекурсивный поиск в глубину (DFS) для нахождения слова {@code word},
     * начиная с ячейки (r, c) и ища символ {@code word[wordIndex]}.
     * Модифицирует доску на месте, помечая посещенные ячейки символом '#',
     * и восстанавливает их при возврате (backtracking).
     *
     * @param board     Доска (модифицируется).
     * @param r         Текущая строка.
     * @param c         Текущий столбец.
     * @param word      Слово для поиска в виде массива символов.
     * @param wordIndex Индекс текущего искомого символа в слове.
     * @return {@code true}, если оставшаяся часть слова найдена, {@code false} иначе.
     */
    private boolean dfs(char[][] board, int r, int c, char[] word, int wordIndex) {
        // Базовый случай: успех - дошли до конца слова
        if (wordIndex == word.length) {
            return true;
        }

        // Базовый случай: неудача - выход за границы или несовпадение символа
        // (Символ '#' означает, что ячейка уже посещена в текущем пути)
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != word[wordIndex]) {
            return false;
        }

        // Выбор: помечаем ячейку как посещенную (временно изменяем)
        char temp = board[r][c];
        board[r][c] = '#'; // Любой символ, не входящий в слова

        // Рекурсия: ищем следующий символ в соседях
        boolean found = dfs(board, r + 1, c, word, wordIndex + 1) || // Низ
                dfs(board, r - 1, c, word, wordIndex + 1) || // Верх
                dfs(board, r, c + 1, word, wordIndex + 1) || // Право
                dfs(board, r, c - 1, word, wordIndex + 1);   // Лево

        // Отмена выбора: восстанавливаем символ на доске
        board[r][c] = temp;

        return found;
    }

    /**
     * Вспомогательный метод для тестирования exist.
     *
     * @param sol         Экземпляр решателя.
     * @param board       Доска.
     * @param word        Слово для поиска.
     * @param description Описание теста.
     */
    private static void runWordSearchTest(WordSearch sol, char[][] board, String word, String description) {
        System.out.println("\n--- " + description + " ---");
        String boardStr = (board == null ? "null" : Arrays.deepToString(board));
        String wordStr = (word == null ? "null" : "'" + word + "'");
        // System.out.println("Input Board: " + boardStr); // Можно раскомментировать для отладки
        System.out.println("Input Word: " + wordStr);
        // Важно создать копию доски для теста, т.к. метод exist ее модифицирует
        char[][] boardCopy = (board == null) ? null : Arrays.stream(board).map(char[]::clone).toArray(char[][]::new);
        try {
            boolean result = sol.exist(boardCopy, word);
            System.out.println("Result (exist): " + result);
            // Можно добавить сравнение с ожидаемым результатом
        } catch (Exception e) {
            System.err.println("Result (exist): Error - " + e.getMessage());
        }
    }
}

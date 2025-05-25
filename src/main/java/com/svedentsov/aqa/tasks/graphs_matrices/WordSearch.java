package com.svedentsov.aqa.tasks.graphs_matrices;

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
            // Считаем, что пустую строку или null слово найти нельзя
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
     * Рекурсивный поиск в глубину (DFS) для нахождения слова {@code word},
     * начиная с ячейки (r, c) и ища символ {@code word[wordIndex]}.
     * Модифицирует доску на месте, помечая посещенные ячейки символом '#',
     * и восстанавливает их при возврате (backtracking).
     *
     * @param board     Доска (модифицируется).
     * @param r         Текущая строка.
     * @param c         Текущий столбец.
     * @param wordChars Слово для поиска в виде массива символов.
     * @param wordIndex Индекс текущего искомого символа в слове.
     * @return {@code true}, если оставшаяся часть слова найдена, {@code false} иначе.
     */
    private boolean dfs(char[][] board, int r, int c, char[] wordChars, int wordIndex) {
        // Базовый случай: успех - дошли до конца слова
        if (wordIndex == wordChars.length) {
            return true;
        }

        // Базовый случай: неудача - выход за границы или несовпадение символа
        // (Символ '#' означает, что ячейка уже посещена в текущем пути)
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != wordChars[wordIndex]) {
            return false;
        }

        // Выбор: помечаем ячейку как посещенную (временно изменяем)
        char temp = board[r][c];
        board[r][c] = '#'; // Любой символ, не входящий в алфавит слов, например

        // Рекурсия: ищем следующий символ в соседях
        boolean found = dfs(board, r + 1, c, wordChars, wordIndex + 1) || // Низ
                dfs(board, r - 1, c, wordChars, wordIndex + 1) || // Верх
                dfs(board, r, c + 1, wordChars, wordIndex + 1) || // Право
                dfs(board, r, c - 1, wordChars, wordIndex + 1);   // Лево

        // Отмена выбора: восстанавливаем символ на доске (backtracking)
        board[r][c] = temp;

        return found;
    }
}

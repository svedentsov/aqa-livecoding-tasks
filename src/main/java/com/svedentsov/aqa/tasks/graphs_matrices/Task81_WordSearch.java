package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;

/**
 * Решение задачи №81: Поиск слова в матрице символов (Word Search).
 */
public class Task81_WordSearch {

    /**
     * Проверяет, существует ли заданное слово {@code word} в 2D сетке символов {@code board}.
     * Слово может быть сформировано путем последовательного перехода к смежным
     * (горизонтально или вертикально) ячейкам. Одна и та же ячейка (символ)
     * не может использоваться более одного раза при формировании одного слова.
     * Алгоритм использует Поиск в Глубину (DFS) с возвратом (backtracking).
     * Он итерирует по всем ячейкам доски, и если ячейка содержит первую букву слова,
     * запускает из нее рекурсивный DFS для поиска оставшейся части слова.
     *
     * @param board 2D массив символов (доска). Предполагается не null и не пустой.
     * @param word  Слово для поиска. Предполагается не null и не пустое.
     * @return {@code true}, если слово найдено на доске, {@code false} в противном случае.
     */
    public boolean exist(char[][] board, String word) {
        // 1. Проверки на невалидный вход
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return false;
        }
        if (word == null || word.isEmpty()) {
            return false; // Или true, если пустая строка считается существующей? По условию LeetCode - false.
        }

        int rows = board.length;
        int cols = board[0].length;
        char[] wordChars = word.toCharArray(); // Для удобного доступа к символам слова

        // Оптимизация: если слово длиннее, чем количество ячеек, его точно нет
        if (word.length() > rows * cols) {
            return false;
        }

        // 2. Запускаем поиск из каждой ячейки доски как из стартовой
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Если нашли ячейку, совпадающую с первой буквой слова,
                // и рекурсивный поиск из этой ячейки успешен, то слово найдено.
                if (board[i][j] == wordChars[0] && dfs(board, i, j, wordChars, 0)) {
                    return true;
                }
            }
        }

        // Если прошли всю доску и не нашли слово
        return false;
    }

    /**
     * Рекурсивный поиск в глубину (DFS) для нахождения слова {@code word},
     * начиная с ячейки (r, c) и ища символ {@code word[wordIndex]}.
     * Модифицирует доску на месте, помечая посещенные ячейки символом '#'
     * (или другим, не входящим в алфавит слова), и восстанавливает их при возврате (backtracking).
     *
     * @param board     Доска (модифицируется).
     * @param r         Текущая строка.
     * @param c         Текущий столбец.
     * @param word      Слово для поиска в виде массива символов.
     * @param wordIndex Индекс текущего искомого символа в слове (начиная с 0).
     * @return {@code true}, если оставшаяся часть слова (начиная с wordIndex)
     * найдена, исходя из ячейки (r, c), {@code false} иначе.
     */
    private boolean dfs(char[][] board, int r, int c, char[] word, int wordIndex) {
        // Базовый случай 1: Успех - все символы слова найдены.
        if (wordIndex == word.length) {
            return true;
        }

        // Базовый случай 2: Неудача - выход за границы доски или
        // символ в текущей ячейке не совпадает с искомым символом слова.
        // Также проверяем, не посещена ли уже ячейка (помечена '#').
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length ||
                board[r][c] != word[wordIndex]) { // '#' тоже не будет равен word[wordIndex]
            return false;
        }

        // --- Шаг выбора ---
        // Сохраняем текущий символ и помечаем ячейку как посещенную,
        // чтобы избежать ее повторного использования в текущем пути поиска.
        char originalChar = board[r][c];
        board[r][c] = '#'; // Пометка посещения

        // --- Рекурсивный вызов для соседей ---
        // Ищем следующий символ слова (wordIndex + 1) в 4 соседних ячейках.
        boolean found = dfs(board, r + 1, c, word, wordIndex + 1) || // Вниз
                dfs(board, r - 1, c, word, wordIndex + 1) || // Вверх
                dfs(board, r, c + 1, word, wordIndex + 1) || // Вправо
                dfs(board, r, c - 1, word, wordIndex + 1);   // Влево

        // --- Шаг отмены выбора (Backtrack) ---
        // Восстанавливаем исходный символ в ячейке, чтобы она была доступна
        // для других путей поиска, начинающихся из других стартовых точек.
        board[r][c] = originalChar;

        return found; // Возвращаем результат поиска из этой ячейки
    }

    /**
     * Точка входа для демонстрации работы метода поиска слова.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task81_WordSearch sol = new Task81_WordSearch();
        char[][] board1 = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        System.out.println("Board:");
        for (char[] row : board1) System.out.println(Arrays.toString(row));

        String[] wordsToTest = {"ABCCED", "SEE", "ABCB", "SFDA", "XYZ", "ADEE", "S"};
        boolean[] expected = {true, true, false, true, false, true, true};

        for (int i = 0; i < wordsToTest.length; i++) {
            // Создаем копию доски для каждого теста, т.к. метод ее модифицирует
            char[][] boardCopy = Arrays.stream(board1).map(char[]::clone).toArray(char[][]::new);
            boolean result = sol.exist(boardCopy, wordsToTest[i]);
            System.out.println("\nWord '" + wordsToTest[i] + "' exists: " + result + " (Expected: " + expected[i] + ")");
            if (result != expected[i]) System.err.println("   Mismatch!");
        }

        // Тест с пустой строкой
        System.out.println("\nWord '' exists: " + sol.exist(board1, "")); // false
        // Тест с null словом
        System.out.println("Word null exists: " + sol.exist(board1, null)); // false
        // Тест с null доской
        System.out.println("Word 'ABC' exists in null board: " + sol.exist(null, "ABC")); // false
    }
}

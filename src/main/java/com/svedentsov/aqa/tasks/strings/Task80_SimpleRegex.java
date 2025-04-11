package com.svedentsov.aqa.tasks.strings;

import java.util.Arrays;

/**
 * Решение задачи №80: Регулярное выражение для поиска/валидации.
 */
public class Task80_SimpleRegex {

    /**
     * Проверяет, существует ли заданное слово в 2D сетке символов (доске).
     * Слово может быть сформировано из смежных по горизонтали или вертикали ячеек.
     * Одна и та же ячейка не может использоваться более одного раза при формировании слова.
     * Использует поиск в глубину (DFS) с возвратом (backtracking).
     *
     * @param board 2D массив символов (доска).
     * @param word  Слово для поиска.
     * @return {@code true}, если слово найдено на доске, {@code false} в противном случае.
     */
    public boolean exist(char[][] board, String word) {
        // Проверки на невалидный вход
        if (board == null || board.length == 0 || board[0].length == 0 || word == null || word.isEmpty()) {
            return false;
        }

        int rows = board.length;
        int cols = board[0].length;
        char[] wordChars = word.toCharArray(); // Для удобного доступа к символам слова

        // Массив для отслеживания посещенных ячеек в ТЕКУЩЕМ пути DFS
        boolean[][] visited = new boolean[rows][cols];

        // Запускаем поиск из каждой ячейки доски как из стартовой
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Если нашли первую букву слова, начинаем DFS из этой ячейки
                if (board[i][j] == wordChars[0]) {
                    // Если DFS вернул true, значит слово найдено
                    if (dfs(board, i, j, wordChars, 0, visited)) {
                        return true;
                    }
                    // Если DFS вернул false, продолжаем поиск из других стартовых ячеек
                }
            }
        }
        // Если прошли всю доску и не нашли слово
        return false;
    }

    /**
     * Рекурсивный поиск в глубину (DFS) для нахождения слова.
     *
     * @param board     Доска.
     * @param r         Текущая строка.
     * @param c         Текущий столбец.
     * @param word      Слово для поиска в виде массива символов.
     * @param wordIndex Индекс текущего искомого символа в слове.
     * @param visited   Массив для отметки посещенных ячеек в текущем пути DFS.
     * @return {@code true}, если оставшаяся часть слова найдена, начиная с (r, c), {@code false} иначе.
     */
    private boolean dfs(char[][] board, int r, int c, char[] word, int wordIndex, boolean[][] visited) {
        // Базовый случай 1: Все символы слова найдены
        if (wordIndex == word.length) {
            return true;
        }

        // Базовый случай 2: Выход за границы доски, или ячейка уже посещена в этом пути,
        //                  или символ в ячейке не совпадает с искомым символом слова.
        if (r < 0 || r >= board.length || c < 0 || c >= board[0].length ||
                visited[r][c] || board[r][c] != word[wordIndex]) {
            return false;
        }

        // --- Шаг выбора ---
        // Отмечаем текущую ячейку как посещенную В ЭТОМ ПУТИ
        visited[r][c] = true;

        // --- Рекурсивный вызов для соседей ---
        // Ищем следующий символ слова (wordIndex + 1) в 4 соседних ячейках
        boolean found = dfs(board, r + 1, c, word, wordIndex + 1, visited) || // Вниз
                dfs(board, r - 1, c, word, wordIndex + 1, visited) || // Вверх
                dfs(board, r, c + 1, word, wordIndex + 1, visited) || // Вправо
                dfs(board, r, c - 1, word, wordIndex + 1, visited);   // Влево

        // --- Шаг отмены выбора (Backtrack) ---
        // Снимаем отметку о посещении, чтобы эту ячейку можно было использовать
        // в других путях поиска, начинающихся из других стартовых точек.
        visited[r][c] = false;

        return found; // Возвращаем результат поиска из этой ячейки
    }

    // Пример использования
    public static void main(String[] args) {
        Task80_SimpleRegex sol = new Task80_SimpleRegex();
        char[][] board1 = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        System.out.println("Board:");
        for (char[] row : board1) System.out.println(Arrays.toString(row));

        String word1 = "ABCCED";
        System.out.println("\nWord '" + word1 + "' exists: " + sol.exist(board1, word1)); // true

        String word2 = "SEE";
        System.out.println("Word '" + word2 + "' exists: " + sol.exist(board1, word2)); // true

        String word3 = "ABCB"; // Нельзя использовать B дважды подряд
        System.out.println("Word '" + word3 + "' exists: " + sol.exist(board1, word3)); // false

        String word4 = "SFDA";
        System.out.println("Word '" + word4 + "' exists: " + sol.exist(board1, word4)); // true

        String word5 = "XYZ";
        System.out.println("Word '" + word5 + "' exists: " + sol.exist(board1, word5)); // false

        char[][] board2 = {{'a', 'a'}};
        String word6 = "aaa";
        System.out.println("\nBoard: " + Arrays.deepToString(board2));
        System.out.println("Word '" + word6 + "' exists: " + sol.exist(board2, word6)); // false

        char[][] board3 = {{'a'}};
        String word7 = "a";
        System.out.println("\nBoard: " + Arrays.deepToString(board3));
        System.out.println("Word '" + word7 + "' exists: " + sol.exist(board3, word7)); // true
    }
}

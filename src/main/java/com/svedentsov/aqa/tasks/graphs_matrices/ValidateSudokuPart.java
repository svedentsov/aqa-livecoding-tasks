package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.HashSet;
import java.util.Set;

/**
 * Решение задачи №84: Проверка валидности части Судоку (строка, столбец, блок).
 * Описание: Проверить одну строку/колонку/квадрат на уникальность цифр 1-9.
 * (Проверяет: Set, работа с 2D массивами)
 * Задание: Напишите метод `boolean isSudokuRowValid(char[][] board, int row)`,
 * который проверяет, содержит ли указанная строка `row` в 9x9 поле `board`
 * (представленном `char[][]`, где '.' - пустая клетка, '1'-'9' - цифры)
 * только уникальные цифры от '1' до '9' (пустые клетки игнорируются).
 * Дополнительно: методы для проверки столбца и блока 3x3.
 */
public class ValidateSudokuPart {

    private static final int SIZE = 9; // Размерность доски Судоку
    private static final char EMPTY_CELL = '.'; // Символ пустой ячейки

    /**
     * Проверяет валидность указанной строки {@code row} на доске судоку 9x9.
     * Валидная строка не содержит повторяющихся цифр '1'-'9' и невалидных символов.
     * Пустые ячейки ('.') игнорируются.
     *
     * @param board Доска судоку 9x9 ({@code char[][]}).
     * @param row   Индекс строки для проверки (0-8).
     * @return {@code true}, если строка валидна, {@code false} иначе.
     * @throws IllegalArgumentException       если доска невалидна (null, не 9x9, содержит null строки или строки неверной длины).
     * @throws ArrayIndexOutOfBoundsException если {@code row} вне диапазона [0, 8].
     */
    public boolean isSudokuRowValid(char[][] board, int row) {
        validateSudokuBoard(board); // Проверка доски на корректность структуры
        if (row < 0 || row >= SIZE) {
            throw new ArrayIndexOutOfBoundsException("Row index " + row + " out of bounds [0, " + (SIZE - 1) + "].");
        }

        Set<Character> seenDigits = new HashSet<>();
        for (int col = 0; col < SIZE; col++) {
            // Доступ к board[row][col] безопасен после validateSudokuBoard и проверки row
            char cell = board[row][col];
            if (!isValidSudokuCell(cell, seenDigits)) {
                return false; // Невалидный символ или дубликат
            }
        }
        return true; // Строка валидна
    }

    /**
     * Проверяет валидность указанного столбца {@code col} на доске судоку 9x9.
     *
     * @param board Доска судоку 9x9.
     * @param col   Индекс столбца для проверки (0-8).
     * @return {@code true}, если столбец валиден, {@code false} иначе.
     * @throws IllegalArgumentException       если доска невалидна.
     * @throws ArrayIndexOutOfBoundsException если {@code col} вне диапазона [0, 8].
     */
    public boolean isSudokuColumnValid(char[][] board, int col) {
        validateSudokuBoard(board);
        if (col < 0 || col >= SIZE) {
            throw new ArrayIndexOutOfBoundsException("Column index " + col + " out of bounds [0, " + (SIZE - 1) + "].");
        }

        Set<Character> seenDigits = new HashSet<>();
        for (int row = 0; row < SIZE; row++) { // Итерация по строкам
            char cell = board[row][col]; // Доступ безопасен
            if (!isValidSudokuCell(cell, seenDigits)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет валидность указанного 3x3 блока (квадрата) на доске судоку.
     *
     * @param board  Доска судоку 9x9.
     * @param boxRow Индекс строки блока (0, 1 или 2). Блоки нумеруются сверху вниз.
     * @param boxCol Индекс столбца блока (0, 1 или 2). Блоки нумеруются слева направо.
     * @return {@code true}, если блок валиден, {@code false} иначе.
     * @throws IllegalArgumentException если доска невалидна или индексы блока некорректны.
     */
    public boolean isSudokuBoxValid(char[][] board, int boxRow, int boxCol) {
        validateSudokuBoard(board);
        if (boxRow < 0 || boxRow > 2 || boxCol < 0 || boxCol > 2) {
            throw new IllegalArgumentException("Box row and column indices must be between 0 and 2. Got: boxRow=" + boxRow + ", boxCol=" + boxCol);
        }

        Set<Character> seenDigits = new HashSet<>();
        int startRow = boxRow * 3;
        int startCol = boxCol * 3;

        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                char cell = board[row][col]; // Доступ безопасен
                if (!isValidSudokuCell(cell, seenDigits)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Проверяет базовую валидность структуры доски судоку (размер 9x9, не null, без null строк).
     *
     * @throws IllegalArgumentException если доска не соответствует требованиям 9x9.
     */
    private void validateSudokuBoard(char[][] board) {
        if (board == null || board.length != SIZE) {
            throw new IllegalArgumentException("Board must be non-null and have " + SIZE + " rows.");
        }
        for (int i = 0; i < SIZE; i++) {
            if (board[i] == null || board[i].length != SIZE) {
                throw new IllegalArgumentException("Row " + i + " must be non-null and have " + SIZE + " columns.");
            }
        }
    }

    /**
     * Проверяет валидность символа в ячейке судоку и его уникальность в предоставленном наборе.
     *
     * @param cell       Символ ячейки ('1'-'9' или '.').
     * @param seenDigits Set для отслеживания уже встреченных цифр в текущей проверке (строка/столбец/блок).
     * @return {@code true}, если символ является валидной цифрой '1'-'9' и еще не встречался,
     * или если это пустая ячейка '.', {@code false} в противном случае (невалидный символ или дубликат).
     */
    private boolean isValidSudokuCell(char cell, Set<Character> seenDigits) {
        if (cell == EMPTY_CELL) {
            return true; // Пустые ячейки всегда валидны в контексте уникальности
        }
        // Проверка на допустимый диапазон цифр
        if (cell < '1' || cell > '9') {
            return false; // Невалидный символ (не цифра '1'-'9' и не '.')
        }
        // Проверка на дубликат (Set.add вернет false, если элемент уже существует)
        return seenDigits.add(cell);
    }
}

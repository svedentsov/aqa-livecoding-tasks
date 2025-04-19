package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Решение задачи №84: Проверка валидности части Судоку (строка, столбец, блок).
 * <p>
 * Описание: Проверить одну строку/колонку/квадрат на уникальность цифр 1-9.
 * (Проверяет: Set, работа с 2D массивами)
 * <p>
 * Задание: Напишите метод `boolean isSudokuRowValid(char[][] board, int row)`,
 * который проверяет, содержит ли указанная строка `row` в 9x9 поле `board`
 * (представленном `char[][]`, где '.' - пустая клетка, '1'-'9' - цифры)
 * только уникальные цифры от '1' до '9' (пустые клетки игнорируются).
 * Дополнительно: методы для проверки столбца и блока 3x3.
 * <p>
 * Пример: Если `board[row]` содержит `['5','3','.','.','7','.','.','.','.']`,
 * метод вернет `true`. Если `['5','3','.','.','7','.','.','3','.']`, метод вернет `false`.
 */
public class ValidateSudokuPart {

    private static final int SIZE = 9; // Размерность доски Судоку
    private static final char EMPTY_CELL = '.'; // Символ пустой ячейки

    /**
     * Проверяет валидность указанной строки {@code row} на доске судоку 9x9.
     * Валидная строка не содержит повторяющихся цифр '1'-'9'. Пустые ячейки ('.') игнорируются.
     * Также проверяет отсутствие невалидных символов.
     *
     * @param board Доска судоку 9x9 ({@code char[][]}).
     * @param row   Индекс строки для проверки (0-8).
     * @return {@code true}, если строка валидна, {@code false} иначе.
     * @throws IllegalArgumentException       если доска невалидна (null, не 9x9).
     * @throws ArrayIndexOutOfBoundsException если {@code row} вне диапазона [0, 8].
     */
    public boolean isSudokuRowValid(char[][] board, int row) {
        validateSudokuBoard(board); // Проверка доски
        if (row < 0 || row >= SIZE) {
            throw new ArrayIndexOutOfBoundsException("Row index " + row + " out of bounds [0, " + (SIZE - 1) + "].");
        }

        Set<Character> seenDigits = new HashSet<>();
        for (int col = 0; col < SIZE; col++) {
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
     */
    public boolean isSudokuColumnValid(char[][] board, int col) {
        validateSudokuBoard(board);
        if (col < 0 || col >= SIZE) {
            throw new ArrayIndexOutOfBoundsException("Column index " + col + " out of bounds [0, " + (SIZE - 1) + "].");
        }

        Set<Character> seenDigits = new HashSet<>();
        for (int row = 0; row < SIZE; row++) { // Итерация по строкам
            char cell = board[row][col];
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
     * @param boxRow Индекс строки блока (0, 1 или 2).
     * @param boxCol Индекс столбца блока (0, 1 или 2).
     * @return {@code true}, если блок валиден, {@code false} иначе.
     */
    public boolean isSudokuBoxValid(char[][] board, int boxRow, int boxCol) {
        validateSudokuBoard(board);
        if (boxRow < 0 || boxRow > 2 || boxCol < 0 || boxCol > 2) {
            throw new IllegalArgumentException("Box indices must be between 0 and 2.");
        }

        Set<Character> seenDigits = new HashSet<>();
        int startRow = boxRow * 3;
        int startCol = boxCol * 3;

        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                char cell = board[row][col];
                if (!isValidSudokuCell(cell, seenDigits)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Точка входа для демонстрации работы методов проверки частей Судоку.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        ValidateSudokuPart sol = new ValidateSudokuPart();

        // Пример валидной доски (частично)
        char[][] validBoard = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        // Пример невалидной доски
        char[][] invalidBoard = {
                {'5', '3', '3', '.', '7', '.', '.', '.', '.'}, // Дубль '3' в строке 0 / блоке (0,1)
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '1', '.', '.', '2', '.', '.', '.', '6'}, // Дубль '1' в столбце 1
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, // '1' уже есть в столбце 1
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        // Пример доски с невалидным символом
        char[][] invalidCharBoard = Arrays.stream(validBoard).map(char[]::clone).toArray(char[][]::new);
        invalidCharBoard[4][4] = 'A'; // Ставим 'A' в центр

        System.out.println("--- Sudoku Part Validation ---");

        System.out.println("\n[Valid Board Tests]");
        runValidationTest(sol, validBoard, 0, -1, -1, "Row 0"); // true
        runValidationTest(sol, validBoard, -1, 1, -1, "Col 1"); // true
        runValidationTest(sol, validBoard, -1, -1, 0, "Box (0,0)"); // true
        runValidationTest(sol, validBoard, -1, -1, 4, "Box (1,1)"); // true

        System.out.println("\n[Invalid Board Tests]");
        runValidationTest(sol, invalidBoard, 0, -1, -1, "Inv Row 0 (Duplicate '3')"); // false
        runValidationTest(sol, invalidBoard, 5, -1, -1, "Inv Row 5 (Valid)"); // true
        runValidationTest(sol, invalidBoard, -1, 1, -1, "Inv Col 1 (Duplicate '1')"); // false
        runValidationTest(sol, invalidBoard, -1, 0, -1, "Inv Col 0 (Valid)"); // true
        runValidationTest(sol, invalidBoard, -1, -1, 0, "Inv Box (0,0) (Valid)"); // true (дубль '3' не в этом блоке)
        runValidationTest(sol, invalidBoard, -1, -1, 1, "Inv Box (0,1) (Duplicate '3')"); // false
        runValidationTest(sol, invalidBoard, -1, -1, 3, "Inv Box (1,0) (Duplicate '1')"); // false


        System.out.println("\n[Invalid Character Tests]");
        runValidationTest(sol, invalidCharBoard, 4, -1, -1, "InvChar Row 4"); // false
        runValidationTest(sol, invalidCharBoard, -1, 4, -1, "InvChar Col 4"); // false
        runValidationTest(sol, invalidCharBoard, -1, -1, 4, "InvChar Box (1,1)"); // false
    }

    /**
     * Проверяет базовую валидность доски судоку (размер 9x9, не null).
     */
    private void validateSudokuBoard(char[][] board) {
        if (board == null || board.length != SIZE) {
            throw new IllegalArgumentException("Board must be non-null and have " + SIZE + " rows.");
        }
        for (char[] row : board) {
            if (row == null || row.length != SIZE) {
                throw new IllegalArgumentException("Each row must be non-null and have " + SIZE + " columns.");
            }
        }
    }

    /**
     * Проверяет валидность отдельной ячейки и обновляет сет увиденных цифр.
     *
     * @param cell       Ячейка ('1'-'9' или '.').
     * @param seenDigits Set для проверки дубликатов.
     * @return true, если ячейка валидна и не является дубликатом, false иначе.
     */
    private boolean isValidSudokuCell(char cell, Set<Character> seenDigits) {
        if (cell == EMPTY_CELL) {
            return true; // Пустые ячейки валидны
        }
        // Проверка на допустимый диапазон цифр
        if (cell < '1' || cell > '9') {
            return false; // Невалидный символ
        }
        // Проверка на дубликат (Set.add вернет false, если элемент уже есть)
        return seenDigits.add(cell);
    }

    /**
     * Вспомогательный метод для запуска тестов валидации строки, столбца или блока.
     */
    private static void runValidationTest(ValidateSudokuPart sol, char[][] board, int row, int col, int boxIndex, String description) {
        System.out.print(description + ": ");
        boolean result = false;
        try {
            if (row != -1) {
                result = sol.isSudokuRowValid(board, row);
                System.out.println("Row " + row + " valid? " + result);
            } else if (col != -1) {
                result = sol.isSudokuColumnValid(board, col);
                System.out.println("Col " + col + " valid? " + result);
            } else if (boxIndex != -1) {
                int boxRow = boxIndex / 3;
                int boxCol = boxIndex % 3;
                result = sol.isSudokuBoxValid(board, boxRow, boxCol);
                System.out.println("Box(" + boxRow + "," + boxCol + ") valid? " + result);
            }
            // Можно добавить сравнение с ожидаемым результатом
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error - " + e.getMessage());
        }
    }
}

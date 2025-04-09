package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Решение задачи №84: Проверка валидности части Судоку (строка, столбец, блок).
 */
public class Task84_ValidateSudokuPart {

    /**
     * Проверяет, является ли указанная строка (row) на доске судоку 9x9 валидной.
     * Валидная строка содержит цифры от '1' до '9' без повторений.
     * Пустые ячейки ('.') игнорируются.
     * Также проверяет, что в строке нет других символов, кроме '.' и '1'-'9'.
     *
     * @param board Доска судоку 9x9, представленная как {@code char[][]}.
     *              Предполагается, что board не null и имеет размер 9x9.
     *              Для большей надежности можно добавить проверки внутри метода.
     * @param row   Индекс строки для проверки (от 0 до 8).
     * @return {@code true}, если строка валидна, {@code false} в противном случае
     * (дубликаты, невалидные символы или некорректный индекс строки).
     * @throws IllegalArgumentException       если доска не 9x9 или содержит null строки/столбцы (если добавить проверки).
     * @throws ArrayIndexOutOfBoundsException если row вне диапазона [0, 8].
     */
    public boolean isSudokuRowValid(char[][] board, int row) {
        // 1. Базовая проверка входных данных (можно сделать строже)
        if (!isValidSudokuBoard(board)) {
            throw new IllegalArgumentException("Invalid Sudoku board provided.");
        }
        if (row < 0 || row >= 9) {
            throw new ArrayIndexOutOfBoundsException("Row index " + row + " is out of bounds [0, 8].");
        }

        // 2. Используем Set для отслеживания цифр, встреченных в строке
        Set<Character> seenDigits = new HashSet<>();

        // 3. Проходим по всем ячейкам (столбцам) указанной строки
        for (int col = 0; col < 9; col++) {
            char cellValue = board[row][col];

            // 3.1 Игнорируем пустые ячейки '.'
            if (cellValue == '.') {
                continue;
            }

            // 3.2 Проверяем, является ли символ валидной цифрой ('1'-'9')
            if (cellValue < '1' || cellValue > '9') {
                // Невалидный символ в ячейке
                return false;
            }

            // 3.3 Проверяем на дубликат с использованием Set.
            // Метод add() возвращает false, если элемент уже существует в сете.
            if (!seenDigits.add(cellValue)) {
                // Найдена повторяющаяся цифра в строке
                return false;
            }
        }

        // 4. Если прошли всю строку без нарушений, строка валидна
        return true;
    }

    /**
     * Проверяет, является ли указанный столбец (col) на доске судоку 9x9 валидным.
     * Логика аналогична проверке строки.
     *
     * @param board Доска судоку 9x9.
     * @param col   Индекс столбца для проверки (0-8).
     * @return {@code true}, если столбец валиден, {@code false} иначе.
     */
    public boolean isSudokuColumnValid(char[][] board, int col) {
        if (!isValidSudokuBoard(board)) throw new IllegalArgumentException("Invalid Sudoku board.");
        if (col < 0 || col >= 9)
            throw new ArrayIndexOutOfBoundsException("Column index " + col + " out of bounds [0, 8].");

        Set<Character> seenDigits = new HashSet<>();
        for (int row = 0; row < 9; row++) { // Итерируем по строкам
            char cellValue = board[row][col];
            if (cellValue == '.') continue;
            if (cellValue < '1' || cellValue > '9') return false; // Невалидный символ
            if (!seenDigits.add(cellValue)) return false;      // Дубликат
        }
        return true;
    }

    /**
     * Проверяет, является ли указанный 3x3 квадрат (блок/бокс) на доске судоку валидным.
     *
     * @param board  Доска судоку 9x9.
     * @param boxRow Индекс строки блока (0, 1 или 2). Блок 0 - строки 0-2, Блок 1 - строки 3-5, Блок 2 - строки 6-8.
     * @param boxCol Индекс столбца блока (0, 1 или 2). Блок 0 - столбцы 0-2, Блок 1 - столбцы 3-5, Блок 2 - столбцы 6-8.
     * @return {@code true}, если блок валиден, {@code false} иначе.
     */
    public boolean isSudokuBoxValid(char[][] board, int boxRow, int boxCol) {
        if (!isValidSudokuBoard(board)) throw new IllegalArgumentException("Invalid Sudoku board.");
        if (boxRow < 0 || boxRow > 2 || boxCol < 0 || boxCol > 2) {
            throw new IllegalArgumentException("Box indices must be between 0 and 2.");
        }

        Set<Character> seenDigits = new HashSet<>();
        // Вычисляем начальные индексы строки и столбца для блока
        int startRow = boxRow * 3;
        int startCol = boxCol * 3;

        // Итерируем по ячейкам блока 3x3
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                char cellValue = board[row][col];
                if (cellValue == '.') continue;
                if (cellValue < '1' || cellValue > '9') return false; // Невалидный символ
                if (!seenDigits.add(cellValue)) return false;      // Дубликат
            }
        }
        return true;
    }

    /**
     * Вспомогательный метод для базовой проверки размеров доски судоку.
     *
     * @param board Доска для проверки.
     * @return true, если доска не null, имеет 9 строк и 9 столбцов (проверяется только первая строка).
     */
    private boolean isValidSudokuBoard(char[][] board) {
        // Добавим проверку на null строк внутри
        if (board == null || board.length != 9) return false;
        for (char[] row : board) {
            if (row == null || row.length != 9) return false;
        }
        return true;
        // Старая проверка: return board != null && board.length == 9 && board[0] != null && board[0].length == 9;
    }

    /**
     * Точка входа для демонстрации работы методов проверки частей Судоку.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task84_ValidateSudokuPart sol = new Task84_ValidateSudokuPart();
        // Пример частично заполненной валидной доски
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
                {'5', '3', '3', '.', '7', '.', '.', '.', '.'}, // Ошибка в строке 0, блоке (0,0)
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '1', '.', '.', '2', '.', '.', '.', '6'}, // '1' - ошибка в столбце 1
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        System.out.println("--- Checking Valid Board ---");
        System.out.println("Row 0 valid: " + sol.isSudokuRowValid(validBoard, 0)); // true
        System.out.println("Column 1 valid: " + sol.isSudokuColumnValid(validBoard, 1)); // true
        System.out.println("Box (0,0) valid: " + sol.isSudokuBoxValid(validBoard, 0, 0)); // true
        System.out.println("Box (1,1) valid: " + sol.isSudokuBoxValid(validBoard, 1, 1)); // true

        System.out.println("\n--- Checking Invalid Board ---");
        System.out.println("Row 0 valid: " + sol.isSudokuRowValid(invalidBoard, 0)); // false (дубль 3)
        System.out.println("Row 5 valid: " + sol.isSudokuRowValid(invalidBoard, 5)); // true (эта строка валидна)
        System.out.println("Column 1 valid: " + sol.isSudokuColumnValid(invalidBoard, 1)); // false (дубль 1)
        System.out.println("Column 0 valid: " + sol.isSudokuColumnValid(invalidBoard, 0)); // true (этот столбец валиден)
        System.out.println("Box (0,0) valid: " + sol.isSudokuBoxValid(invalidBoard, 0, 0)); // false (дубль 6?) Нет, дубль 3 из строки 0.
        System.out.println("Box (1,1) valid: " + sol.isSudokuBoxValid(invalidBoard, 1, 1)); // true (этот блок валиден)


        // Тест на невалидный символ
        char[][] invalidCharBoard = Arrays.stream(validBoard).map(char[]::clone).toArray(char[][]::new);
        invalidCharBoard[4][4] = 'A';
        System.out.println("\n--- Checking Invalid Character ---");
        System.out.println("Row 4 valid (with 'A'): " + sol.isSudokuRowValid(invalidCharBoard, 4)); // false
        System.out.println("Column 4 valid (with 'A'): " + sol.isSudokuColumnValid(invalidCharBoard, 4)); // false
        System.out.println("Box (1,1) valid (with 'A'): " + sol.isSudokuBoxValid(invalidCharBoard, 1, 1)); // false
    }
}

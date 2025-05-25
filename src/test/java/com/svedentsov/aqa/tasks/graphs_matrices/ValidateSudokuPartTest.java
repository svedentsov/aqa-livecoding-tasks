package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для ValidateSudokuPart (проверка частей Судоку)")
class ValidateSudokuPartTest {

    private ValidateSudokuPart validator;

    // Стандартное валидное (частично) поле для тестов
    private static final char[][] VALID_BOARD = {
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

    // Вспомогательный метод для глубокого копирования доски
    private char[][] deepCopy(char[][] original) {
        if (original == null) return null;
        return Arrays.stream(original).map(char[]::clone).toArray(char[][]::new);
    }

    @BeforeEach
    void setUp() {
        validator = new ValidateSudokuPart();
    }

    @Nested
    @DisplayName("Тесты валидации структуры доски (через вызов public методов)")
    class BoardStructureValidationTests {

        @Test
        @DisplayName("Null доска должна выбрасывать IllegalArgumentException")
        void validateBoard_nullBoard_throwsException() {
            assertThrows(IllegalArgumentException.class, () -> validator.isSudokuRowValid(null, 0));
            assertThrows(IllegalArgumentException.class, () -> validator.isSudokuColumnValid(null, 0));
            assertThrows(IllegalArgumentException.class, () -> validator.isSudokuBoxValid(null, 0, 0));
        }

        @Test
        @DisplayName("Доска с неверным количеством строк выбрасывает IllegalArgumentException")
        void validateBoard_wrongRowCount_throwsException() {
            char[][] board = new char[8][9];
            assertThrows(IllegalArgumentException.class, () -> validator.isSudokuRowValid(board, 0));
        }

        @Test
        @DisplayName("Доска с null строкой выбрасывает IllegalArgumentException")
        void validateBoard_nullRow_throwsException() {
            char[][] board = new char[9][];
            board[0] = new char[9]; // одна валидная строка
            // board[1] будет null
            assertThrows(IllegalArgumentException.class, () -> validator.isSudokuRowValid(board, 0)); // Проверка до validateSudokuBoard
        }

        @Test
        @DisplayName("Доска со строкой неверной длины выбрасывает IllegalArgumentException")
        void validateBoard_wrongColCountInRow_throwsException() {
            char[][] board = new char[9][9];
            for(int i=0; i < 9; i++) board[i] = new char[9]; // Инициализация всех строк
            board[3] = new char[8]; // Одна строка неверной длины
            assertThrows(IllegalArgumentException.class, () -> validator.isSudokuRowValid(board, 0));
        }
    }

    @Nested
    @DisplayName("Тесты для isSudokuRowValid")
    class IsSudokuRowValidTests {
        char[][] testBoard;

        @BeforeEach
        void initBoard() {
            testBoard = deepCopy(VALID_BOARD);
        }

        @Test
        @DisplayName("Валидная строка (уникальные цифры и пустые ячейки)")
        void validRow() {
            assertTrue(validator.isSudokuRowValid(testBoard, 0)); // "53..7...."
            assertTrue(validator.isSudokuRowValid(testBoard, 1)); // "6..195..."
        }

        @Test
        @DisplayName("Строка с дубликатом цифры")
        void rowWithDuplicateDigit() {
            testBoard[0][3] = '3'; // "53.37...." - теперь есть дубликат '3'
            assertFalse(validator.isSudokuRowValid(testBoard, 0));
        }

        @Test
        @DisplayName("Строка с невалидным символом")
        void rowWithInvalidCharacter() {
            testBoard[0][2] = 'A'; // "53A.7...."
            assertFalse(validator.isSudokuRowValid(testBoard, 0));
        }

        @Test
        @DisplayName("Строка, полностью состоящая из пустых ячеек")
        void rowWithAllEmptyCells() {
            Arrays.fill(testBoard[0], '.'); // "........."
            assertTrue(validator.isSudokuRowValid(testBoard, 0));
        }

        @Test
        @DisplayName("Строка, полностью состоящая из уникальных цифр")
        void rowWithAllUniqueDigits() {
            testBoard[0] = new char[]{'1','2','3','4','5','6','7','8','9'};
            assertTrue(validator.isSudokuRowValid(testBoard, 0));
        }

        @ParameterizedTest(name = "Индекс строки {0} вне диапазона")
        @ValueSource(ints = {-1, 9, 100})
        @DisplayName("Невалидный индекс строки выбрасывает ArrayIndexOutOfBoundsException")
        void invalidRowIndex_throwsException(int rowIndex) {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> validator.isSudokuRowValid(testBoard, rowIndex));
        }
    }

    @Nested
    @DisplayName("Тесты для isSudokuColumnValid")
    class IsSudokuColumnValidTests {
        char[][] testBoard;
        @BeforeEach
        void initBoard() {
            testBoard = deepCopy(VALID_BOARD);
        }

        @Test
        @DisplayName("Валидный столбец")
        void validColumn() {
            assertTrue(validator.isSudokuColumnValid(testBoard, 0)); // 5,6,.,8,4,7,.,.,.
            assertTrue(validator.isSudokuColumnValid(testBoard, 8)); // .,.,.,3,1,6,.,5,9
        }

        @Test
        @DisplayName("Столбец с дубликатом цифры")
        void columnWithDuplicateDigit() {
            testBoard[2][0] = '6'; // Столбец 0 теперь: 5,6,6,... - дубликат '6'
            assertFalse(validator.isSudokuColumnValid(testBoard, 0));
        }

        @Test
        @DisplayName("Столбец с невалидным символом")
        void columnWithInvalidCharacter() {
            testBoard[0][0] = 'X';
            assertFalse(validator.isSudokuColumnValid(testBoard, 0));
        }

        @ParameterizedTest(name = "Индекс столбца {0} вне диапазона")
        @ValueSource(ints = {-1, 9, 10})
        @DisplayName("Невалидный индекс столбца выбрасывает ArrayIndexOutOfBoundsException")
        void invalidColumnIndex_throwsException(int colIndex) {
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> validator.isSudokuColumnValid(testBoard, colIndex));
        }
    }

    @Nested
    @DisplayName("Тесты для isSudokuBoxValid")
    class IsSudokuBoxValidTests {
        char[][] testBoard;
        @BeforeEach
        void initBoard() {
            testBoard = deepCopy(VALID_BOARD);
        }

        // boxRow, boxCol, expected
        static Stream<Arguments> boxValidationCases() {
            return Stream.of(
                    Arguments.of(0, 0, true, "Box (0,0) VALID_BOARD"), // 53. 6.. .98
                    Arguments.of(0, 1, true, "Box (0,1) VALID_BOARD"), // .7. 195 ...
                    Arguments.of(1, 1, true, "Box (1,1) VALID_BOARD")  // .6. 8.3 .2.
            );
        }
        @ParameterizedTest(name = "{3} - box({0},{1}) -> {2}")
        @MethodSource("boxValidationCases")
        @DisplayName("Проверка валидных блоков")
        void validBox(int boxRow, int boxCol, boolean expected, String description) {
            assertEquals(expected, validator.isSudokuBoxValid(testBoard, boxRow, boxCol), description);
        }

        @Test
        @DisplayName("Блок с дубликатом цифры")
        void boxWithDuplicateDigit() {
            // Box (0,0) содержит 5,3,.,6,.,.,.,9,8
            testBoard[1][1] = '5'; // Ставим '5' туда, где была '.', теперь в Box (0,0) две '5'
            assertFalse(validator.isSudokuBoxValid(testBoard, 0, 0));
        }

        @Test
        @DisplayName("Блок с невалидным символом")
        void boxWithInvalidCharacter() {
            testBoard[0][0] = '#';
            assertFalse(validator.isSudokuBoxValid(testBoard, 0, 0));
        }

        @ParameterizedTest(name = "Индексы блока ({0},{1}) вне диапазона")
        @CsvSource({
                "-1, 0", "0, -1", "3, 0", "0, 3", "3,3"
        })
        @DisplayName("Невалидные индексы блока выбрасывают IllegalArgumentException")
        void invalidBoxIndices_throwsException(int boxRow, int boxCol) {
            assertThrows(IllegalArgumentException.class, () -> validator.isSudokuBoxValid(testBoard, boxRow, boxCol));
        }
    }
}

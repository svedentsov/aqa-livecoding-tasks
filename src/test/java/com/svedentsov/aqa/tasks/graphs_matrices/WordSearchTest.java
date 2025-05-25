package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("Тесты для WordSearch (Поиск слова в матрице)")
class WordSearchTest {

    private WordSearch wordSearch;

    @BeforeEach
    void setUp() {
        wordSearch = new WordSearch();
    }

    // Вспомогательный метод для глубокого копирования 2D массива char
    private char[][] deepCopyBoard(char[][] originalBoard) {
        if (originalBoard == null) return null;
        return Arrays.stream(originalBoard).map(char[]::clone).toArray(char[][]::new);
    }

    static Stream<Arguments> provideBoardAndWordCases() {
        char[][] board1 = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        // A(0,0), B(0,1), C_top(0,2), E_top_right(0,3)
        // S_left(1,0), F(1,1), C_middle(1,2), S_right(1,3)
        // A_bottom(2,0), D(2,1), E_mid(2,2), E_bottom_right(2,3)

        char[][] board2 = {{'a', 'a'}};
        char[][] board3 = {{'a'}};
        char[][] board4 = {{'C', 'A', 'A'}, {'A', 'A', 'A'}, {'B', 'C', 'D'}};


        return Stream.of(
                // Тесты для board1
                Arguments.of(board1, "ABCCED", true, "board1: ABCCED (A(0,0)B(0,1)C_top(0,2)C_mid(1,2)E_mid(2,2)D(2,1))"),
                Arguments.of(board1, "SEE", true, "board1: SEE (S_right(1,3)E_bottom_right(2,3)E_mid(2,2))"),
                Arguments.of(board1, "ABCB", false, "board1: ABCB (B(0,1) не может быть использован дважды для этого пути)"),
                Arguments.of(board1, "SFDA", true, "board1: SFDA (S_left(1,0)F(1,1)D(2,1)A_bottom(2,0))"),
                Arguments.of(board1, "ASAD", true, "board1: ASAD (A(0,0)S_left(1,0)A_bottom(2,0)D(2,1))"),
                Arguments.of(board1, "ADEE", true, "board1: ADEE (A_bottom(2,0)D(2,1)E_mid(2,2)E_bottom_right(2,3))"),
                Arguments.of(board1, "XYZ", false, "board1: XYZ (не найдено)"),
                Arguments.of(board1, "S", true, "board1: S (одна буква, найдено)"),
                Arguments.of(board1, "AE", false, "board1: AE (нет прямого пути для двух букв)"),
                Arguments.of(board1, "ACE", false, "board1: ACE (нет прямого пути)"),

                // Тесты для board2
                Arguments.of(board2, "aaa", false, "board2 {{a,a}}: aaa (недостаточно 'a')"),
                Arguments.of(board2, "aa", true, "board2 {{a,a}}: aa"),

                // Тесты для board3
                Arguments.of(board3, "a", true, "board3 {{a}}: a"),
                Arguments.of(board3, "b", false, "board3 {{a}}: b (не найдено)"),

                // Тесты для board4
                Arguments.of(board4, "AAB", true, "board4: AAB (например, A(0,1)A(1,1)B(2,0))"),

                // Общие случаи
                Arguments.of(new char[][]{{'A'}}, "A", true, "Доска из одного элемента, слово найдено"),
                Arguments.of(new char[][]{{'A'}}, "B", false, "Доска из одного элемента, слово не найдено"),
                Arguments.of(new char[][]{{'A', 'B'}, {'C', 'D'}}, "AC", true, "Доска 2x2: AC (вниз)"),
                Arguments.of(new char[][]{{'A', 'B'}, {'C', 'D'}}, "DB", true, "Доска 2x2: DB (вверх, от D к B)"),
                Arguments.of(new char[][]{{'A', 'A', 'A'}, {'A', 'B', 'A'}, {'A', 'A', 'A'}}, "AB", true, "B окружена A"),
                Arguments.of(new char[][]{{'A', 'B', 'C'}, {'D', 'E', 'F'}, {'G', 'H', 'I'}}, "AEI", false, "3x3: AEI (нет прямого пути)"),
                Arguments.of(new char[][]{{'A', 'B', 'C'}, {'S', 'E', 'I'}, {'G', 'H', 'F'}}, "ASEI", true, "3x3: ASEI (A(0,0)S(1,0)E(1,1)I(1,2))"),


                // Слово длиннее доски
                Arguments.of(new char[][]{{'a', 'b'}, {'c', 'd'}}, "abcde", false, "Слово длиннее доски")
        );
    }

    @ParameterizedTest(name = "{3}: word=\"{1}\" -> {2}")
    @MethodSource("provideBoardAndWordCases")
    @DisplayName("Проверка существования слова на доске")
    void testExist(char[][] boardSource, String word, boolean expected, String description) {
        // Глубокое копирование доски, так как метод exist ее модифицирует
        char[][] boardCopy = deepCopyBoard(boardSource);
        assertEquals(expected, wordSearch.exist(boardCopy, word), description);
    }

    @Test
    @DisplayName("Проверка с null доской")
    void testExist_nullBoard_shouldReturnFalse() {
        assertFalse(wordSearch.exist(null, "WORD"));
    }

    @Test
    @DisplayName("Проверка с доской нулевой длины")
    void testExist_emptyBoardArray_shouldReturnFalse() {
        assertFalse(wordSearch.exist(new char[0][0], "WORD"));
    }

    @Test
    @DisplayName("Проверка с доской, где первая строка null (board[0] == null)")
    void testExist_firstRowNull_shouldReturnFalse() {
        char[][] boardWithNullFirstRow = new char[1][]; // board[0] is null by default
        assertFalse(wordSearch.exist(boardWithNullFirstRow, "A"));
    }

    @Test
    @DisplayName("Проверка с доской, где первая строка пустая (board[0].length == 0)")
    void testExist_firstRowEmpty_shouldReturnFalse() {
        char[][] boardWithEmptyFirstRow = {{}}; // board[0] is new char[0]
        assertFalse(wordSearch.exist(boardWithEmptyFirstRow, "A"));
        char[][] boardWithEmptyFirstRow2 = new char[1][0]; // board[0] is new char[0]
        assertFalse(wordSearch.exist(boardWithEmptyFirstRow2, "A"));
    }

    @Test
    @DisplayName("Проверка с null словом")
    void testExist_nullWord_shouldReturnFalse() {
        char[][] board = {{'A'}};
        assertFalse(wordSearch.exist(board, null));
    }

    @Test
    @DisplayName("Проверка с пустым словом")
    void testExist_emptyWord_shouldReturnFalse() {
        char[][] board = {{'A'}};
        assertFalse(wordSearch.exist(board, ""));
    }
}

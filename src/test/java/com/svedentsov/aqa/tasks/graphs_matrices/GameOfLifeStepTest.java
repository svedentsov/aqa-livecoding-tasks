package com.svedentsov.aqa.tasks.graphs_matrices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Тесты для GameOfLifeStep (следующее поколение Игры \"Жизнь\")")
class GameOfLifeStepTest {

    private GameOfLifeStep game;

    @BeforeEach
    void setUp() {
        game = new GameOfLifeStep();
    }

    // Вспомогательный метод для сравнения 2D массивов
    private void assertBoardEquals(int[][] expected, int[][] actual, String message) {
        assertTrue(Arrays.deepEquals(expected, actual), message +
                "\nExpected:\n" + boardToString(expected) +
                "Actual:\n" + boardToString(actual));
    }

    // Вспомогательный метод для красивого вывода доски в сообщении об ошибке
    private String boardToString(int[][] board) {
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            sb.append(Arrays.toString(row)).append("\n");
        }
        return sb.toString();
    }

    @Nested
    @DisplayName("Тесты для известных паттернов")
    class PatternTests {

        @Test
        @DisplayName("Блинкер (осциллятор, период 2)")
        void blinkerOscillator() {
            int[][] blinkerGen0 = {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            };
            int[][] blinkerGen1 = {
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            };
            int[][] next = game.nextGeneration(blinkerGen0);
            assertBoardEquals(blinkerGen1, next, "Блинкер: Gen 0 -> Gen 1");

            next = game.nextGeneration(next); // next is now blinkerGen1
            assertBoardEquals(blinkerGen0, next, "Блинкер: Gen 1 -> Gen 0 (Gen 2)");
        }

        @Test
        @DisplayName("Блок (стабильная фигура)")
        void blockStillLife() {
            int[][] block = {
                    {0, 0, 0, 0},
                    {0, 1, 1, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0}
            };
            int[][] next = game.nextGeneration(block);
            assertBoardEquals(block, next, "Блок должен оставаться стабильным");
        }

        @Test
        @DisplayName("Планер (космический корабль)")
        void gliderSpaceship() {
            int[][] gliderGen0 = {
                    {0, 1, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {1, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            };
            // Ожидаемые состояния для планера (ручной расчет или из источника)
            // Gen 1:
            // . . . . .
            // 1 . 1 . .
            // . 1 1 . .
            // . 1 . . .
            // . . . . .
            int[][] gliderGen1_expected = {
                    {0, 0, 0, 0, 0},
                    {1, 0, 1, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0}
            };
            // Gen 2:
            // . . . . .
            // . . 1 . .
            // 1 . 1 . .
            // . 1 1 . .
            // . . . . .
            int[][] gliderGen2_expected = {
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {1, 0, 1, 0, 0},
                    {0, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0}
            };

            int[][] gliderGen1_actual = game.nextGeneration(gliderGen0);
            assertBoardEquals(gliderGen1_expected, gliderGen1_actual, "Планер: Gen 0 -> Gen 1");

            int[][] gliderGen2_actual = game.nextGeneration(gliderGen1_actual);
            assertBoardEquals(gliderGen2_expected, gliderGen2_actual, "Планер: Gen 1 -> Gen 2");
        }
    }

    @Nested
    @DisplayName("Тесты для правил игры")
    class RulesTests {
        // 1. Живая (1) с < 2 живыми соседями -> умирает (0).
        @Test
        @DisplayName("Живая клетка с 0 соседями умирает")
        void liveCell_zeroNeighbors_dies() {
            int[][] board = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
            int[][] expected = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
            assertBoardEquals(expected, game.nextGeneration(board), "Живая с 0 соседями");
        }

        @Test
        @DisplayName("Живая клетка с 1 соседом умирает")
        void liveCell_oneNeighbor_dies() {
            int[][] board = {{0, 1, 0}, {0, 1, 0}, {0, 0, 0}}; // центральная 1, сосед сверху 1
            int[][] expected = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
            assertBoardEquals(expected, game.nextGeneration(board), "Живая с 1 соседом");
        }

        @Test
        @DisplayName("Живая клетка с 3 соседями выживает")
        void liveCell_threeNeighbors_survives() {
            int[][] board = {{1, 1, 0}, {1, 1, 0}, {0, 0, 0}}; // центральная (1,1) имеет 3 соседа
            int[][] expected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 0}}; // блок 2х2 стабилен
            assertBoardEquals(expected, game.nextGeneration(board), "Живая с 3 соседями");
        }

        // 3. Живая (1) с > 3 живыми соседями -> умирает (0).
        @Test
        @DisplayName("Живая клетка с 4 соседями умирает (перенаселение)")
        void liveCell_fourNeighbors_dies() {
            int[][] board = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}; // центральная (1,1) имеет 8 соседей. Она умрет.
            int[][] expected = {{1, 0, 1}, {0, 0, 0}, {1, 0, 1}};
            assertBoardEquals(expected, game.nextGeneration(board), "Живая с 8 соседями");
        }

        // 4. Мертвая (0) с ровно 3 живыми соседями -> оживает (1).
        @Test
        @DisplayName("Мертвая клетка с 3 живыми соседями оживает")
        void deadCell_threeNeighbors_becomesAlive() {
            int[][] board = {{1, 1, 0}, {1, 0, 0}, {0, 0, 0}}; // (1,1) мертва, 3 живых соседа
            int[][] expected = {{1, 1, 0}, {1, 1, 0}, {0, 0, 0}}; // (1,1) оживает. (0,0),(0,1),(1,0) остаются живы с 2-3 соседями
            assertBoardEquals(expected, game.nextGeneration(board), "Мертвая с 3 соседями");
        }

        @Test
        @DisplayName("Мертвая клетка с 2 живыми соседями остается мертвой")
        void deadCell_twoNeighbors_staysDead() {
            int[][] board = {{1, 1, 0}, {0, 0, 0}, {0, 0, 0}}; // (1,0) мертва, 2 живых соседа
            int[][] expected = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
            assertBoardEquals(expected, game.nextGeneration(board), "Мертвая с 2 соседями");
        }
    }

    @Nested
    @DisplayName("Тесты для граничных и невалидных случаев доски")
    class EdgeAndInvalidBoardTests {
        @Test
        @DisplayName("Пустая доска (0x0) возвращает пустую доску")
        void emptyBoard_0x0_returnsEmptyBoard() {
            int[][] board = new int[0][0];
            int[][] next = game.nextGeneration(board);
            assertEquals(0, next.length, "Пустая доска должна вернуть 0 строк");
        }

        @Test
        @DisplayName("Пустая доска (0xN) возвращает пустую доску")
        void emptyBoard_0xN_returnsEmptyBoard() {
            int[][] board = new int[0][5];
            int[][] next = game.nextGeneration(board);
            assertEquals(0, next.length, "Пустая доска (0xN) должна вернуть 0 строк");
        }

        @Test
        @DisplayName("Пустая доска (Nx0) возвращает пустую доску")
        void emptyBoard_Nx0_returnsEmptyBoard() {
            int[][] board = new int[5][0];
            int[][] next = game.nextGeneration(board);
            // nextGeneration вернет new int[0][0] по текущей логике,
            // т.к. board[0].length будет 0, что вызовет возврат new int[0][0]
            assertEquals(0, next.length);
            // Если бы он возвращал new int[5][0], то это бы тоже было логично.
            // Но текущая имплементация из-за board[0].length == 0 ведет себя так.
        }

        @Test
        @DisplayName("Доска null возвращает пустую доску")
        void nullBoard_returnsEmptyBoard() {
            int[][] next = game.nextGeneration(null);
            assertEquals(0, next.length, "Null доска должна вернуть 0 строк");
        }

        @Test
        @DisplayName("Доска, где первая строка null, возвращает пустую доску")
        void boardWithFirstRowNull_returnsEmptyBoard() {
            int[][] board = new int[1][]; // board[0] is null
            int[][] next = game.nextGeneration(board);
            assertEquals(0, next.length);
        }

        @Test
        @DisplayName("Доска полностью из мертвых клеток остается мертвой")
        void allDeadBoard_staysAllDead() {
            int[][] board = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
            int[][] next = game.nextGeneration(board);
            assertBoardEquals(board, next, "Полностью мертвая доска остается мертвой");
        }

        @Test
        @DisplayName("Доска полностью из живых клеток (3x3) -> специфичный паттерн")
        void allLiveBoard_3x3_becomesPattern() {
            int[][] board = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
            int[][] expected = {{1, 0, 1}, {0, 0, 0}, {1, 0, 1}}; // Углы выживают, центр и стороны умирают
            assertBoardEquals(expected, game.nextGeneration(board), "Полностью живая доска 3x3");
        }
    }
}

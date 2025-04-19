package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Решение задачи №90: Игра "Жизнь" Конвея (один шаг).
 * <p>
 * Описание: Рассчитать следующее состояние для одной клетки или небольшой матрицы.
 * (Проверяет: работа с 2D массивами, логика)
 * <p>
 * Задание: Напишите метод `int[][] nextGeneration(int[][] board)`, который
 * принимает 2D массив `board` (0 - мертвая клетка, 1 - живая) и вычисляет
 * следующее состояние поля согласно правилам игры "Жизнь" Конвея. Верните новое поле.
 * <p>
 * Правила:
 * 1. Живая (1) с < 2 живыми соседями -> умирает (0).
 * 2. Живая (1) с 2 или 3 живыми соседями -> выживает (1).
 * 3. Живая (1) с > 3 живыми соседями -> умирает (0).
 * 4. Мертвая (0) с ровно 3 живыми соседями -> оживает (1).
 * <p>
 * Пример: Показать небольшой пример 3x3 поля и его состояние на следующем шаге.
 */
public class GameOfLifeStep {

    /**
     * Вычисляет следующее поколение (состояние) игрового поля для игры "Жизнь" Конвея.
     * Применяет правила к каждой клетке на основе её 8 соседей (по горизонтали,
     * вертикали и диагонали). Границы поля считаются мертвыми.
     * Возвращает новое поле, не изменяя исходное.
     *
     * @param board Текущее состояние поля (0 - мертвая, 1 - живая).
     *              Предполагается, что массив не null и прямоугольный.
     * @return Новый 2D массив {@code int[][]}, представляющий состояние поля
     * в следующем поколении. Возвращает пустой массив {@code int[0][0]},
     * если исходный {@code board} null или пуст.
     */
    public int[][] nextGeneration(int[][] board) {
        // Проверка входных данных
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return new int[0][0]; // Или можно вернуть null/бросить исключение
        }

        int rows = board.length;
        int cols = board[0].length;
        // Новое поле для следующего поколения
        int[][] nextBoard = new int[rows][cols];

        // Итерация по всем клеткам
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Подсчет живых соседей
                int liveNeighbors = countLiveNeighbors(board, r, c);
                int currentState = board[r][c];
                int nextState = 0; // По умолчанию клетка мертва

                // Применение правил "Жизни"
                if (currentState == 1) { // Если клетка жива
                    if (liveNeighbors == 2 || liveNeighbors == 3) {
                        nextState = 1; // Выживает
                    } // Иначе умирает (nextState остается 0)
                } else { // Если клетка мертва
                    if (liveNeighbors == 3) {
                        nextState = 1; // Оживает
                    } // Иначе остается мертвой (nextState остается 0)
                }
                nextBoard[r][c] = nextState; // Записываем новое состояние
            }
        }
        return nextBoard;
    }

    /**
     * Точка входа для демонстрации вычисления следующего шага игры "Жизнь".
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        GameOfLifeStep sol = new GameOfLifeStep();

        System.out.println("--- Conway's Game of Life - Next Generation ---");

        // Пример 1: Блинкер (осциллятор)
        int[][] blinker0 = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0}
        };
        runGameOfLifeTest(sol, blinker0, "Blinker - Gen 0");
        int[][] blinker1 = sol.nextGeneration(blinker0);
        runGameOfLifeTest(sol, blinker1, "Blinker - Gen 1");
        int[][] blinker2 = sol.nextGeneration(blinker1);
        runGameOfLifeTest(sol, blinker2, "Blinker - Gen 2"); // Должен совпасть с Gen 0

        // Пример 2: Блок (стабильная фигура)
        int[][] block = {
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 0}
        };
        runGameOfLifeTest(sol, block, "Block - Gen 0");
        int[][] block1 = sol.nextGeneration(block);
        runGameOfLifeTest(sol, block1, "Block - Gen 1"); // Должен быть таким же

        // Пример 3: Планер (движется)
        int[][] glider0 = {
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        runGameOfLifeTest(sol, glider0, "Glider - Gen 0");
        int[][] glider1 = sol.nextGeneration(glider0);
        runGameOfLifeTest(sol, glider1, "Glider - Gen 1");
        int[][] glider2 = sol.nextGeneration(glider1);
        runGameOfLifeTest(sol, glider2, "Glider - Gen 2");
        int[][] glider3 = sol.nextGeneration(glider2);
        runGameOfLifeTest(sol, glider3, "Glider - Gen 3");

        // Пример 4: Пустое поле
        runGameOfLifeTest(sol, new int[3][3], "Empty Board");
    }

    /**
     * Вспомогательный метод для подсчета живых соседей клетки (r, c).
     * Учитывает 8 соседей, клетки за границами считаются мертвыми.
     *
     * @param board Игровое поле.
     * @param r     Строка текущей клетки.
     * @param c     Столбец текущей клетки.
     * @return Количество живых соседей (0-8).
     */
    private int countLiveNeighbors(int[][] board, int r, int c) {
        int rows = board.length;
        int cols = board[0].length;
        int liveCount = 0;

        // Перебираем всех 8 соседей, включая диагональные
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                // Пропускаем саму клетку (r, c)
                if (i == r && j == c) {
                    continue;
                }
                // Проверяем, находится ли сосед (i, j) в пределах поля
                if (i >= 0 && i < rows && j >= 0 && j < cols) {
                    // Если сосед живой (равен 1), увеличиваем счетчик
                    if (board[i][j] == 1) {
                        liveCount++;
                    }
                }
            }
        }
        return liveCount;
    }

    /**
     * Вспомогательный метод для красивой печати поля (0 как '.', 1 как '#').
     */
    private static void printBoard(String title, int[][] board) {
        System.out.println(title + ":");
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            System.out.println("Empty or null board.");
            return;
        }
        for (int[] row : board) {
            String rowStr = Arrays.stream(row)
                    .mapToObj(cell -> cell == 1 ? "#" : ".")
                    .collect(Collectors.joining(" "));
            System.out.println("  " + rowStr);
        }
    }

    /**
     * Вспомогательный метод для тестирования одного шага Игры Жизнь.
     *
     * @param sol          Экземпляр решателя.
     * @param currentBoard Текущее состояние доски.
     * @param description  Описание теста.
     */
    private static void runGameOfLifeTest(GameOfLifeStep sol, int[][] currentBoard, String description) {
        System.out.println("\n--- " + description + " ---");
        printBoard("Current Generation", currentBoard);
        try {
            int[][] nextBoard = sol.nextGeneration(currentBoard);
            printBoard("Next Generation", nextBoard);
        } catch (Exception e) {
            System.err.println("Error calculating next generation: " + e.getMessage());
        }
    }
}

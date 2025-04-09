package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Решение задачи №90: Игра "Жизнь" Конвея (один шаг).
 */
public class Task90_GameOfLifeStep {

    /**
     * Вычисляет следующее поколение (состояние) игрового поля для игры "Жизнь" Конвея,
     * применяя стандартные правила к каждой клетке на основе её 8 соседей.
     * Правила:
     * 1. Живая клетка (1) с менее чем двумя живыми соседями умирает (от одиночества).
     * 2. Живая клетка (1) с двумя или тремя живыми соседями выживает до следующего поколения.
     * 3. Живая клетка (1) с более чем тремя живыми соседями умирает (от перенаселения).
     * 4. Мертвая клетка (0) с ровно тремя живыми соседями оживает (становится живой).
     * Границы поля считаются мертвыми (незацикленное поле).
     * Результат вычислений записывается в новый массив, чтобы изменения состояния
     * одних клеток не влияли на расчеты для других клеток в том же поколении.
     *
     * @param board Текущее состояние игрового поля, где 0 - мертвая клетка, 1 - живая.
     *              Предполагается, что массив не null и прямоугольный.
     * @return Новый 2D массив {@code int[][]}, представляющий состояние поля в следующем поколении.
     * Возвращает пустой массив {@code int[0][0]}, если исходный {@code board} null или пуст.
     */
    public int[][] nextGeneration(int[][] board) {
        // Проверка на невалидное входное поле
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) {
            return new int[0][0];
        }

        int rows = board.length;
        int cols = board[0].length;
        // Создаем новое поле для хранения следующего состояния,
        // инициализированное нулями (все клетки изначально мертвы).
        int[][] nextBoard = new int[rows][cols];

        // Итерация по каждой клетке текущего поля board[i][j]
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 1. Подсчитываем количество живых соседей для клетки (i, j)
                int liveNeighbors = countLiveNeighbors(board, i, j);

                // 2. Получаем текущее состояние клетки
                int currentState = board[i][j];

                // 3. Применяем правила игры "Жизнь" для определения нового состояния
                // Если клетка живая (currentState == 1)
                if (currentState == 1) {
                    // Условие выживания: 2 или 3 живых соседа
                    if (liveNeighbors == 2 || liveNeighbors == 3) {
                        nextBoard[i][j] = 1; // Клетка выживает
                    }
                    // Иначе (liveNeighbors < 2 или > 3) клетка умирает (остается 0 в nextBoard).
                }
                // Если клетка мертвая (currentState == 0)
                else {
                    // Условие оживления: ровно 3 живых соседа
                    if (liveNeighbors == 3) {
                        nextBoard[i][j] = 1; // Клетка оживает
                    }
                    // Иначе остается мертвой (остается 0 в nextBoard).
                }
            }
        }

        // 4. Возвращаем поле следующего поколения
        return nextBoard;
    }

    /**
     * Вспомогательный метод для подсчета количества живых соседей (значение 1)
     * вокруг клетки с координатами (r, c) на поле {@code board}.
     * Учитывает 8 соседей (по горизонтали, вертикали и диагонали).
     * Клетки за пределами поля считаются мертвыми.
     *
     * @param board Игровое поле.
     * @param r     Строка текущей клетки.
     * @param c     Столбец текущей клетки.
     * @return Количество живых соседей (от 0 до 8).
     */
    private int countLiveNeighbors(int[][] board, int r, int c) {
        int rows = board.length;
        int cols = board[0].length;
        int liveCount = 0;

        // Массивы смещений для 8 соседей (относительно [r, c])
        // (-1,-1) (-1, 0) (-1, 1)
        // ( 0,-1) ( 0, 0) ( 0, 1)  <- (0,0) пропускаем
        // ( 1,-1) ( 1, 0) ( 1, 1)
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        // Перебираем всех 8 возможных соседей
        for (int i = 0; i < 8; i++) {
            int neighborRow = r + dr[i];
            int neighborCol = c + dc[i];

            // Проверяем, находится ли сосед в пределах поля
            if (neighborRow >= 0 && neighborRow < rows && neighborCol >= 0 && neighborCol < cols) {
                // Если сосед живой (равен 1), увеличиваем счетчик
                // Можно написать board[neighborRow][neighborCol] == 1 ? 1 : 0; и суммировать
                liveCount += board[neighborRow][neighborCol]; // Так как мертвые = 0, живые = 1
            }
            // Если сосед за пределами поля, он не вносит вклад в счетчик (считается мертвым).
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
            System.out.println(rowStr);
        }
        System.out.println("------------------------------");
    }

    /**
     * Точка входа для демонстрации вычисления следующего шага игры "Жизнь".
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task90_GameOfLifeStep sol = new Task90_GameOfLifeStep();

        // Пример 1: Блинкер (осциллятор)
        System.out.println("--- Blinker Example ---");
        int[][] blinker1 = {
                {0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0}
        };
        printBoard("Blinker - Gen 0", blinker1);
        int[][] blinker2 = sol.nextGeneration(blinker1);
        printBoard("Blinker - Gen 1", blinker2);
        int[][] blinker3 = sol.nextGeneration(blinker2);
        printBoard("Blinker - Gen 2 (should match Gen 0)", blinker3);

        // Пример 2: Планер (движется)
        System.out.println("\n--- Glider Example ---");
        int[][] glider0 = {
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        printBoard("Glider - Gen 0", glider0);
        int[][] glider1 = sol.nextGeneration(glider0);
        printBoard("Glider - Gen 1", glider1);
        int[][] glider2 = sol.nextGeneration(glider1);
        printBoard("Glider - Gen 2", glider2);
        int[][] glider3 = sol.nextGeneration(glider2);
        printBoard("Glider - Gen 3", glider3);
        int[][] glider4 = sol.nextGeneration(glider3);
        printBoard("Glider - Gen 4 (shape of Gen 0, shifted)", glider4);
    }
}

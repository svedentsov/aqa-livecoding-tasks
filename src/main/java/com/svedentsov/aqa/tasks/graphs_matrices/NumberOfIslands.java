package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №55: Подсчет островов.
 * <p>
 * Описание: Дана 2D матрица 0 и 1. Посчитать количество групп из 1
 * (связанных по горизонтали/вертикали).
 * (Проверяет: обход матриц, рекурсия/DFS/BFS - базовая концепция)
 * <p>
 * Задание: Напишите метод `int countIslands(char[][] grid)`, где `grid` - это 2D
 * массив символов, содержащий '1' (земля) и '0' (вода). Метод должен посчитать
 * количество "островов". Остров окружен водой и формируется путем соединения
 * смежных (горизонтально или вертикально) земель.
 * <p>
 * Пример:
 * `grid = {{'1','1','0','0','0'}, {'1','1','0','0','0'}, {'0','0','1','0','0'}, {'0','0','0','1','1'}}` ->
 * `countIslands(grid)` должен вернуть `3`.
 */
public class NumberOfIslands {

    /**
     * Подсчитывает количество "островов" в 2D сетке (матрице), используя Поиск в Глубину (DFS).
     * Остров - это группа смежных (горизонтально или вертикально) ячеек '1' (земля),
     * окруженная '0' (вода).
     * Модифицирует исходный массив grid на месте, "затапливая" (меняя на '0')
     * посещенные ячейки земли.
     *
     * @param grid 2D массив символов '1' (земля) и '0' (вода).
     * @return Количество островов. Возвращает 0, если grid null или пуст.
     */
    public int countIslandsDFS(char[][] grid) {
        // Проверка на невалидный grid
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islandCount = 0;

        // Итерация по каждой ячейке
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Если нашли начало нового острова ('1')
                if (grid[r][c] == '1') {
                    islandCount++; // Увеличиваем счетчик
                    // Запускаем DFS, чтобы "затопить" весь этот остров
                    dfsFloodFill(grid, r, c);
                }
            }
        }
        // Важно: этот метод модифицирует исходный grid!
        return islandCount;
    }

    /**
     * Подсчитывает количество островов с использованием Поиска в Ширину (BFS).
     * Также модифицирует исходный массив grid.
     *
     * @param grid 2D массив символов '1' и '0'.
     * @return Количество островов.
     */
    public int countIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islandCount = 0;
        Queue<int[]> queue = new LinkedList<>(); // Очередь для координат BFS

        // Направления смещения (вниз, вверх, вправо, влево)
        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Если нашли начало нового острова ('1')
                if (grid[r][c] == '1') {
                    islandCount++;
                    queue.offer(new int[]{r, c}); // Добавляем стартовую ячейку в очередь
                    grid[r][c] = '0'; // Помечаем ее как посещенную ("затоплено")

                    // Запускаем BFS
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        int currR = current[0];
                        int currC = current[1];

                        // Исследуем 4-х соседей
                        for (int k = 0; k < 4; k++) {
                            int nextR = currR + dr[k];
                            int nextC = currC + dc[k];

                            // Проверяем границы и является ли сосед землей ('1')
                            if (nextR >= 0 && nextR < rows && nextC >= 0 && nextC < cols && grid[nextR][nextC] == '1') {
                                grid[nextR][nextC] = '0'; // Помечаем как посещенное
                                queue.offer(new int[]{nextR, nextC}); // Добавляем в очередь
                            }
                        }
                    } // конец BFS для одного острова
                } // конец if == '1'
            }
        }
        return islandCount;
    }

    /**
     * Точка входа для демонстрации работы методов подсчета островов.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        NumberOfIslands sol = new NumberOfIslands();

        char[][] grid1 = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        runIslandTest(sol, grid1, "Grid 1 (Expected: 3)");

        char[][] grid2 = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        runIslandTest(sol, grid2, "Grid 2 (Expected: 1)");

        char[][] grid3 = {
                {'1', '0', '1'},
                {'0', '1', '0'},
                {'1', '0', '1'}
        };
        runIslandTest(sol, grid3, "Grid 3 (Expected: 5)");

        char[][] grid4 = {};
        runIslandTest(sol, grid4, "Grid 4 (Empty - Expected: 0)");

        char[][] grid5 = {{'0', '0'}, {'0', '0'}};
        runIslandTest(sol, grid5, "Grid 5 (All Water - Expected: 0)");

        char[][] grid6 = {{'1'}};
        runIslandTest(sol, grid6, "Grid 6 (Single Island - Expected: 1)");

        runIslandTest(sol, null, "Grid 7 (Null - Expected: 0)");
    }

    /**
     * Рекурсивный Поиск в Глубину (DFS) для "затопления" острова.
     * Помечает текущую ячейку земли ('1') как воду ('0') и рекурсивно
     * вызывает себя для всех смежных (4 направления) ячеек земли.
     *
     * @param grid Сетка (модифицируется на месте).
     * @param r    Текущая строка.
     * @param c    Текущий столбец.
     */
    private void dfsFloodFill(char[][] grid, int r, int c) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Условия выхода из рекурсии: выход за границы или ячейка не является землей '1'
        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] != '1') {
            return;
        }

        // "Затопляем" текущую ячейку (помечаем как посещенную)
        grid[r][c] = '0';

        // Рекурсивно посещаем всех соседей
        dfsFloodFill(grid, r + 1, c); // Вниз
        dfsFloodFill(grid, r - 1, c); // Вверх
        dfsFloodFill(grid, r, c + 1); // Вправо
        dfsFloodFill(grid, r, c - 1); // Влево
    }

    /**
     * Вспомогательный метод для печати сетки.
     *
     * @param grid Сетка для печати.
     */
    private static void printGrid(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null) {
            System.out.println("Grid is null or empty.");
            return;
        }
        System.out.println("Grid state:");
        for (char[] row : grid) {
            // Убираем запятые для лучшей читаемости
            System.out.print("  [");
            for (int i = 0; i < row.length; i++) {
                System.out.print(row[i] + (i == row.length - 1 ? "" : " "));
            }
            System.out.println("]");
            // System.out.println(Arrays.toString(row)); // Альтернатива с запятыми
        }
    }

    /**
     * Вспомогательный метод для тестирования подсчета островов.
     *
     * @param sol          Экземпляр решателя.
     * @param originalGrid Исходная сетка.
     * @param description  Описание теста.
     */
    private static void runIslandTest(NumberOfIslands sol, char[][] originalGrid, String description) {
        System.out.println("\n--- " + description + " ---");
        if (originalGrid == null) {
            System.out.println("Input: null");
            System.out.println("Islands count (DFS): " + sol.countIslandsDFS(null));
            System.out.println("Islands count (BFS): " + sol.countIslandsBFS(null));
            return;
        }
        if (originalGrid.length == 0) {
            System.out.println("Input: {}");
            System.out.println("Islands count (DFS): " + sol.countIslandsDFS(originalGrid));
            System.out.println("Islands count (BFS): " + sol.countIslandsBFS(originalGrid));
            return;
        }

        // Создаем копии, так как методы DFS/BFS модифицируют сетку
        char[][] gridDfs = Arrays.stream(originalGrid).map(char[]::clone).toArray(char[][]::new);
        char[][] gridBfs = Arrays.stream(originalGrid).map(char[]::clone).toArray(char[][]::new);

        printGrid(originalGrid); // Печатаем исходную сетку
        try {
            int countDfs = sol.countIslandsDFS(gridDfs);
            System.out.println("Islands count (DFS): " + countDfs);
            // Печатаем сетку после DFS для демонстрации "затопления"
            // printGrid(gridDfs);
        } catch (Exception e) {
            System.err.println("Islands count (DFS): Error - " + e.getMessage());
        }
        try {
            int countBfs = sol.countIslandsBFS(gridBfs);
            System.out.println("Islands count (BFS): " + countBfs);
            // printGrid(gridBfs);
        } catch (Exception e) {
            System.err.println("Islands count (BFS): Error - " + e.getMessage());
        }
    }
}

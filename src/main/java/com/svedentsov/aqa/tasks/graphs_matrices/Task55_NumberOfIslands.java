package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №55: Подсчет островов.
 */
public class Task55_NumberOfIslands {

    /**
     * Подсчитывает количество "островов" в 2D сетке (матрице).
     * Остров - это группа смежных (горизонтально или вертикально) ячеек '1' (земля),
     * окруженная '0' (вода).
     * Использует поиск в глубину (DFS) для обхода и "затопления" (пометки)
     * найденного острова, чтобы не считать его части повторно.
     * Модифицирует исходный массив grid на месте.
     *
     * @param grid 2D массив символов '1' (земля) и '0' (вода).
     * @return Количество островов. Возвращает 0, если grid null или пуст.
     */
    public int countIslandsDFS(char[][] grid) {
        // Проверка на пустой или невалидный grid
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islandCount = 0; // Счетчик островов

        // Итерация по каждой ячейке сетки
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Если найдена ячейка с землей ('1'), которая еще не была посещена
                // (т.е. не была "затоплена" предыдущим обходом)
                if (grid[i][j] == '1') {
                    // Нашли новый остров! Увеличиваем счетчик.
                    islandCount++;
                    // Запускаем DFS для того, чтобы "затопить" все ячейки этого острова,
                    // изменив их значение с '1' на '0'.
                    dfsFloodFill(grid, i, j);
                }
            }
        }

        // После обхода всей сетки возвращаем итоговый счетчик островов.
        // Примечание: Исходный массив grid будет изменен (все '1' станут '0').
        // Если нужно сохранить исходный массив, следует использовать
        // дополнительный boolean[][] visited массив в dfsFloodFill.
        return islandCount;
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

        // Условия выхода из рекурсии (границы или не земля):
        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] != '1') {
            return;
        }

        // Помечаем текущую ячейку как посещенную ("затопляем")
        grid[r][c] = '0'; // Или можно использовать другой символ, например, '#'

        // Рекурсивный вызов для 4 соседей
        dfsFloodFill(grid, r + 1, c); // Вниз
        dfsFloodFill(grid, r - 1, c); // Вверх
        dfsFloodFill(grid, r, c + 1); // Вправо
        dfsFloodFill(grid, r, c - 1); // Влево
    }

    /**
     * Подсчитывает количество островов с использованием Поиска в Ширину (BFS).
     * Также модифицирует исходный массив grid.
     *
     * @param grid 2D массив символов '1' и '0'.
     * @return Количество островов.
     */
    public int countIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        int islandCount = 0;
        Queue<int[]> queue = new LinkedList<>();

        // Направления смещения (вниз, вверх, вправо, влево)
        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    islandCount++;
                    queue.offer(new int[]{i, j});
                    grid[i][j] = '0'; // Помечаем стартовую ячейку острова

                    // Запускаем BFS для "затопления" острова
                    while (!queue.isEmpty()) {
                        int[] current = queue.poll();
                        int r = current[0];
                        int c = current[1];

                        // Проверяем соседей
                        for (int k = 0; k < 4; k++) {
                            int nr = r + dr[k];
                            int nc = c + dc[k];

                            // Если сосед в пределах поля и является непосещенной землей
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '1') {
                                grid[nr][nc] = '0'; // Помечаем
                                queue.offer(new int[]{nr, nc}); // Добавляем в очередь
                            }
                        }
                    } // конец while BFS
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
        Task55_NumberOfIslands sol = new Task55_NumberOfIslands();

        char[][] grid1_orig = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        // Создаем копии, так как методы модифицируют массив
        char[][] grid1_dfs = Arrays.stream(grid1_orig).map(char[]::clone).toArray(char[][]::new);
        char[][] grid1_bfs = Arrays.stream(grid1_orig).map(char[]::clone).toArray(char[][]::new);

        System.out.println("Grid 1:");
        printGrid(grid1_orig); // Используем метод из Task90
        System.out.println("Islands count (DFS): " + sol.countIslandsDFS(grid1_dfs)); // 3
        System.out.println("Islands count (BFS): " + sol.countIslandsBFS(grid1_bfs)); // 3


        char[][] grid2_orig = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };
        char[][] grid2_dfs = Arrays.stream(grid2_orig).map(char[]::clone).toArray(char[][]::new);
        char[][] grid2_bfs = Arrays.stream(grid2_orig).map(char[]::clone).toArray(char[][]::new);
        System.out.println("\nGrid 2:");
        printGrid(grid2_orig);
        System.out.println("Islands count (DFS): " + sol.countIslandsDFS(grid2_dfs)); // 1
        System.out.println("Islands count (BFS): " + sol.countIslandsBFS(grid2_bfs)); // 1


        char[][] grid3_orig = {
                {'1', '0', '1'},
                {'0', '1', '0'},
                {'1', '0', '1'}
        };
        char[][] grid3_dfs = Arrays.stream(grid3_orig).map(char[]::clone).toArray(char[][]::new);
        char[][] grid3_bfs = Arrays.stream(grid3_orig).map(char[]::clone).toArray(char[][]::new);
        System.out.println("\nGrid 3:");
        printGrid(grid3_orig);
        System.out.println("Islands count (DFS): " + sol.countIslandsDFS(grid3_dfs)); // 5
        System.out.println("Islands count (BFS): " + sol.countIslandsBFS(grid3_bfs)); // 5
    }

    /**
     * Вспомогательный метод для печати сетки (можно взять из Task90).
     */
    private static void printGrid(char[][] grid) {
        if (grid == null || grid.length == 0) return;
        for (char[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("-------------");
    }
}

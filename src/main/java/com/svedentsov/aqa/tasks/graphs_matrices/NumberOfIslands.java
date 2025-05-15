package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Решение задачи №55: Подсчет островов.
 * Описание: Дана 2D матрица 0 и 1. Посчитать количество групп из 1
 * (связанных по горизонтали/вертикали).
 * (Проверяет: обход матриц, рекурсия/DFS/BFS - базовая концепция)
 * Задание: Напишите метод `int countIslands(char[][] grid)`, где `grid` - это 2D
 * массив символов, содержащий '1' (земля) и '0' (вода). Метод должен посчитать
 * количество "островов". Остров окружен водой и формируется путем соединения
 * смежных (горизонтально или вертикально) земель.
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
     * @return Количество островов. Возвращает 0, если grid null, пуст, или содержит пустые строки.
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
     * Рекурсивный Поиск в Глубину (DFS) для "затопления" острова.
     * Помечает текущую ячейку земли ('1') как воду ('0') и рекурсивно
     * вызывает себя для всех смежных (4 направления) ячеек земли.
     * Приватный, т.к. является деталью реализации countIslandsDFS.
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
     * Подсчитывает количество островов с использованием Поиска в Ширину (BFS).
     * Также модифицирует исходный массив grid на месте.
     *
     * @param grid 2D массив символов '1' (земля) и '0' (вода).
     * @return Количество островов. Возвращает 0, если grid null, пуст, или содержит пустые строки.
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
        int[] dr = {1, -1, 0, 0}; // delta row
        int[] dc = {0, 0, 1, -1}; // delta col

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
}

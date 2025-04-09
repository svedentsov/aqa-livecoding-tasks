package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Решение задачи №99: Заливка (Flood Fill).
 */
public class Task99_FloodFill {

    /**
     * Выполняет алгоритм "заливки" (Flood Fill) на изображении,
     * представленном 2D массивом целых чисел (представляющих цвета пикселей).
     * Начиная с заданной стартовой точки (sr, sc), все смежные пиксели
     * (в 4 направлениях: вверх, вниз, влево, вправо), имеющие тот же цвет,
     * что и стартовый пиксель ({@code originalColor}), перекрашиваются в новый
     * цвет {@code newColor}.
     * Использует рекурсивный Поиск в Глубину (DFS) для обхода связной области.
     * Модифицирует исходный массив {@code image} на месте.
     *
     * @param image    2D массив, представляющий изображение (цвета пикселей).
     *                 Предполагается не null и не пустым.
     * @param sr       Начальная строка (row) для заливки (0 <= sr < image.length).
     * @param sc       Начальный столбец (column) для заливки (0 <= sc < image[0].length).
     * @param newColor Новый цвет (int), которым нужно залить область.
     * @return Модифицированный массив {@code image} с залитой областью.
     * Возвращает исходный {@code image}, если стартовые координаты некорректны
     * или если цвет в стартовой точке уже равен {@code newColor}.
     * @throws IndexOutOfBoundsException если sr или sc выходят за границы массива.
     * @throws NullPointerException      если image равен null.
     */
    public int[][] floodFillRecursiveDFS(int[][] image, int sr, int sc, int newColor) {
        // 1. Проверка входных данных
        Objects.requireNonNull(image, "Image cannot be null");
        if (image.length == 0 || image[0] == null || image[0].length == 0) {
            return image; // Возвращаем пустой или невалидный массив
        }
        int rows = image.length;
        int cols = image[0].length;
        // Проверка координат (можно убрать, если вызывающий код гарантирует валидность)
        if (sr < 0 || sr >= rows || sc < 0 || sc >= cols) {
            throw new IndexOutOfBoundsException("Start coordinates (" + sr + "," + sc + ") are out of bounds.");
            // return image; // Альтернативно - просто вернуть без изменений
        }

        // 2. Получаем исходный цвет в стартовой точке
        int originalColor = image[sr][sc];

        // 3. Если новый цвет совпадает с исходным, заливка не требуется,
        //    иначе мы войдем в бесконечную рекурсию (или BFS).
        if (originalColor == newColor) {
            return image;
        }

        // 4. Запускаем рекурсивный DFS для заливки области
        dfsFill(image, sr, sc, originalColor, newColor);

        // 5. Возвращаем измененное изображение
        return image;
    }

    /**
     * Рекурсивный вспомогательный метод (DFS) для выполнения заливки.
     * Перекрашивает текущую ячейку (r, c), если она имеет {@code originalColor},
     * и рекурсивно вызывает себя для 4 соседних ячеек.
     *
     * @param image         Изображение (модифицируется на месте).
     * @param r             Текущая строка.
     * @param c             Текущий столбец.
     * @param originalColor Исходный цвет области, которую нужно залить.
     * @param newColor      Новый цвет для заливки.
     */
    private void dfsFill(int[][] image, int r, int c, int originalColor, int newColor) {
        int rows = image.length;
        int cols = image[0].length;

        // Условия выхода из рекурсии (границы или не тот цвет):
        // 1. Выход за пределы изображения (r или c некорректны).
        // 2. Цвет текущего пикселя НЕ равен исходному цвету области (`originalColor`).
        //    Это означает, что мы либо на границе области, либо уже посетили эту клетку
        //    (т.к. ее цвет уже был изменен на `newColor` или был другим изначально).
        if (r < 0 || r >= rows || c < 0 || c >= cols || image[r][c] != originalColor) {
            return;
        }

        // Перекрашиваем текущий пиксель в новый цвет
        image[r][c] = newColor;

        // Рекурсивно вызываем для 4 соседних пикселей (вверх, вниз, влево, вправо)
        dfsFill(image, r + 1, c, originalColor, newColor);
        dfsFill(image, r - 1, c, originalColor, newColor);
        dfsFill(image, r, c + 1, originalColor, newColor);
        dfsFill(image, r, c - 1, originalColor, newColor);
    }

    /**
     * Выполняет заливку (Flood Fill) итеративно с использованием Поиска в Ширину (BFS).
     *
     * @param image    Изображение.
     * @param sr       Начальная строка.
     * @param sc       Начальный столбец.
     * @param newColor Новый цвет.
     * @return Модифицированный массив image.
     */
    public int[][] floodFillIterativeBFS(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image.length == 0 || image[0] == null || image[0].length == 0) return image;
        int rows = image.length;
        int cols = image[0].length;
        if (sr < 0 || sr >= rows || sc < 0 || sc >= cols)
            throw new IndexOutOfBoundsException("Start coordinates out of bounds.");

        int originalColor = image[sr][sc];
        if (originalColor == newColor) return image;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc}); // Добавляем стартовую точку в очередь
        image[sr][sc] = newColor;       // Сразу перекрашиваем стартовую точку

        // Массивы смещений для 4 направлений
        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            // Проверяем 4 соседей
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                // Если сосед находится в пределах поля И имеет исходный цвет
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && image[nr][nc] == originalColor) {
                    image[nr][nc] = newColor; // Перекрашиваем соседа
                    queue.offer(new int[]{nr, nc}); // Добавляем его в очередь для обработки
                }
            }
        }
        return image;
    }

    /**
     * Вспомогательный метод для печати изображения (матрицы).
     */
    private static void printImage(String title, int[][] image) {
        System.out.println(title + ":");
        if (image == null || image.length == 0 || image[0] == null || image[0].length == 0) {
            System.out.println("Empty or null image.");
            return;
        }
        for (int[] row : image) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("------------------------------");
    }

    /**
     * Точка входа для демонстрации работы алгоритма заливки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task99_FloodFill sol = new Task99_FloodFill();

        // Пример 1
        int[][] image1_orig = {
                {1, 1, 1},
                {1, 1, 0},
                {1, 0, 1}
        };
        int sr1 = 1, sc1 = 1, newColor1 = 2;
        int[][] image1_dfs = Arrays.stream(image1_orig).map(int[]::clone).toArray(int[][]::new); // Копия для DFS
        int[][] image1_bfs = Arrays.stream(image1_orig).map(int[]::clone).toArray(int[][]::new); // Копия для BFS

        printImage("Original Image 1", image1_orig);

        sol.floodFillRecursiveDFS(image1_dfs, sr1, sc1, newColor1);
        printImage("Filled Image 1 (DFS, start=" + sr1 + "," + sc1 + ", newColor=" + newColor1 + ")", image1_dfs);
        /* Ожидаемый результат:
           [[2, 2, 2],
            [2, 2, 0],
            [2, 0, 1]]
         */

        sol.floodFillIterativeBFS(image1_bfs, sr1, sc1, newColor1);
        printImage("Filled Image 1 (BFS, start=" + sr1 + "," + sc1 + ", newColor=" + newColor1 + ")", image1_bfs);

        // Пример 2: Новый цвет совпадает с исходным
        int[][] image2_orig = {{0, 0, 0}, {0, 1, 1}, {0, 0, 0}};
        int sr2 = 1, sc2 = 1, newColor2 = 1;
        int[][] image2_dfs = Arrays.stream(image2_orig).map(int[]::clone).toArray(int[][]::new);
        printImage("\nOriginal Image 2", image2_orig);
        sol.floodFillRecursiveDFS(image2_dfs, sr2, sc2, newColor2);
        printImage("Filled Image 2 (newColor==originalColor)", image2_dfs); // Должна остаться без изменений

        // Пример 3: Заливка от края
        int[][] image3_orig = {{0, 0, 0}, {0, 0, 0}};
        int sr3 = 0, sc3 = 0, newColor3 = 5;
        int[][] image3_dfs = Arrays.stream(image3_orig).map(int[]::clone).toArray(int[][]::new);
        printImage("\nOriginal Image 3", image3_orig);
        sol.floodFillRecursiveDFS(image3_dfs, sr3, sc3, newColor3);
        printImage("Filled Image 3 (start=0,0)", image3_dfs); // Все должны стать 5
    }
}

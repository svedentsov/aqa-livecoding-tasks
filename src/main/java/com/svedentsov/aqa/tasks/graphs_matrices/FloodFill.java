package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Решение задачи №99: Заливка (Flood Fill).
 * <p>
 * Описание: Заменить цвет в связанной области матрицы.
 * (Проверяет: рекурсия/DFS/BFS, работа с матрицами)
 * <p>
 * Задание: Напишите метод `int[][] floodFill(int[][] image, int sr, int sc, int newColor)`,
 * который выполняет "заливку" в 2D массиве `image`, начиная с точки (`sr`, `sc`).
 * Все смежные клетки (4 направления), имеющие тот же цвет, что и стартовая клетка,
 * должны быть перекрашены в `newColor`.
 * <p>
 * Пример: `image = {{1,1,1},{1,1,0},{1,0,1}}, sr = 1, sc = 1, newColor = 2`.
 * Результат: `{{2,2,2},{2,2,0},{2,0,1}}`.
 */
public class FloodFill {

    /**
     * Выполняет "заливку" (Flood Fill) на изображении, используя рекурсивный DFS.
     * Модифицирует исходный массив {@code image} на месте.
     *
     * @param image    2D массив цветов.
     * @param sr       Начальная строка.
     * @param sc       Начальный столбец.
     * @param newColor Новый цвет.
     * @return Модифицированный массив {@code image}.
     * @throws IndexOutOfBoundsException если sr или sc выходят за границы.
     * @throws NullPointerException      если image равен null.
     */
    public int[][] floodFillRecursiveDFS(int[][] image, int sr, int sc, int newColor) {
        Objects.requireNonNull(image, "Image cannot be null");
        if (image.length == 0 || image[0] == null || image[0].length == 0) {
            return image; // Нет смысла заливать пустое изображение
        }
        int rows = image.length;
        int cols = image[0].length;
        if (sr < 0 || sr >= rows || sc < 0 || sc >= cols) {
            throw new IndexOutOfBoundsException("Start coordinates (" + sr + "," + sc + ") out of bounds.");
        }

        int originalColor = image[sr][sc];
        // Если новый цвет совпадает с исходным, ничего делать не нужно
        if (originalColor != newColor) {
            dfsFill(image, sr, sc, originalColor, newColor);
        }
        return image;
    }

    /**
     * Выполняет заливку (Flood Fill) итеративно с использованием Поиска в Ширину (BFS).
     * Модифицирует исходный массив {@code image} на месте.
     *
     * @param image    Изображение.
     * @param sr       Начальная строка.
     * @param sc       Начальный столбец.
     * @param newColor Новый цвет.
     * @return Модифицированный массив image.
     * @throws IndexOutOfBoundsException если sr или sc выходят за границы.
     * @throws NullPointerException      если image равен null.
     */
    public int[][] floodFillIterativeBFS(int[][] image, int sr, int sc, int newColor) {
        Objects.requireNonNull(image, "Image cannot be null");
        if (image.length == 0 || image[0] == null || image[0].length == 0) return image;
        int rows = image.length;
        int cols = image[0].length;
        if (sr < 0 || sr >= rows || sc < 0 || sc >= cols)
            throw new IndexOutOfBoundsException("Start coordinates (" + sr + "," + sc + ") out of bounds.");

        int originalColor = image[sr][sc];
        if (originalColor == newColor) return image; // Нечего делать

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc}); // Начинаем с исходной точки
        image[sr][sc] = newColor;       // Сразу перекрашиваем ее

        // Направления: вниз, вверх, вправо, влево
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

                // Если сосед в границах И имеет исходный цвет (т.е. еще не перекрашен)
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && image[nr][nc] == originalColor) {
                    image[nr][nc] = newColor; // Перекрашиваем
                    queue.offer(new int[]{nr, nc}); // Добавляем в очередь
                }
            }
        }
        return image;
    }

    /**
     * Точка входа для демонстрации работы алгоритма заливки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FloodFill sol = new FloodFill();

        System.out.println("--- Flood Fill Demonstration ---");

        // Пример 1
        int[][] image1 = {
                {1, 1, 1},
                {1, 1, 0},
                {1, 0, 1}
        };
        runFloodFillTest(sol, image1, 1, 1, 2, "Example 1 (Start 1,1 -> New 2)");
        /* Ожидаемый результат:
           [[2, 2, 2],
            [2, 2, 0],
            [2, 0, 1]] */

        // Пример 2: Новый цвет совпадает с исходным
        int[][] image2 = {{0, 0, 0}, {0, 1, 1}, {0, 0, 0}};
        runFloodFillTest(sol, image2, 1, 1, 1, "Example 2 (New == Original)");
        /* Ожидаемый результат: без изменений
           [[0, 0, 0],
            [0, 1, 1],
            [0, 0, 0]] */

        // Пример 3: Заливка от края
        int[][] image3 = {{0, 0, 0}, {0, 0, 0}};
        runFloodFillTest(sol, image3, 0, 0, 5, "Example 3 (Fill all)");
        /* Ожидаемый результат:
           [[5, 5, 5],
            [5, 5, 5]] */

        // Пример 4: Заливка не затрагивает другую область
        int[][] image4 = {{1, 0, 1}, {0, 1, 0}, {1, 0, 1}};
        runFloodFillTest(sol, image4, 1, 1, 9, "Example 4 (Fill center)");
        /* Ожидаемый результат:
            [[1, 0, 1],
             [0, 9, 0],
             [1, 0, 1]] */

        // Пример 5: Пустое изображение
        runFloodFillTest(sol, new int[0][0], 0, 0, 1, "Empty image");
        // Ожидается возврат пустого массива или ошибка координат

        // Пример 6: Null изображение
        runFloodFillTest(sol, null, 0, 0, 1, "Null image");
        // Ожидается NullPointerException

        // Пример 7: Неверные координаты
        runFloodFillTest(sol, new int[][]{{1}}, 1, 0, 2, "Invalid coordinates");
        // Ожидается IndexOutOfBoundsException
    }

    /**
     * Рекурсивный вспомогательный метод (DFS) для выполнения заливки.
     * Перекрашивает текущую ячейку (r, c), если она имеет {@code originalColor},
     * и рекурсивно вызывает себя для 4 соседних ячеек.
     */
    private void dfsFill(int[][] image, int r, int c, int originalColor, int newColor) {
        int rows = image.length;
        int cols = image[0].length;

        // Условия выхода: вне границ или цвет не совпадает с исходным
        if (r < 0 || r >= rows || c < 0 || c >= cols || image[r][c] != originalColor) {
            return;
        }

        // Перекрашиваем
        image[r][c] = newColor;

        // Рекурсия для соседей
        dfsFill(image, r + 1, c, originalColor, newColor);
        dfsFill(image, r - 1, c, originalColor, newColor);
        dfsFill(image, r, c + 1, originalColor, newColor);
        dfsFill(image, r, c - 1, originalColor, newColor);
    }

    /**
     * Вспомогательный метод для печати изображения (матрицы).
     */
    private static void printImage(String title, int[][] image) {
        System.out.println(title + ":");
        if (image == null) {
            System.out.println("null");
            return;
        }
        if (image.length == 0 || image[0] == null) {
            System.out.println("[]");
            return;
        }

        for (int[] row : image) {
            System.out.println("  " + Arrays.toString(row));
        }
        // System.out.println("------------------------------"); // Разделитель можно убрать
    }

    /**
     * Вспомогательный метод для тестирования методов Flood Fill.
     *
     * @param sol           Экземпляр решателя.
     * @param originalImage Исходное изображение.
     * @param sr            Начальная строка.
     * @param sc            Начальный столбец.
     * @param newColor      Новый цвет.
     * @param description   Описание теста.
     */
    private static void runFloodFillTest(FloodFill sol, int[][] originalImage, int sr, int sc, int newColor, String description) {
        System.out.println("\n--- " + description + " ---");
        printImage("Original Image", originalImage);
        System.out.println("Start=(" + sr + "," + sc + "), NewColor=" + newColor);

        // Тест DFS
        // Создаем копию, так как методы модифицируют массив на месте
        int[][] imageDfs = (originalImage == null) ? null : Arrays.stream(originalImage).map(int[]::clone).toArray(int[][]::new);
        System.out.print("Result (DFS):");
        try {
            int[][] resultDfs = sol.floodFillRecursiveDFS(imageDfs, sr, sc, newColor);
            System.out.println(); // Перенос строки
            printImage("", resultDfs);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println(" Error - " + e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println(" Unexpected Error (DFS) - " + e.getMessage());
        }

        // Тест BFS
        int[][] imageBfs = (originalImage == null) ? null : Arrays.stream(originalImage).map(int[]::clone).toArray(int[][]::new);
        System.out.print("Result (BFS):");
        try {
            int[][] resultBfs = sol.floodFillIterativeBFS(imageBfs, sr, sc, newColor);
            System.out.println(); // Перенос строки
            printImage("", resultBfs);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println(" Error - " + e.getClass().getSimpleName() + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println(" Unexpected Error (BFS) - " + e.getMessage());
        }
        System.out.println("------------------------------");
    }
}

package com.svedentsov.aqa.tasks.graphs_matrices;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Решение задачи №99: Заливка (Flood Fill).
 * Описание: Заменить цвет в связанной области матрицы.
 * (Проверяет: рекурсия/DFS/BFS, работа с матрицами)
 * Задание: Напишите метод `int[][] floodFill(int[][] image, int sr, int sc, int newColor)`,
 * который выполняет "заливку" в 2D массиве `image`, начиная с точки (`sr`, `sc`).
 * Все смежные клетки (4 направления), имеющие тот же цвет, что и стартовая клетка,
 * должны быть перекрашены в `newColor`.
 * Пример: `image = {{1,1,1},{1,1,0},{1,0,1}}, sr = 1, sc = 1, newColor = 2`.
 * Результат: `{{2,2,2},{2,2,0},{2,0,1}}`.
 */
public class FloodFill {

    /**
     * Выполняет "заливку" (Flood Fill) на изображении, используя рекурсивный обход в глубину (DFS).
     * Модифицирует исходный массив {@code image} на месте.
     *
     * @param image    2D массив цветов.
     * @param sr       Начальная строка.
     * @param sc       Начальный столбец.
     * @param newColor Новый цвет.
     * @return Модифицированный массив {@code image}.
     * @throws IndexOutOfBoundsException если sr или sc выходят за границы массива image.
     * @throws NullPointerException      если image равен null.
     */
    public int[][] floodFillRecursiveDFS(int[][] image, int sr, int sc, int newColor) {
        Objects.requireNonNull(image, "Image не должен быть null");
        if (image.length == 0) {
            return image; // Обработка случая полностью пустого изображения (0 строк)
        }
        if (image[0] == null || image[0].length == 0) { // Обработка изображения без колонок или с первой строкой null
            if (image.length > 0 && (sr != 0 || sc != 0) && image[0] == null) {
                // Возможна проблема, если sr указывает на строку со значением null.
                // Для полной надёжности потребуется более строгая валидация "рваных" массивов.
            } else if (image.length > 0 && image[0].length == 0 && (sr != 0 || sc != 0)) {
                throw new IndexOutOfBoundsException("Начальные координаты (" + sr + "," + sc + ") выходят за пределы изображения с пустыми колонками.");
            }
            return image;
        }

        int rows = image.length;
        int cols = image[0].length;

        // Проверка координат на выход за границы
        if (sr < 0 || sr >= rows || sc < 0 || sc >= cols) {
            throw new IndexOutOfBoundsException("Начальные координаты (" + sr + "," + sc + ") выходят за границы.");
        }
        // Дополнительная проверка для рваных массивов
        if (image[sr] == null || sc >= image[sr].length) {
            throw new IndexOutOfBoundsException("Начальные координаты (" + sr + "," + sc + ") выходят за границы конкретной строки.");
        }

        int originalColor = image[sr][sc];
        if (originalColor != newColor) {
            dfsFill(image, sr, sc, originalColor, newColor);
        }
        return image;
    }

    /**
     * Рекурсивная заливка смежных клеток с тем же цветом.
     */
    private void dfsFill(int[][] image, int r, int c, int originalColor, int newColor) {
        int rows = image.length;
        int cols = image[0].length; // Предполагаем, что image[0] валиден после начальных проверок

        // Базовые случаи
        if (r < 0 || r >= rows || c < 0 || c >= cols || // Выход за границы
                image[r] == null || c >= image[r].length || // Безопасность для рваных массивов
                image[r][c] != originalColor) { // Цвет не совпадает (уже посещена или другой цвет)
            return;
        }

        image[r][c] = newColor; // Перекрашиваем текущую клетку

        // Рекурсивный вызов для 4 соседей
        dfsFill(image, r + 1, c, originalColor, newColor); // Вниз
        dfsFill(image, r - 1, c, originalColor, newColor); // Вверх
        dfsFill(image, r, c + 1, originalColor, newColor); // Вправо
        dfsFill(image, r, c - 1, originalColor, newColor); // Влево
    }

    /**
     * Выполняет заливку (Flood Fill) итеративно с использованием поиска в ширину (BFS).
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
        Objects.requireNonNull(image, "Image не должен быть null");
        if (image.length == 0) return image;
        if (image[0] == null || image[0].length == 0) {
            if (image.length > 0 && (sr != 0 || sc != 0) && image[0] == null) {
                // Дополнительная защита для рваных массивов
            } else if (image.length > 0 && image[0].length == 0 && (sr != 0 || sc != 0)) {
                throw new IndexOutOfBoundsException("Начальные координаты (" + sr + "," + sc + ") выходят за пределы изображения с пустыми колонками.");
            }
            return image;
        }

        int rows = image.length;
        int cols = image[0].length;
        if (sr < 0 || sr >= rows || sc < 0 || sc >= cols)
            throw new IndexOutOfBoundsException("Начальные координаты (" + sr + "," + sc + ") выходят за границы.");
        if (image[sr] == null || sc >= image[sr].length) {
            throw new IndexOutOfBoundsException("Начальные координаты (" + sr + "," + sc + ") выходят за границы конкретной строки.");
        }

        int originalColor = image[sr][sc];
        if (originalColor == newColor) return image;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});
        image[sr][sc] = newColor; // Помечаем как окрашенное

        int[] dr = {1, -1, 0, 0}; // смещение по строкам
        int[] dc = {0, 0, 1, -1}; // смещение по столбцам

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && // Границы
                        image[nr] != null && nc < image[nr].length && // Безопасность для рваных массивов
                        image[nr][nc] == originalColor) { // Совпадение цвета
                    image[nr][nc] = newColor;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return image;
    }
}

package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Решение задачи №75: Найти K ближайших точек к началу координат.
 * Описание: (Проверяет: геометрия, PriorityQueue/сортировка)
 * Задание: Напишите метод `int[][] kClosest(int[][] points, int k)`, где `points`
 * - массив точек вида `[x, y]`. Метод должен вернуть `k` точек, наиболее
 * близких к началу координат (0, 0).
 * Пример: `points = [[1, 3], [-2, 2]], k = 1`. Результат: `[[-2, 2]]`.
 * `points = [[3, 3], [5, -1], [-2, 4]], k = 2`. Результат: `[[3, 3], [-2, 4]]`
 * (порядок не важен).
 */
public class KClosestPointsOrigin {

    /**
     * Находит k точек из массива {@code points}, ближайших к началу координат (0, 0).
     * Использует Max-Heap (приоритетную очередь с обратным порядком сравнения)
     * размером k для эффективного отслеживания k ближайших точек.
     * Сравнивает точки по квадрату евклидова расстояния (x^2 + y^2),
     * чтобы избежать вычисления квадратного корня.
     * Сложность: O(n log k) по времени, O(k) по памяти для кучи.
     *
     * @param points Массив точек, где каждая точка представлена как {@code int[2] {x, y}}.
     * @param k      Количество ближайших точек для поиска (1 <= k <= points.length).
     * @return Массив из k ближайших точек. Порядок точек в результирующем массиве не гарантирован.
     * Возвращает пустой 2D массив, если {@code points} null/пуст или {@code k <= 0}.
     * Возвращает копию {@code points}, если {@code k >= points.length}.
     * @throws IllegalArgumentException если k некорректно (хотя в коде возвращается пустой массив).
     */
    public int[][] kClosestHeap(int[][] points, int k) {
        // 1. Обработка некорректных входных данных
        if (points == null || points.length == 0 || k <= 0) {
            return new int[0][0]; // Возвращаем пустой массив
        }
        int n = points.length;
        // Если k больше или равно количеству точек, возвращаем все точки
        if (k >= n) {
            // Возвращаем копию, чтобы не возвращать исходный массив
            return Arrays.stream(points)
                    .filter(Objects::nonNull) // Добавим проверку на null внутри массива
                    .map(int[]::clone)
                    .toArray(int[][]::new);
        }

        // 2. Создаем Max-Heap размером k. Компаратор по убыванию расстояния.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(k,
                (p1, p2) -> Integer.compare(squaredDistance(p2), squaredDistance(p1))
        );

        // 3. Проходим по всем точкам
        for (int[] point : points) {
            // Проверка, что сама точка не null и имеет 2 координаты
            if (point == null || point.length != 2) {
                System.err.println("Warning: Skipping invalid point format: " + Arrays.toString(point));
                continue;
            }

            maxHeap.offer(point); // Добавляем точку в кучу
            // Если размер кучи превысил k, удаляем самый дальний элемент (вершину)
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // 4. Извлекаем k точек из кучи в результирующий массив
        int[][] result = new int[k][2];
        // Заполняем массив результатом из кучи
        // (порядок в результирующем массиве будет произвольным)
        int index = 0;
        while (!maxHeap.isEmpty()) {
            result[index++] = maxHeap.poll();
        }
        // Или можно заполнять с конца:
        // for (int i = k - 1; i >= 0; i--) {
        //    result[i] = maxHeap.poll();
        // }

        return result;
    }

    /**
     * Находит k ближайших точек с использованием сортировки всего массива.
     * Сложность: O(n log n) по времени, O(log n) или O(n) по памяти для сортировки.
     *
     * @param points Массив точек.
     * @param k      Количество точек.
     * @return Массив из k ближайших точек, отсортированных по расстоянию.
     */
    public int[][] kClosestSorting(int[][] points, int k) {
        if (points == null || points.length == 0 || k <= 0) return new int[0][0];
        k = Math.min(k, points.length); // Убедимся, что k не больше размера массива

        // Фильтруем невалидные точки перед сортировкой
        int[][] validPoints = Arrays.stream(points)
                .filter(p -> p != null && p.length == 2)
                .toArray(int[][]::new);

        if (validPoints.length == 0) return new int[0][0];
        k = Math.min(k, validPoints.length); // Корректируем k, если точек стало меньше

        // Сортируем валидные точки по возрастанию квадрата расстояния
        Arrays.sort(validPoints, Comparator.comparingInt(this::squaredDistance));

        // Возвращаем первые k точек (создаем копию диапазона)
        return Arrays.copyOfRange(validPoints, 0, k);
    }

    /**
     * Точка входа для демонстрации работы методов поиска k ближайших точек.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        KClosestPointsOrigin sol = new KClosestPointsOrigin();

        System.out.println("--- Finding K Closest Points to Origin ---");

        runKClosestTest(sol, new int[][]{{1, 3}, {-2, 2}}, 1, "Example 1 (k=1)");
        // Expected: [[-2, 2]]

        runKClosestTest(sol, new int[][]{{3, 3}, {5, -1}, {-2, 4}}, 2, "Example 2 (k=2)");
        // Expected (Sort): [[3, 3], [-2, 4]]
        // Expected (Heap): [[-2, 4], [3, 3]] (или наоборот)

        runKClosestTest(sol, new int[][]{{0, 1}, {1, 0}}, 2, "Two points same distance (k=2)");
        // Expected: [[0, 1], [1, 0]] (или наоборот)

        runKClosestTest(sol, new int[][]{{1, 1}, {2, 2}, {3, 3}}, 1, "Simple case (k=1)");
        // Expected: [[1, 1]]

        runKClosestTest(sol, new int[][]{{1, 1}, {2, 2}, {3, 3}}, 3, "k equals n");
        // Expected: [[1, 1], [2, 2], [3, 3]] (или другой порядок для Heap)

        runKClosestTest(sol, new int[][]{{1, 1}, {2, 2}, {3, 3}}, 5, "k greater than n");
        // Expected: [[1, 1], [2, 2], [3, 3]] (или другой порядок для Heap)

        runKClosestTest(sol, new int[][]{}, 1, "Empty points array");
        // Expected: []

        runKClosestTest(sol, new int[][]{{1, 1}}, 0, "k=0");
        // Expected: []

        runKClosestTest(sol, null, 2, "Null points array");
        // Expected: []

        runKClosestTest(sol, new int[][]{{1, 1}, null, {3, 3}}, 2, "Array with null point");
        // Expected (Sort): [[1, 1], [3, 3]]
        // Expected (Heap): [[1, 1], [3, 3]] (или наоборот, т.к. null пропускается)
    }

    /**
     * Вспомогательный метод для вычисления КВАДРАТА евклидова расстояния
     * от точки до начала координат (0, 0): x^2 + y^2.
     *
     * @param point Точка в виде массива {x, y}. Предполагается не null и length=2.
     * @return Квадрат расстояния до начала координат.
     */
    private int squaredDistance(int[] point) {
        // Добавим проверку на null на всякий случай, хотя она есть в вызывающих методах
        if (point == null || point.length != 2) return Integer.MAX_VALUE; // Или бросить исключение
        return point[0] * point[0] + point[1] * point[1];
    }

    /**
     * Вспомогательный метод для тестирования kClosest методов.
     *
     * @param sol         Экземпляр решателя.
     * @param points      Массив точек.
     * @param k           Количество искомых точек.
     * @param description Описание теста.
     */
    private static void runKClosestTest(KClosestPointsOrigin sol, int[][] points, int k, String description) {
        System.out.println("\n--- " + description + " ---");
        String pointsStr = (points == null ? "null" : Arrays.deepToString(points));
        System.out.println("Input points: " + pointsStr + ", k=" + k);

        // Копируем массив для метода сортировки, т.к. он его изменяет
        int[][] pointsCopy = (points == null) ? null : Arrays.stream(points).map(int[]::clone).toArray(int[][]::new);

        try {
            int[][] resultHeap = sol.kClosestHeap(points, k);
            // Сортируем результат кучи для консистентности вывода
            Arrays.sort(resultHeap, Comparator.comparingInt(sol::squaredDistance));
            System.out.println("  Result (Heap, sorted): " + Arrays.deepToString(resultHeap));
        } catch (Exception e) {
            System.err.println("  Result (Heap): Error - " + e.getMessage());
        }

        try {
            int[][] resultSort = sol.kClosestSorting(pointsCopy, k);
            // Результат сортировки уже отсортирован
            System.out.println("  Result (Sort)      : " + Arrays.deepToString(resultSort));
        } catch (Exception e) {
            System.err.println("  Result (Sort)      : Error - " + e.getMessage());
        }
    }
}

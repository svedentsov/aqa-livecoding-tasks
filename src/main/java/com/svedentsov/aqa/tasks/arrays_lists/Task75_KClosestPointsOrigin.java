package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Решение задачи №75: Найти K ближайших точек к началу координат.
 */
public class Task75_KClosestPointsOrigin {

    /**
     * Находит k точек из массива {@code points}, ближайших к началу координат (0, 0).
     * Использует Max-Heap (приоритетную очередь с обратным порядком сравнения)
     * размером k для эффективного отслеживания k ближайших точек.
     * Сравнивает точки по квадрату евклидова расстояния, чтобы избежать вычисления корня.
     * Сложность O(n log k) по времени, O(k) по памяти для кучи.
     *
     * @param points Массив точек, где каждая точка представлена как {@code int[2] {x, y}}.
     * @param k      Количество ближайших точек для поиска (1 <= k <= points.length).
     * @return Массив из k ближайших точек. Порядок точек в результирующем массиве не гарантирован.
     * Возвращает пустой 2D массив, если {@code points} null/пуст или {@code k <= 0}.
     * Возвращает копию {@code points}, если {@code k >= points.length}.
     */
    public int[][] kClosestHeap(int[][] points, int k) {
        // 1. Обработка некорректных входных данных
        if (points == null || points.length == 0 || k <= 0) {
            return new int[0][0]; // Пустой результат
        }
        // Если k больше или равно количеству точек, возвращаем все точки (можно копию)
        if (k >= points.length) {
            return Arrays.stream(points).toArray(int[][]::new); // Возвращаем копию
        }
        // Доп. проверка на валидность самих точек (опционально)
        // for (int[] p : points) if (p == null || p.length != 2) throw new IllegalArgumentException("Invalid point format");


        // 2. Создаем Max-Heap (кучу максимума) размером k.
        // Компаратор сравнивает точки по УБЫВАНИЮ квадрата расстояния.
        // В вершине кучи (peek()) всегда будет самая ДАЛЬНЯЯ из k ближайших точек.
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(k,
                // Компаратор: (p1, p2) -> compare(dist(p2), dist(p1))
                (p1, p2) -> Integer.compare(squaredDistance(p2), squaredDistance(p1))
        );
        // Альтернативный компаратор:
        // PriorityQueue<int[]> maxHeap = new PriorityQueue<>(k,
        //     Comparator.<int[], Integer>comparing(this::squaredDistance).reversed()
        // );

        // 3. Проходим по всем точкам в массиве points
        for (int[] point : points) {
            // Добавляем точку в кучу
            maxHeap.offer(point);
            // Если размер кучи превысил k, удаляем самый дальний элемент (вершину max-heap)
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
            /* // Альтернативная логика (сначала проверка размера)
            if (maxHeap.size() < k) {
                maxHeap.offer(point);
            } else if (squaredDistance(point) < squaredDistance(maxHeap.peek())) {
                maxHeap.poll();
                maxHeap.offer(point);
            }
            */
        }

        // 4. Извлекаем k точек из кучи в результирующий массив.
        // Порядок извлечения из кучи не гарантирует сортировку по расстоянию.
        int[][] result = new int[k][2];
        for (int i = k - 1; i >= 0; i--) { // Заполняем с конца или начала - не важно
            result[i] = maxHeap.poll();
        }

        return result;
    }

    /**
     * Вспомогательный метод для вычисления КВАДРАТА евклидова расстояния
     * от точки (point[0], point[1]) до начала координат (0, 0): x^2 + y^2.
     *
     * @param point Точка в виде массива {x, y}. Предполагается не null и length=2.
     * @return Квадрат расстояния до начала координат.
     */
    private int squaredDistance(int[] point) {
        return point[0] * point[0] + point[1] * point[1];
    }

    /**
     * Находит k ближайших точек с использованием сортировки всего массива.
     * Сложность O(n log n) по времени, O(log n) или O(n) по памяти для сортировки.
     *
     * @param points Массив точек.
     * @param k      Количество точек.
     * @return Массив из k ближайших точек, отсортированных по расстоянию.
     */
    public int[][] kClosestSorting(int[][] points, int k) {
        if (points == null || points.length == 0 || k <= 0) return new int[0][0];
        k = Math.min(k, points.length); // k не может быть больше числа точек

        // Сортируем массив точек по возрастанию квадрата расстояния
        Arrays.sort(points, Comparator.comparingInt(this::squaredDistance));
        // или: Arrays.sort(points, (p1, p2) -> Integer.compare(squaredDistance(p1), squaredDistance(p2)));

        // Возвращаем первые k точек из отсортированного массива (создаем копию)
        return Arrays.copyOfRange(points, 0, k);
    }

    /**
     * Точка входа для демонстрации работы методов поиска k ближайших точек.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task75_KClosestPointsOrigin sol = new Task75_KClosestPointsOrigin();

        int[][] points1 = {{1, 3}, {-2, 2}}; // distSq: 10, 8
        int k1 = 1;
        System.out.println("Points: " + Arrays.deepToString(points1) + ", k=" + k1);
        int[][] result1H = sol.kClosestHeap(points1, k1);
        System.out.println("Heap Result: " + Arrays.deepToString(result1H)); // [[-2, 2]]
        int[][] result1S = sol.kClosestSorting(points1, k1);
        System.out.println("Sort Result: " + Arrays.deepToString(result1S)); // [[-2, 2]]

        int[][] points2 = {{3, 3}, {5, -1}, {-2, 4}}; // distSq: 18, 26, 20
        int k2 = 2;
        System.out.println("\nPoints: " + Arrays.deepToString(points2) + ", k=" + k2);
        int[][] result2H = sol.kClosestHeap(points2, k2);
        // Результат Heap может быть в любом порядке, например [[-2, 4], [3, 3]]
        System.out.println("Heap Result: " + Arrays.deepToString(result2H));
        // Сортируем результат Heap для сравнения с Sort
        Arrays.sort(result2H, Comparator.comparingInt(sol::squaredDistance));
        System.out.println("Heap Res(Sorted): " + Arrays.deepToString(result2H));

        int[][] result2S = sol.kClosestSorting(points2, k2);
        // Результат Sort будет отсортирован по расстоянию: [[3, 3], [-2, 4]]
        System.out.println("Sort Result: " + Arrays.deepToString(result2S));

        int[][] points3 = {{0, 1}, {1, 0}}; // distSq: 1, 1
        int k3 = 2;
        System.out.println("\nPoints: " + Arrays.deepToString(points3) + ", k=" + k3);
        int[][] result3H = sol.kClosestHeap(points3, k3);
        System.out.println("Heap Result: " + Arrays.deepToString(result3H)); // Порядок не гарантирован
        int[][] result3S = sol.kClosestSorting(points3, k3);
        System.out.println("Sort Result: " + Arrays.deepToString(result3S)); // Порядок не гарантирован при равных расстояниях

        int[][] points4 = {};
        int k4 = 1;
        System.out.println("\nPoints: " + Arrays.deepToString(points4) + ", k=" + k4);
        System.out.println("Heap Result: " + Arrays.deepToString(sol.kClosestHeap(points4, k4))); // []
        System.out.println("Sort Result: " + Arrays.deepToString(sol.kClosestSorting(points4, k4))); // []
    }
}

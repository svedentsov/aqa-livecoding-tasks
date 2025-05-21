package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.Comparator;
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
     * Находит k точек из массива {@code points}, ближайших к началу координат (0, 0),
     * используя Max-Heap.
     * Сложность: O(n log k) по времени, O(k) по памяти.
     *
     * @param points Массив точек {@code int[2] {x, y}}.
     * @param k      Количество ближайших точек.
     * @return Массив из k (или менее, если валидных точек меньше k) ближайших точек.
     * Порядок не гарантирован.
     */
    public int[][] kClosestHeap(int[][] points, int k) {
        if (points == null || points.length == 0 || k <= 0) {
            return new int[0][0];
        }

        // Фильтруем невалидные точки ДО обработки, чтобы k было релевантно
        int[][] validPoints = Arrays.stream(points)
                .filter(p -> p != null && p.length == 2)
                .toArray(int[][]::new);

        if (validPoints.length == 0) {
            return new int[0][0];
        }

        int n = validPoints.length;
        // Если k больше или равно количеству валидных точек, возвращаем все валидные точки
        if (k >= n) {
            // Возвращаем копии валидных точек
            return Arrays.stream(validPoints)
                    .map(int[]::clone)
                    .toArray(int[][]::new);
        }

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(k,
                (p1, p2) -> Integer.compare(squaredDistance(p2), squaredDistance(p1))
        );

        for (int[] point : validPoints) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // Размер результата будет равен maxHeap.size(), что равно k или количеству валидных точек, если их меньше k.
        int[][] result = new int[maxHeap.size()][2];
        int index = 0;
        while (!maxHeap.isEmpty()) {
            result[index++] = maxHeap.poll();
        }
        // Чтобы порядок был предсказуемым для тестов (хотя задача не требует),
        // можно отсортировать результат. Но для теста лучше сравнивать множества.
        // Для простоты тестов, можно отсортировать здесь или в тесте.
        // Arrays.sort(result, Comparator.comparingInt(this::squaredDistance)); // Опционально
        return result;
    }

    /**
     * Находит k ближайших точек с использованием сортировки всего массива.
     * Сложность: O(n log n) по времени.
     *
     * @param points Массив точек.
     * @param k      Количество точек.
     * @return Массив из k (или менее) ближайших точек, отсортированных по расстоянию.
     */
    public int[][] kClosestSorting(int[][] points, int k) {
        if (points == null || points.length == 0 || k <= 0) {
            return new int[0][0];
        }

        int[][] validPoints = Arrays.stream(points)
                .filter(p -> p != null && p.length == 2)
                .toArray(int[][]::new);

        if (validPoints.length == 0) {
            return new int[0][0];
        }

        // Убедимся, что k не больше количества валидных точек
        k = Math.min(k, validPoints.length);
        if (k == 0) return new int[0][0]; // если k стал 0 после Math.min


        Arrays.sort(validPoints, Comparator.comparingInt(this::squaredDistance));

        return Arrays.copyOfRange(validPoints, 0, k);
    }

    /**
     * Вспомогательный метод для вычисления КВАДРАТА евклидова расстояния.
     */
    private int squaredDistance(int[] point) {
        // Предполагается, что point валиден (не null и length=2) после фильтрации
        return point[0] * point[0] + point[1] * point[1];
    }
}

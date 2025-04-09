package com.svedentsov.aqa.tasks.algorithms;

import java.util.Arrays;

/**
 * Решение задачи №62: Найти медиану двух отсортированных массивов.
 */
public class Task62_MedianSortedArrays {

    /**
     * Находит медиану двух отсортированных массивов nums1 и nums2.
     * Медиана - это значение, которое делит отсортированный набор данных пополам.
     * Если общее количество элементов нечетное, медиана - это средний элемент.
     * Если четное - среднее арифметическое двух средних элементов.
     * Реализует эффективный алгоритм на основе бинарного поиска по разрезам (partitions)
     * с логарифмической сложностью O(log(min(m, n))), где m и n - длины массивов.
     *
     * @param nums1 Первый отсортированный массив.
     * @param nums2 Второй отсортированный массив.
     * @return Медиана объединенного отсортированного массива в виде double.
     * @throws IllegalArgumentException если входные массивы некорректны (например, не отсортированы).
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Обработка null массивов (можно вернуть 0, NaN или бросить исключение)
        if (nums1 == null) nums1 = new int[0];
        if (nums2 == null) nums2 = new int[0];


        // Убедимся, что nums1 - это массив меньшей или равной длины,
        // чтобы бинарный поиск выполнялся по меньшему диапазону индексов.
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1); // Вызов с поменянными массивами
        }

        int m = nums1.length;
        int n = nums2.length;

        // Если оба массива пусты
        if (m == 0 && n == 0) {
            throw new IllegalArgumentException("Cannot find median of two empty arrays.");
        }


        // Границы бинарного поиска для индекса разреза в nums1 (от 0 до m)
        int low = 0;
        int high = m;

        // Общее количество элементов в ОБЪЕДИНЕННОЙ левой половине
        // (m + n + 1) / 2 работает и для четного, и для нечетного общего количества
        int totalHalfLen = (m + n + 1) / 2;

        while (low <= high) {
            // Индекс разреза в nums1 (partition1 элементов слева от разреза)
            int partition1 = low + (high - low) / 2;
            // Соответствующий индекс разреза в nums2
            int partition2 = totalHalfLen - partition1;

            // Определяем значения элементов непосредственно слева и справа от разрезов.
            // Используем MIN_VALUE/MAX_VALUE для обработки крайних случаев (разрез в начале/конце).

            // Максимальный элемент в левой части nums1 (если partition1=0, то нет элементов слева)
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            // Минимальный элемент в правой части nums1 (если partition1=m, то нет элементов справа)
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

            // Аналогично для nums2
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

            // Проверяем условие правильного разреза:
            // Все элементы левых частей <= Всех элементов правых частей
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Разрез найден! Вычисляем медиану.

                // Если общее количество элементов (m + n) четное
                if ((m + n) % 2 == 0) {
                    // Медиана - это среднее арифметическое двух центральных элементов:
                    // max(maxLeft1, maxLeft2) и min(minRight1, minRight2).
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else { // Если общее количество элементов нечетное
                    // Медиана - это максимальный элемент из левых частей.
                    return Math.max(maxLeft1, maxLeft2);
                }
            }
            // Если условие разреза не выполнено, корректируем границы бинарного поиска:
            else if (maxLeft1 > minRight2) {
                // Слишком много элементов взяли из nums1 (maxLeft1 слишком большой).
                // Нужно сдвинуть разрез влево в nums1.
                high = partition1 - 1;
            } else { // maxLeft2 > minRight1
                // Слишком мало элементов взяли из nums1 (maxLeft2 слишком большой).
                // Нужно сдвинуть разрез вправо в nums1.
                low = partition1 + 1;
            }
        }

        // Если бинарный поиск завершился без нахождения разреза,
        // это указывает на ошибку во входных данных (например, массивы не отсортированы).
        throw new IllegalArgumentException("Input arrays might not be sorted or contain invalid values.");
    }

    /**
     * Точка входа для демонстрации работы метода поиска медианы.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task62_MedianSortedArrays sol = new Task62_MedianSortedArrays();

        int[][] numsList1 = {{1, 3}, {1, 2}, {0, 0}, {}, {2}, {1, 3, 5, 7}, {100001}};
        int[][] numsList2 = {{2}, {3, 4}, {0, 0}, {1}, {}, {2, 4, 6, 8}, {100000}};
        double[] expectedMedians = {2.0, 2.5, 0.0, 1.0, 2.0, 4.5, 100000.5};

        for (int i = 0; i < numsList1.length; i++) {
            int[] n1 = numsList1[i];
            int[] n2 = numsList2[i];
            try {
                double median = sol.findMedianSortedArrays(n1, n2);
                System.out.println("Median of " + Arrays.toString(n1) + " and " + Arrays.toString(n2) + ": "
                        + median + " (Expected: " + expectedMedians[i] + ")");
                // Сравнение double требует погрешности
                if (Math.abs(median - expectedMedians[i]) > 1e-9) System.err.println("   Mismatch!");
            } catch (IllegalArgumentException e) {
                System.err.println("Error for " + Arrays.toString(n1) + " and " + Arrays.toString(n2) + ": " + e.getMessage());
                // Если ожидался результат, а получили ошибку
                if (expectedMedians[i] != Double.NaN) System.err.println("   Mismatch! Expected result, got error.");
            }

        }
        // Тест на пустые массивы
        try {
            sol.findMedianSortedArrays(new int[]{}, new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("\nCaught expected error for two empty arrays: " + e.getMessage());
        }
    }
}

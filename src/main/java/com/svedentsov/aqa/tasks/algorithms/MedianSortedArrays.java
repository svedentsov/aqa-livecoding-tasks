package com.svedentsov.aqa.tasks.algorithms;

import java.util.Arrays;

/**
 * Решение задачи №62: Найти медиану двух отсортированных массивов.
 * <p>
 * Описание: (Проверяет: сложные алгоритмы, бинарный поиск)
 * <p>
 * Задание: Напишите метод `double findMedianSortedArrays(int[] nums1, int[] nums2)`,
 * который находит медиану двух отсортированных массивов `nums1` и `nums2`.
 * <p>
 * Пример: `findMedianSortedArrays(new int[]{1, 3}, new int[]{2})` -> `2.0`.
 * `findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})` -> `2.5`.
 */
public class MedianSortedArrays {

    /**
     * Находит медиану двух отсортированных массивов nums1 и nums2.
     * Медиана - это значение, которое делит отсортированный набор данных пополам.
     * Если общее количество элементов нечетное, медиана - это средний элемент.
     * Если четное - среднее арифметическое двух средних элементов.
     * <p>
     * Реализует эффективный алгоритм на основе бинарного поиска по разрезам (partitions)
     * с логарифмической сложностью O(log(min(m, n))), где m и n - длины массивов.
     * <p>
     * Идея: Нужно найти такой разрез в обоих массивах, чтобы все элементы слева
     * от разрезов были меньше или равны всем элементам справа от разрезов,
     * и чтобы общее количество элементов в левых половинах было равно
     * (m + n + 1) / 2 (для нечетного случая) или (m + n) / 2 (для четного, но
     * формула (m+n+1)/2 работает для обоих случаев при правильной обработке медианы).
     *
     * @param nums1 Первый отсортированный массив.
     * @param nums2 Второй отсортированный массив.
     * @return Медиана объединенного отсортированного массива в виде double.
     * @throws IllegalArgumentException если входные массивы некорректны
     *                                  (например, не отсортированы - хотя метод это не проверяет).
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Обработка null массивов как пустых
        if (nums1 == null) nums1 = new int[0];
        if (nums2 == null) nums2 = new int[0];

        int m = nums1.length;
        int n = nums2.length;

        // Убедимся, что бинарный поиск идет по меньшему массиву
        if (m > n) {
            return findMedianSortedArrays(nums2, nums1); // Рекурсивный вызов с обменом
        }

        // Если оба массива пусты - медиану найти нельзя
        if (m == 0 && n == 0) {
            throw new IllegalArgumentException("Cannot find median of two empty arrays.");
        }

        // --- Бинарный поиск разреза в меньшем массиве (nums1) ---
        int low = 0;          // Минимальный индекс разреза в nums1 (0 элементов слева)
        int high = m;         // Максимальный индекс разреза в nums1 (m элементов слева)
        int totalHalfLen = (m + n + 1) / 2; // Общее число элементов в левых половинах

        while (low <= high) {
            // partition1: сколько элементов из nums1 берем в левую половину
            int partition1 = low + (high - low) / 2;
            // partition2: сколько элементов из nums2 нужно взять в левую половину
            int partition2 = totalHalfLen - partition1;

            // Определяем граничные элементы для разреза:
            // maxLeft: максимальный элемент слева от разреза
            // minRight: минимальный элемент справа от разреза
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

            // Проверяем условие правильного разреза: maxLeft <= minRight для обеих пар
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Разрез найден! Вычисляем медиану.

                // Если общее число элементов четное
                if ((m + n) % 2 == 0) {
                    // Медиана - среднее двух центральных элементов
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else { // Если общее число элементов нечетное
                    // Медиана - это максимальный элемент из левых частей
                    return Math.max(maxLeft1, maxLeft2);
                }
            } else if (maxLeft1 > minRight2) {
                // Слишком много элементов взяли из nums1, нужно сдвинуть разрез влево
                high = partition1 - 1;
            } else { // maxLeft2 > minRight1
                // Слишком мало элементов взяли из nums1, нужно сдвинуть разрез вправо
                low = partition1 + 1;
            }
        }

        // Сюда не должны дойти, если массивы отсортированы
        throw new IllegalArgumentException("Input arrays might not be sorted.");
    }

    /**
     * Точка входа для демонстрации работы метода поиска медианы.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        MedianSortedArrays sol = new MedianSortedArrays();

        System.out.println("--- Finding Median of Two Sorted Arrays ---");

        runMedianTest(sol, new int[]{1, 3}, new int[]{2}, "Example 1"); // 2.0
        runMedianTest(sol, new int[]{1, 2}, new int[]{3, 4}, "Example 2"); // 2.5
        runMedianTest(sol, new int[]{0, 0}, new int[]{0, 0}, "All zeroes"); // 0.0
        runMedianTest(sol, new int[]{}, new int[]{1}, "First empty"); // 1.0
        runMedianTest(sol, new int[]{2}, new int[]{}, "Second empty"); // 2.0
        runMedianTest(sol, new int[]{1, 3, 5, 7}, new int[]{2, 4, 6, 8}, "Interleaved"); // 4.5
        runMedianTest(sol, new int[]{100000}, new int[]{100001}, "Large numbers"); // 100000.5
        runMedianTest(sol, new int[]{1, 2, 3}, new int[]{4, 5, 6}, "Separated"); // 3.5
        runMedianTest(sol, new int[]{4, 5, 6}, new int[]{1, 2, 3}, "Separated reversed"); // 3.5
        runMedianTest(sol, new int[]{}, new int[]{}, "Both empty"); // Exception
        runMedianTest(sol, null, new int[]{1}, "First null"); // 1.0
        runMedianTest(sol, new int[]{2}, null, "Second null"); // 2.0
        runMedianTest(sol, null, null, "Both null"); // Exception
    }

    /**
     * Вспомогательный метод для тестирования findMedianSortedArrays.
     *
     * @param sol         Экземпляр решателя.
     * @param nums1       Первый массив.
     * @param nums2       Второй массив.
     * @param description Описание теста.
     */
    private static void runMedianTest(MedianSortedArrays sol, int[] nums1, int[] nums2, String description) {
        System.out.println("\n--- " + description + " ---");
        String n1Str = (nums1 == null ? "null" : Arrays.toString(nums1));
        String n2Str = (nums2 == null ? "null" : Arrays.toString(nums2));
        System.out.println("Input nums1: " + n1Str);
        System.out.println("Input nums2: " + n2Str);
        System.out.print("Median: ");
        try {
            double median = sol.findMedianSortedArrays(nums1, nums2);
            System.out.println(median);
            // Можно добавить сравнение с ожидаемым значением, если оно передано
        } catch (IllegalArgumentException e) {
            System.out.println("Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error - " + e.getMessage());
        }
    }
}

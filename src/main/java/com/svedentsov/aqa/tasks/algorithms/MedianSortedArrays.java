package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №62: Найти медиану двух отсортированных массивов.
 * Описание: (Проверяет: сложные алгоритмы, бинарный поиск)
 * Задание: Напишите метод `double findMedianSortedArrays(int[] nums1, int[] nums2)`,
 * который находит медиану двух отсортированных массивов `nums1` и `nums2`.
 * Пример: `findMedianSortedArrays(new int[]{1, 3}, new int[]{2})` -> `2.0`.
 * `findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})` -> `2.5`.
 */
public class MedianSortedArrays {

    /**
     * Находит медиану двух отсортированных массивов nums1 и nums2.
     * Использует алгоритм на основе бинарного поиска по разрезам (partitions).
     * Сложность: O(log(min(m, n))), где m и n - длины массивов.
     *
     * @param nums1 Первый отсортированный массив. Может быть null.
     * @param nums2 Второй отсортированный массив. Может быть null.
     * @return Медиана объединенного отсортированного массива в виде double.
     * @throws IllegalArgumentException если оба входных массива пусты (после обработки null).
     *                                  Также может выбросить это исключение, если входные массивы
     *                                  не отсортированы, и алгоритм не сможет найти корректный разрез.
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Обработка null массивов как пустых
        if (nums1 == null) nums1 = new int[0];
        if (nums2 == null) nums2 = new int[0];

        int m = nums1.length;
        int n = nums2.length;

        // Убедимся, что бинарный поиск идет по меньшему массиву для эффективности
        if (m > n) {
            return findMedianSortedArrays(nums2, nums1); // Рекурсивный вызов с обменом
        }

        // Если оба массива пусты после обработки null - медиану найти нельзя
        if (m == 0 && n == 0) {
            throw new IllegalArgumentException("Cannot find median of two empty arrays.");
        }

        int low = 0;
        int high = m; // Бинарный поиск по разрезам в меньшем массиве (nums1)
        int totalHalfLen = (m + n + 1) / 2; // Количество элементов в левой "объединенной" половине

        while (low <= high) {
            // partition1: количество элементов из nums1 в левой половине
            int partition1 = low + (high - low) / 2;
            // partition2: количество элементов из nums2 в левой половине
            int partition2 = totalHalfLen - partition1;

            // Определяем граничные элементы для разреза
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

            // Проверяем условие правильного разреза
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Разрез найден, вычисляем медиану
                if ((m + n) % 2 == 0) { // Четное общее количество элементов
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else { // Нечетное общее количество элементов
                    return Math.max(maxLeft1, maxLeft2);
                }
            } else if (maxLeft1 > minRight2) {
                // Слишком много элементов из nums1 в левой части, сдвигаем разрез влево в nums1
                high = partition1 - 1;
            } else { // maxLeft2 > minRight1
                // Слишком мало элементов из nums1 в левой части, сдвигаем разрез вправо в nums1
                low = partition1 + 1;
            }
        }
        // Сюда не должны доходить, если массивы отсортированы и логика верна.
        // Этот throw может сработать, если входные массивы не отсортированы,
        // или если есть ошибка в логике границ бинарного поиска.
        throw new IllegalArgumentException("Input arrays might not be sorted or other logic error occurred.");
    }
}

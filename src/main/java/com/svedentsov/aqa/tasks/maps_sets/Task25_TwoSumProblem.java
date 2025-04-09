package com.svedentsov.aqa.tasks.maps_sets; // или arrays_lists

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Решение задачи №25: Two Sum Problem (Найти два числа с заданной суммой).
 */
public class Task25_TwoSumProblem {

    /**
     * Находит индексы двух чисел в массиве, сумма которых равна заданному целевому значению.
     * Использует HashMap для хранения уже просмотренных чисел и их индексов,
     * что позволяет найти решение за один проход по массиву (O(n) по времени).
     * O(n) по памяти для хранения карты.
     * Предполагается, что для каждого входа существует ровно одно решение (как в классической постановке LeetCode),
     * и нельзя использовать один и тот же элемент дважды.
     *
     * @param nums   Массив целых чисел.
     * @param target Целевая сумма.
     * @return Массив из двух индексов {@code [index1, index2]}, сумма элементов
     * которых равна {@code target}. Если решение не найдено, возвращает пустой массив.
     * @throws IllegalArgumentException если входной массив null.
     */
    public int[] findTwoSumIndices(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        // Карта для хранения: {значение_числа: его_индекс}
        Map<Integer, Integer> numIndexMap = new HashMap<>();

        // Проходим по массиву
        for (int i = 0; i < nums.length; i++) {
            int currentNum = nums[i];
            // Вычисляем, какое число нужно найти (дополнение до target)
            int complement = target - currentNum;

            // Проверяем, есть ли искомое дополнение УЖЕ в карте
            // Если есть, значит, мы нашли второе число пары.
            if (numIndexMap.containsKey(complement)) {
                // Возвращаем массив из двух индексов:
                // индекс дополнения (из карты) и текущий индекс i.
                return new int[]{numIndexMap.get(complement), i};
            }

            // Если дополнение не найдено, добавляем ТЕКУЩЕЕ число и его индекс в карту.
            // Это делается для того, чтобы на последующих итерациях можно было найти
            // текущее число как дополнение для какого-либо будущего элемента.
            numIndexMap.put(currentNum, i);
        }

        // Если цикл завершен, а пара не найдена (например, если нет решения)
        return new int[0]; // Возвращаем пустой массив
    }

    /**
     * Точка входа для демонстрации работы метода поиска двух чисел с заданной суммой.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task25_TwoSumProblem sol = new Task25_TwoSumProblem();

        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        int[] result1 = sol.findTwoSumIndices(nums1, target1);
        System.out.println("Nums: " + Arrays.toString(nums1) + ", Target: " + target1 + " -> Indices: " + Arrays.toString(result1)); // [0, 1] (т.к. 2+7=9)

        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        int[] result2 = sol.findTwoSumIndices(nums2, target2);
        System.out.println("Nums: " + Arrays.toString(nums2) + ", Target: " + target2 + " -> Indices: " + Arrays.toString(result2)); // [1, 2] (т.к. 2+4=6)

        int[] nums3 = {3, 3}; // Случай с одинаковыми числами
        int target3 = 6;
        int[] result3 = sol.findTwoSumIndices(nums3, target3);
        System.out.println("Nums: " + Arrays.toString(nums3) + ", Target: " + target3 + " -> Indices: " + Arrays.toString(result3)); // [0, 1] (т.к. 3+3=6)

        int[] nums4 = {0, 4, 3, 0};
        int target4 = 0;
        int[] result4 = sol.findTwoSumIndices(nums4, target4);
        System.out.println("Nums: " + Arrays.toString(nums4) + ", Target: " + target4 + " -> Indices: " + Arrays.toString(result4)); // [0, 3] (т.к. 0+0=0)

        int[] nums5 = {-1, -3, 5, 9};
        int target5 = 4;
        int[] result5 = sol.findTwoSumIndices(nums5, target5);
        System.out.println("Nums: " + Arrays.toString(nums5) + ", Target: " + target5 + " -> Indices: " + Arrays.toString(result5)); // [0, 2] (т.к. -1+5=4)


        int[] nums6 = {1, 2, 3, 4, 5};
        int target6 = 10;
        int[] result6 = sol.findTwoSumIndices(nums6, target6);
        System.out.println("Nums: " + Arrays.toString(nums6) + ", Target: " + target6 + " -> Indices: " + Arrays.toString(result6)); // [] (нет решения)

        int[] nums7 = {};
        int target7 = 5;
        int[] result7 = sol.findTwoSumIndices(nums7, target7);
        System.out.println("Nums: " + Arrays.toString(nums7) + ", Target: " + target7 + " -> Indices: " + Arrays.toString(result7)); // []
    }
}

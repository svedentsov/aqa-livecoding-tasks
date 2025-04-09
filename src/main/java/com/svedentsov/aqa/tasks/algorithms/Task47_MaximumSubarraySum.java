package com.svedentsov.aqa.tasks.algorithms;

import java.util.Arrays;

/**
 * Решение задачи №47: Максимальная сумма подмассива (Алгоритм Кадане).
 */
public class Task47_MaximumSubarraySum {

    /**
     * Находит максимальную сумму непрерывного подмассива в заданном массиве целых чисел,
     * используя алгоритм Кадане.
     * Подмассив должен содержать хотя бы один элемент.
     * Алгоритм за один проход вычисляет `maxEndingHere` - максимальную сумму подмассива,
     * заканчивающегося на текущей позиции `i`, и `maxSoFar` - максимальную сумму,
     * найденную на всем массиве до текущей позиции.
     * `maxEndingHere = max(nums[i], maxEndingHere + nums[i])`
     * `maxSoFar = max(maxSoFar, maxEndingHere)`
     * Сложность O(n) по времени, O(1) по памяти.
     *
     * @param nums Массив целых чисел. Не должен быть null или пустым.
     * @return Максимальная сумма непрерывного подмассива.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int maxSubArraySum(int[] nums) {
        // Проверка на недопустимые входные данные
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        // Инициализация переменных первым элементом массива
        int maxSoFar = nums[0];      // Глобальный максимум (результат)
        int maxEndingHere = nums[0]; // Максимум, заканчивающийся на текущей позиции

        // Проходим по массиву, начиная со второго элемента
        for (int i = 1; i < nums.length; i++) {
            int currentNum = nums[i];

            // Выбираем: либо начать новый подмассив с currentNum,
            // либо продолжить предыдущий подмассив, добавив currentNum.
            // Если maxEndingHere до этого был отрицательным, выгоднее начать новый подмассив.
            maxEndingHere = Math.max(currentNum, maxEndingHere + currentNum);

            // Обновляем глобальный максимум, если текущий локальный максимум больше.
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    /**
     * Точка входа для демонстрации работы алгоритма Кадане.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task47_MaximumSubarraySum sol = new Task47_MaximumSubarraySum();

        int[][] testArrays = {
                {-2, 1, -3, 4, -1, 2, 1, -5, 4}, // 6 -> [4, -1, 2, 1]
                {1},                            // 1 -> [1]
                {5, 4, -1, 7, 8},             // 23 -> [5, 4, -1, 7, 8]
                {-1, -2, -3},                   // -1 -> [-1]
                {-1},                           // -1 -> [-1]
                {1, 2, 3, -2, 5},             // 9 -> [1, 2, 3, -2, 5]
                {-1, -2, 3, 4, -5, 6}          // 8 -> [3, 4, -5, 6]
        };
        int[] expectedSums = {6, 1, 23, -1, -1, 9, 8};


        for (int i = 0; i < testArrays.length; i++) {
            try {
                int result = sol.maxSubArraySum(testArrays[i]);
                System.out.println("Max subarray sum for " + Arrays.toString(testArrays[i]) + ": " + result + " (Expected: " + expectedSums[i] + ")");
                if (result != expectedSums[i]) System.err.println("   Mismatch!");
            } catch (IllegalArgumentException e) {
                System.err.println("Error processing " + Arrays.toString(testArrays[i]) + ": " + e.getMessage());
            }
        }

        // Тест на пустом массиве
        try {
            sol.maxSubArraySum(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("\nError on empty array: " + e.getMessage());
        }
        // Тест на null массиве
        try {
            sol.maxSubArraySum(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error on null array: " + e.getMessage());
        }
    }
}

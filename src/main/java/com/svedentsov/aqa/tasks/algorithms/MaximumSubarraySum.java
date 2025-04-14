package com.svedentsov.aqa.tasks.algorithms;

import java.util.Arrays;

/**
 * Решение задачи №47: Максимальная сумма подмассива (Алгоритм Кадане).
 * <p>
 * Описание: Найти непрерывный подмассив с наибольшей суммой.
 * (Проверяет: алгоритмы, циклы)
 * <p>
 * Задание: Напишите метод `int maxSubArraySum(int[] nums)`, который находит
 * максимальную сумму непрерывного подмассива в массиве `nums` (подмассив
 * должен содержать хотя бы один элемент).
 * <p>
 * Пример: `maxSubArraySum(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})` -> `6`
 * (подмассив `[4, -1, 2, 1]`).
 * `maxSubArraySum(new int[]{1})` -> `1`.
 * `maxSubArraySum(new int[]{5, 4, -1, 7, 8})` -> `23`.
 */
public class MaximumSubarraySum {

    /**
     * Находит максимальную сумму непрерывного подмассива в заданном массиве целых чисел,
     * используя алгоритм Кадане.
     * Подмассив должен содержать хотя бы один элемент.
     * <p>
     * Алгоритм:
     * 1. Инициализировать `maxSoFar` (глобальный максимум) и `maxEndingHere` (максимум,
     * заканчивающийся на текущей позиции) первым элементом массива.
     * 2. Итерировать по массиву, начиная со второго элемента (`i = 1`).
     * 3. На каждой итерации `i`:
     * a. Вычислить новый `maxEndingHere`: это либо сам `nums[i]`, либо
     * `maxEndingHere` предыдущей итерации + `nums[i]`. Выбираем большее.
     * Это означает: либо начинаем новый подмассив с `nums[i]`, либо
     * продолжаем существующий, если он все еще положителен.
     * b. Обновить `maxSoFar`: `maxSoFar = max(maxSoFar, maxEndingHere)`.
     * 4. Вернуть `maxSoFar`.
     * <p>
     * Сложность: O(n) по времени, O(1) по памяти.
     *
     * @param nums Массив целых чисел. Не должен быть null или пустым.
     * @return Максимальная сумма непрерывного подмассива.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int maxSubArraySum(int[] nums) {
        // Шаг 0: Проверка на недопустимые входные данные
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        // Шаг 1: Инициализация
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];

        // Шаг 2 & 3: Итерация и вычисление максимумов
        for (int i = 1; i < nums.length; i++) {
            int currentNum = nums[i];
            // Шаг 3a: Обновляем максимум, заканчивающийся здесь
            maxEndingHere = Math.max(currentNum, maxEndingHere + currentNum);
            // Шаг 3b: Обновляем глобальный максимум
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        // Шаг 4: Возвращаем результат
        return maxSoFar;
    }

    /**
     * Точка входа для демонстрации работы алгоритма Кадане.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        MaximumSubarraySum sol = new MaximumSubarraySum();

        int[][] testArrays = {
                {-2, 1, -3, 4, -1, 2, 1, -5, 4}, // 6 -> [4, -1, 2, 1]
                {1},                             // 1 -> [1]
                {5, 4, -1, 7, 8},                // 23 -> [5, 4, -1, 7, 8]
                {-1, -2, -3},                    // -1 -> [-1] (т.к. подмассив д.б. >= 1 элемента)
                {-1},                            // -1 -> [-1]
                {1, 2, 3, -2, 5},                // 9 -> [1, 2, 3, -2, 5]
                {-1, -2, 3, 4, -5, 6},           // 8 -> [3, 4, -5, 6]
                {2, 3, -1, -20, 5, 5}            // 10 -> [5, 5]
        };

        System.out.println("--- Finding Maximum Subarray Sum (Kadane's Algorithm) ---");
        for (int[] arr : testArrays) {
            runMaxSubarrayTest(sol, arr);
        }

        // Тесты на исключения
        runMaxSubarrayTest(sol, new int[]{}); // Exception
        runMaxSubarrayTest(sol, null); // Exception
    }

    /**
     * Вспомогательный метод для тестирования maxSubArraySum.
     *
     * @param sol Экземпляр решателя.
     * @param arr Тестовый массив.
     */
    private static void runMaxSubarrayTest(MaximumSubarraySum sol, int[] arr) {
        String arrString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("\nInput array: " + arrString);
        try {
            int result = sol.maxSubArraySum(arr);
            System.out.println("Max Sum: " + result);
            // Можно добавить сравнение с ожидаемым значением, если они переданы
        } catch (IllegalArgumentException e) {
            System.out.println("Max Sum: Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Max Sum: Unexpected Error - " + e.getMessage());
        }
    }
}

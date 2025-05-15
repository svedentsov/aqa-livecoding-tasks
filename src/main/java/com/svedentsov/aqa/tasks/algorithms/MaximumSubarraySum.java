package com.svedentsov.aqa.tasks.algorithms;

/**
 * Решение задачи №47: Максимальная сумма подмассива (Алгоритм Кадане).
 * Описание: Найти непрерывный подмассив с наибольшей суммой.
 * (Проверяет: алгоритмы, циклы)
 * Задание: Напишите метод `int maxSubArraySum(int[] nums)`, который находит
 * максимальную сумму непрерывного подмассива в массиве `nums` (подмассив
 * должен содержать хотя бы один элемент).
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
        // Инициализируем первым элементом, т.к. подмассив должен содержать хотя бы один элемент.
        int maxSoFar = nums[0];
        int maxEndingHere = nums[0];

        // Шаг 2 & 3: Итерация и вычисление максимумов (начиная со второго элемента)
        for (int i = 1; i < nums.length; i++) {
            int currentNum = nums[i];
            // Шаг 3a: Обновляем максимум, заканчивающийся здесь
            // Либо начинаем новый подмассив с currentNum, либо продолжаем старый
            maxEndingHere = Math.max(currentNum, maxEndingHere + currentNum);
            // Шаг 3b: Обновляем глобальный максимум
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        // Шаг 4: Возвращаем результат
        return maxSoFar;
    }
}

package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №6: Найти максимальный/минимальный элемент в массиве.
 */
public class Task06_FindMaxMinArray {

    /**
     * Находит максимальный элемент в массиве целых чисел.
     * Проходит по массиву, сравнивая текущий элемент с текущим максимумом.
     *
     * @param numbers Массив целых чисел.
     * @return Максимальное значение в массиве.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findMax(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        int max = numbers[0]; // Начинаем с первого элемента как с текущего максимума
        // Итерируем со второго элемента (индекс 1)
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i]; // Обновляем максимум, если нашли элемент больше
            }
        }
        return max;
    }

    /**
     * Находит минимальный элемент в массиве целых чисел.
     * Проходит по массиву, сравнивая текущий элемент с текущим минимумом.
     *
     * @param numbers Массив целых чисел.
     * @return Минимальное значение в массиве.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findMin(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        int min = numbers[0]; // Начинаем с первого элемента как с текущего минимума
        // Итерируем со второго элемента (индекс 1)
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < min) {
                min = numbers[i]; // Обновляем минимум, если нашли элемент меньше
            }
        }
        return min;
    }

    /**
     * Точка входа для демонстрации работы методов поиска min/max.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task06_FindMaxMinArray sol = new Task06_FindMaxMinArray();
        int[] arr1 = {1, 5, 2, 9, 3};
        int[] arr2 = {-1, -5, -2, -9, -3};
        int[] arr3 = {5};

        System.out.println("Max in " + Arrays.toString(arr1) + ": " + sol.findMax(arr1)); // 9
        System.out.println("Min in " + Arrays.toString(arr1) + ": " + sol.findMin(arr1)); // 1

        System.out.println("Max in " + Arrays.toString(arr2) + ": " + sol.findMax(arr2)); // -1
        System.out.println("Min in " + Arrays.toString(arr2) + ": " + sol.findMin(arr2)); // -9

        System.out.println("Max in " + Arrays.toString(arr3) + ": " + sol.findMax(arr3)); // 5
        System.out.println("Min in " + Arrays.toString(arr3) + ": " + sol.findMin(arr3)); // 5

        try {
            sol.findMax(new int[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("Error finding max in empty array: " + e.getMessage());
        }
        try {
            sol.findMin(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Error finding min in null array: " + e.getMessage());
        }
    }
}

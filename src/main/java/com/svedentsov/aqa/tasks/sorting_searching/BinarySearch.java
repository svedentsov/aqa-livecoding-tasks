package com.svedentsov.aqa.tasks.sorting_searching;

import java.util.Arrays;

/**
 * Решение задачи №38: Бинарный поиск в отсортированном массиве.
 * <p>
 * Описание: Реализовать алгоритм бинарного поиска.
 * (Проверяет: алгоритмы, работа с индексами, рекурсия/циклы)
 * <p>
 * Задание: Напишите метод `int binarySearch(int[] sortedArray, int key)`, который
 * ищет индекс элемента `key` в отсортированном по возрастанию массиве `sortedArray`
 * с помощью алгоритма бинарного поиска. Если элемент не найден, верните -1.
 * <p>
 * Пример: `binarySearch(new int[]{1, 3, 5, 7, 9}, 5)` -> `2`.
 * `binarySearch(new int[]{1, 3, 5, 7, 9}, 6)` -> `-1`.
 */
public class BinarySearch {

    /**
     * Выполняет бинарный поиск ключа {@code key} в отсортированном по возрастанию
     * массиве целых чисел {@code sortedArray}.
     * Итеративная реализация.
     * <p>
     * Сложность: O(log n) по времени, O(1) по памяти.
     * <p>
     * Алгоритм:
     * 1. Инициализировать границы поиска `left = 0`, `right = length - 1`.
     * 2. Пока `left <= right`:
     * a. Вычислить средний индекс `mid = left + (right - left) / 2` (для избежания переполнения).
     * b. Если `sortedArray[mid] == key`, вернуть `mid`.
     * c. Если `sortedArray[mid] < key`, искать в правой половине: `left = mid + 1`.
     * d. Если `sortedArray[mid] > key`, искать в левой половине: `right = mid - 1`.
     * 3. Если цикл завершился (left > right), элемент не найден, вернуть -1.
     *
     * @param sortedArray Отсортированный по возрастанию массив, в котором выполняется поиск.
     *                    Может быть null.
     * @param key         Значение, которое нужно найти.
     * @return Индекс первого найденного элемента {@code key} в массиве,
     * или -1, если элемент не найден или массив равен null/пуст.
     */
    public int binarySearch(int[] sortedArray, int key) {
        // Проверка на null или пустой массив
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }

        int left = 0;
        int right = sortedArray.length - 1;

        while (left <= right) {
            // Вычисление среднего индекса безопасно от переполнения
            int mid = left + (right - left) / 2;
            int midValue = sortedArray[mid];

            if (midValue == key) {
                return mid; // Элемент найден
            } else if (midValue < key) {
                left = mid + 1; // Искать справа
            } else { // midValue > key
                right = mid - 1; // Искать слева
            }
        }

        // Элемент не найден
        return -1;
    }

    /**
     * Точка входа для демонстрации работы метода бинарного поиска.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        BinarySearch sol = new BinarySearch();
        System.out.println("--- Testing Binary Search ---");

        int[] arr1 = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        runBinarySearchTest(sol, arr1, 5, "Key in the middle");     // 2
        runBinarySearchTest(sol, arr1, 1, "Key is first element");  // 0
        runBinarySearchTest(sol, arr1, 19, "Key is last element"); // 9
        runBinarySearchTest(sol, arr1, 6, "Key not present (between elements)"); // -1
        runBinarySearchTest(sol, arr1, 0, "Key not present (smaller than first)"); // -1
        runBinarySearchTest(sol, arr1, 20, "Key not present (larger than last)"); // -1
        runBinarySearchTest(sol, arr1, 13, "Another key present"); // 6

        int[] arr2 = {2, 2, 2, 2, 2};
        runBinarySearchTest(sol, arr2, 2, "Array with duplicates (key present)"); // 2 (может вернуть любой из индексов)
        runBinarySearchTest(sol, arr2, 3, "Array with duplicates (key not present)"); // -1

        int[] arr3 = {10};
        runBinarySearchTest(sol, arr3, 10, "Single element array (key present)"); // 0
        runBinarySearchTest(sol, arr3, 5, "Single element array (key not present)"); // -1

        int[] arr4 = {};
        runBinarySearchTest(sol, arr4, 5, "Empty array"); // -1

        int[] arr5 = null;
        runBinarySearchTest(sol, arr5, 5, "Null array"); // -1
    }

    /**
     * Вспомогательный метод для тестирования бинарного поиска.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Отсортированный массив.
     * @param key         Искомый ключ.
     * @param description Описание теста.
     */
    private static void runBinarySearchTest(BinarySearch sol, int[] arr, int key, String description) {
        System.out.println("\n--- " + description + " ---");
        String arrString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("Array: " + arrString + ", Key: " + key);
        try {
            int index = sol.binarySearch(arr, key);
            System.out.println("Result Index: " + index);
            // Можно добавить сравнение с ожидаемым значением, если оно передано
        } catch (Exception e) {
            System.err.println("Result Index: Error - " + e.getMessage());
        }
    }
}

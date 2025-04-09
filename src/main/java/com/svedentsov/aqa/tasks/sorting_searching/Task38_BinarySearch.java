package com.svedentsov.aqa.tasks.sorting_searching;

import java.util.Arrays;

/**
 * Решение задачи №38: Бинарный поиск в отсортированном массиве.
 */
public class Task38_BinarySearch {

    /**
     * Выполняет бинарный поиск ключа {@code key} в отсортированном по возрастанию
     * массиве целых чисел {@code sortedArray}.
     * Итеративная реализация.
     * Сложность O(log n) по времени, O(1) по памяти.
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

        int left = 0;                     // Левая граница поискового диапазона (индекс)
        int right = sortedArray.length - 1; // Правая граница поискового диапазона (индекс)

        // Цикл продолжается, пока левая граница не станет больше правой (диапазон не пуст)
        while (left <= right) {
            // Вычисляем средний индекс. Используем (right - left) / 2 для предотвращения
            // возможного переполнения int при сложении left + right для очень больших массивов.
            int mid = left + (right - left) / 2;

            // Сравниваем элемент в середине с искомым ключом
            if (sortedArray[mid] == key) {
                return mid; // Элемент найден! Возвращаем его индекс.
            } else if (sortedArray[mid] < key) {
                // Если средний элемент меньше ключа, значит ключ (если он есть)
                // находится в правой половине массива. Сдвигаем левую границу.
                left = mid + 1;
            } else { // sortedArray[mid] > key
                // Если средний элемент больше ключа, значит ключ (если он есть)
                // находится в левой половине массива. Сдвигаем правую границу.
                right = mid - 1;
            }
        }

        // Если цикл завершился (left > right), значит ключ не найден в массиве.
        return -1;
    }

    /**
     * Точка входа для демонстрации работы метода бинарного поиска.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task38_BinarySearch sol = new Task38_BinarySearch();
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19}; // Отсортированный массив
        System.out.println("Array: " + Arrays.toString(arr));

        int[] keysToSearch = {5, 19, 1, 6, 20, 0, 13};
        int[] expectedIndices = {2, 9, 0, -1, -1, -1, 6};

        for (int i = 0; i < keysToSearch.length; i++) {
            int key = keysToSearch[i];
            int index = sol.binarySearch(arr, key);
            System.out.println("Searching for " + key + ": Index = " + index + " (Expected: " + expectedIndices[i] + ")");
            if (index != expectedIndices[i]) System.err.println("   Mismatch!");
        }


        // Тесты с пустым и null массивами
        int[] emptyArr = {};
        System.out.println("\nArray: " + Arrays.toString(emptyArr));
        System.out.println("Searching for 5: Index = " + sol.binarySearch(emptyArr, 5) + " (Expected: -1)");

        System.out.println("\nArray: null");
        System.out.println("Searching for 5: Index = " + sol.binarySearch(null, 5) + " (Expected: -1)");
    }
}

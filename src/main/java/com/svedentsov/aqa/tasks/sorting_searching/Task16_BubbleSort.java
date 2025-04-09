package com.svedentsov.aqa.tasks.sorting_searching;

import java.util.Arrays;

/**
 * Решение задачи №16: Сортировка массива методом пузырька (Bubble Sort).
 */
public class Task16_BubbleSort {

    /**
     * Сортирует массив целых чисел по возрастанию с использованием алгоритма пузырьковой сортировки.
     * Алгоритм проходит по массиву несколько раз, на каждом проходе сравнивая соседние
     * элементы и меняя их местами, если они стоят в неправильном порядке.
     * Большие элементы "всплывают" к концу массива.
     * Сложность: O(n^2) в среднем и худшем случае, O(n) в лучшем (если массив уже отсортирован,
     * благодаря оптимизации с флагом swapped). O(1) по дополнительной памяти.
     * Неэффективен для больших массивов.
     *
     * @param arr Массив для сортировки. Сортируется на месте (in-place). Может быть null.
     */
    public void bubbleSort(int[] arr) {
        // Если массив null или содержит 0/1 элемент, он уже отсортирован
        if (arr == null || arr.length < 2) {
            return;
        }

        int n = arr.length;
        boolean swapped; // Флаг для оптимизации: если за проход не было обменов, массив отсортирован

        // Внешний цикл: определяет количество проходов
        // После i-го прохода последние i элементов гарантированно на своих местах,
        // поэтому внешний цикл может идти до n-1.
        for (int i = 0; i < n - 1; i++) {
            swapped = false; // Сбрасываем флаг перед каждым проходом

            // Внутренний цикл: сравнение и обмен соседних элементов
            // Идет до n - 1 - i, т.к. элементы после этой границы уже отсортированы
            // на предыдущих проходах.
            for (int j = 0; j < n - 1 - i; j++) {
                // Если текущий элемент больше следующего
                if (arr[j] > arr[j + 1]) {
                    // Меняем их местами
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true; // Устанавливаем флаг, т.к. был произведен обмен
                }
            }

            // Оптимизация: если на данном проходе не было ни одного обмена,
            // это означает, что массив уже полностью отсортирован, и можно выйти досрочно.
            if (!swapped) {
                // System.out.println("Optimization triggered: Array sorted early at pass " + (i + 1));
                break;
            }
        }
    }

    /**
     * Точка входа для демонстрации работы метода пузырьковой сортировки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task16_BubbleSort sol = new Task16_BubbleSort();

        int[] myArray1 = {5, 1, 4, 2, 8};
        System.out.println("Original: " + Arrays.toString(myArray1));
        sol.bubbleSort(myArray1);
        System.out.println("Sorted:   " + Arrays.toString(myArray1)); // [1, 2, 4, 5, 8]

        int[] myArray2 = {1, 2, 3, 4, 5};
        System.out.println("\nOriginal: " + Arrays.toString(myArray2));
        sol.bubbleSort(myArray2); // Сработает оптимизация
        System.out.println("Sorted:   " + Arrays.toString(myArray2)); // [1, 2, 3, 4, 5]

        int[] myArray3 = {9, 1, 5, 3, 7, 2};
        System.out.println("\nOriginal: " + Arrays.toString(myArray3));
        sol.bubbleSort(myArray3);
        System.out.println("Sorted:   " + Arrays.toString(myArray3)); // [1, 2, 3, 5, 7, 9]

        int[] myArray4 = {};
        System.out.println("\nOriginal: " + Arrays.toString(myArray4));
        sol.bubbleSort(myArray4);
        System.out.println("Sorted:   " + Arrays.toString(myArray4)); // []

        int[] myArray5 = {5};
        System.out.println("\nOriginal: " + Arrays.toString(myArray5));
        sol.bubbleSort(myArray5);
        System.out.println("Sorted:   " + Arrays.toString(myArray5)); // [5]

        System.out.println("\nOriginal: null");
        sol.bubbleSort(null);
        System.out.println("Sorted:   null"); // null
    }
}

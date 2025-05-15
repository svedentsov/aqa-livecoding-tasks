package com.svedentsov.aqa.tasks.sorting_searching;

import java.util.Arrays;
import java.util.Random;

/**
 * Решение задачи №67: Быстрая сортировка (Quick Sort).
 * Описание: Реализовать алгоритм.
 * (Проверяет: рекурсия, алгоритмы сортировки, выбор опорного элемента)
 * Задание: Реализуйте метод `void quickSort(int[] arr)`, который сортирует
 * массив `arr` по возрастанию, используя рекурсивный алгоритм быстрой сортировки.
 * Пример: Исходный `[5, 1, 4, 2, 8]` после `quickSort` станет `[1, 2, 4, 5, 8]`.
 */
public class QuickSort {

    // Используем один экземпляр Random для выбора pivot
    private static final Random RAND = new Random();

    /**
     * Сортирует массив целых чисел по возрастанию, используя алгоритм быстрой сортировки (QuickSort).
     * Модифицирует массив "на месте" (in-place).
     * Использует случайный выбор опорного элемента для уменьшения вероятности худшего случая O(n^2).
     * Сложность:
     * - Время: O(n log n) в среднем, O(n^2) в худшем случае.
     * - Память: O(log n) в среднем (стек рекурсии), O(n) в худшем случае.
     *
     * @param arr Массив для сортировки. Может быть null. Модифицируется на месте.
     */
    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // Массив уже отсортирован или невалиден
        }
        quickSortRecursive(arr, 0, arr.length - 1);
    }

    /**
     * Точка входа для демонстрации работы метода быстрой сортировки.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        QuickSort sol = new QuickSort();

        System.out.println("--- Quick Sort Demonstration ---");

        runQuickSortTest(sol, new int[]{5, 1, 4, 2, 8, 5, 0, -3, 99}, "Смешанный массив");
        runQuickSortTest(sol, new int[]{1}, "Один элемент");
        runQuickSortTest(sol, new int[]{3, 1}, "Два элемента");
        runQuickSortTest(sol, new int[]{}, "Пустой массив");
        runQuickSortTest(sol, new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, "Обратно отсортированный");
        runQuickSortTest(sol, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, "Уже отсортированный");
        runQuickSortTest(sol, new int[]{2, 3, 1, 5, 4, 7, 6, 9, 8}, "Случайный порядок");
        runQuickSortTest(sol, new int[]{3, 1, 4, 1, 5, 9, 2, 6, 5}, "С дубликатами");
        runQuickSortTest(sol, null, "Null массив");
    }

    /**
     * Рекурсивная функция быстрой сортировки для подмассива {@code arr[low..high]}.
     *
     * @param arr  Массив.
     * @param low  Нижняя граница (индекс) подмассива (включительно).
     * @param high Верхняя граница (индекс) подмассива (включительно).
     */
    private void quickSortRecursive(int[] arr, int low, int high) {
        // Базовый случай: подмассив содержит 0 или 1 элемент
        if (low < high) {
            // Находим индекс опорного элемента после разделения
            int pivotIndex = partitionRandom(arr, low, high);
            // Рекурсивно сортируем левую и правую части
            quickSortRecursive(arr, low, pivotIndex - 1);
            quickSortRecursive(arr, pivotIndex + 1, high);
        }
    }

    /**
     * Выполняет разделение (partitioning) подмассива {@code arr[low..high]}
     * относительно случайно выбранного опорного элемента (pivot).
     * Использует вариацию схемы разделения Хоара или Ломуто. Здесь показана Ломуто.
     *
     * @param arr  Массив.
     * @param low  Нижняя граница подмассива.
     * @param high Верхняя граница подмассива.
     * @return Индекс, на котором опорный элемент оказался после разделения.
     */
    private int partitionRandom(int[] arr, int low, int high) {
        // 1. Выбираем случайный опорный элемент и ставим его в конец
        int pivotIndex = low + RAND.nextInt(high - low + 1);
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, high); // Перемещаем опорный в конец

        // 2. storeIndex - место для следующего элемента <= pivot
        int storeIndex = low;

        // 3. Проходим по подмассиву (кроме опорного в конце)
        for (int i = low; i < high; i++) {
            if (arr[i] <= pivotValue) {
                swap(arr, storeIndex, i); // Перемещаем элемент <= pivot влево
                storeIndex++; // Сдвигаем границу
            }
        }

        // 4. Ставим опорный элемент на его финальное место (storeIndex)
        swap(arr, storeIndex, high);
        return storeIndex; // Возвращаем индекс опорного элемента
    }

    /**
     * Вспомогательный метод для обмена двух элементов в массиве.
     *
     * @param arr Массив.
     * @param i   Индекс первого элемента.
     * @param j   Индекс второго элемента.
     */
    private void swap(int[] arr, int i, int j) {
        if (i == j) return;
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Вспомогательный метод для тестирования quickSort.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Массив для сортировки (будет изменен!).
     * @param description Описание теста.
     */
    private static void runQuickSortTest(QuickSort sol, int[] arr, String description) {
        System.out.println("\n--- " + description + " ---");
        String originalString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("Original: " + originalString);
        // Создаем копию, чтобы не менять массив, переданный извне (если он используется дальше)
        int[] arrCopy = (arr == null) ? null : arr.clone();
        try {
            sol.quickSort(arrCopy); // Сортируем копию
            String sortedString = (arrCopy == null ? "null" : Arrays.toString(arrCopy));
            System.out.println("Sorted:   " + sortedString);
            // Можно добавить проверку на отсортированность
            // if (arrCopy != null && !isSorted(arrCopy)) System.err.println("   Array is NOT sorted!");
        } catch (Exception e) {
            System.err.println("Sorted:   Error - " + e.getMessage());
        }
    }

    // Метод для проверки (если нужен)
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) return false;
        }
        return true;
    }
}

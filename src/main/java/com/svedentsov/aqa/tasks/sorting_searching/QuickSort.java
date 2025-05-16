package com.svedentsov.aqa.tasks.sorting_searching;

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
     * Рекурсивная функция быстрой сортировки для подмассива {@code arr[low..high]}.
     *
     * @param arr  Массив.
     * @param low  Нижняя граница (индекс) подмассива (включительно).
     * @param high Верхняя граница (индекс) подмассива (включительно).
     */
    private void quickSortRecursive(int[] arr, int low, int high) {
        // Базовый случай: подмассив содержит 0 или 1 элемент (если low >= high)
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
     * Использует схему разделения Ломуто.
     *
     * @param arr  Массив.
     * @param low  Нижняя граница подмассива.
     * @param high Верхняя граница подмассива.
     * @return Индекс, на котором опорный элемент оказался после разделения.
     */
    private int partitionRandom(int[] arr, int low, int high) {
        // 1. Выбираем случайный опорный элемент и ставим его в конец (для удобства схемы Ломуто)
        int pivotRandomIndex = low + RAND.nextInt(high - low + 1);
        swap(arr, pivotRandomIndex, high); // Перемещаем опорный в конец
        int pivotValue = arr[high]; // Опорное значение теперь в arr[high]

        // 2. storeIndex - место для следующего элемента, который <= pivotValue
        // Все элементы до storeIndex (не включая его) будут <= pivotValue
        int storeIndex = low;

        // 3. Проходим по подмассиву (от low до high-1, т.к. опорный в arr[high])
        for (int i = low; i < high; i++) {
            if (arr[i] <= pivotValue) { // Если текущий элемент меньше или равен опорному
                swap(arr, storeIndex, i); // Перемещаем его в левую часть (до storeIndex)
                storeIndex++;             // Сдвигаем границу для меньших/равных элементов
            }
        }

        // 4. Ставим опорный элемент (который был в arr[high]) на его финальное место (storeIndex)
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
        if (i == j) return; // Нет смысла менять элемент с самим собой
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

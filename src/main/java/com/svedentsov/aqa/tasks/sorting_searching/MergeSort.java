package com.svedentsov.aqa.tasks.sorting_searching;

import java.util.Arrays;

/**
 * Решение задачи №66: Сортировка слиянием (Merge Sort).
 * <p>
 * Описание: Реализовать алгоритм.
 * (Проверяет: рекурсия, алгоритмы сортировки)
 * <p>
 * Задание: Реализуйте метод `void mergeSort(int[] arr)`, который сортирует
 * массив `arr` по возрастанию, используя рекурсивный алгоритм сортировки слиянием.
 * <p>
 * Пример: Исходный `[5, 1, 4, 2, 8]` после `mergeSort` станет `[1, 2, 4, 5, 8]`.
 */
public class MergeSort {

    /**
     * Сортирует массив целых чисел по возрастанию, используя алгоритм сортировки слиянием.
     * Модифицирует исходный массив {@code arr} на месте.
     * <p>
     * Сложность: O(n log n) по времени (всегда), O(n) по дополнительной памяти.
     * Стабильный алгоритм.
     *
     * @param arr Массив для сортировки. Может быть null.
     */
    public void mergeSort(int[] arr) {
        // Проверка на null и тривиальные случаи (0 или 1 элемент)
        if (arr == null || arr.length <= 1) {
            return;
        }
        // Создаем временный буферный массив один раз для использования в merge
        // Это небольшая оптимизация, чтобы не создавать его на каждом шаге рекурсии.
        int[] temp = new int[arr.length];
        // Запускаем рекурсивную сортировку
        mergeSortRecursive(arr, temp, 0, arr.length - 1);
    }

    /**
     * Точка входа для демонстрации работы метода сортировки слиянием.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        MergeSort sol = new MergeSort();

        System.out.println("--- Merge Sort Demonstration ---");

        runMergeSortTest(sol, new int[]{5, 1, 4, 2, 8, 5, 0, -3, 99}, "Смешанный массив");
        runMergeSortTest(sol, new int[]{1}, "Один элемент");
        runMergeSortTest(sol, new int[]{3, 1}, "Два элемента");
        runMergeSortTest(sol, new int[]{}, "Пустой массив");
        runMergeSortTest(sol, new int[]{9, 8, 7, 6, 5}, "Обратно отсортированный");
        runMergeSortTest(sol, new int[]{2, 3, 1, 5, 4}, "Другой случай");
        runMergeSortTest(sol, new int[]{2, 2, 1, 1, 3, 3}, "С дубликатами");
        runMergeSortTest(sol, null, "Null массив");
    }

    /**
     * Рекурсивная функция сортировки слиянием для подмассива arr[left..right].
     * Использует предоставленный временный массив {@code temp} для слияния.
     *
     * @param arr   Массив (или подмассив), который сортируется.
     * @param temp  Временный массив того же размера, что и arr, для операции слияния.
     * @param left  Левая граница (индекс) текущего подмассива (включительно).
     * @param right Правая граница (индекс) текущего подмассива (включительно).
     */
    private void mergeSortRecursive(int[] arr, int[] temp, int left, int right) {
        // Базовый случай рекурсии: если подмассив содержит 0 или 1 элемент
        if (left >= right) {
            return;
        }

        // Находим середину
        int mid = left + (right - left) / 2;

        // Рекурсивно сортируем левую и правую половины
        mergeSortRecursive(arr, temp, left, mid);
        mergeSortRecursive(arr, temp, mid + 1, right);

        // Сливаем две отсортированные половины
        merge(arr, temp, left, mid, right);
    }

    /**
     * Сливает два соседних отсортированных подмассива {@code arr[left..mid]} и {@code arr[mid+1..right]}
     * в один отсортированный подмассив {@code arr[left..right]}, используя временный массив {@code temp}.
     *
     * @param arr   Основной массив, содержащий подмассивы для слияния.
     * @param temp  Временный массив для копирования данных перед слиянием.
     * @param left  Начальный индекс первого подмассива (и всего сливаемого диапазона).
     * @param mid   Конечный индекс первого подмассива.
     * @param right Конечный индекс второго подмассива (и всего сливаемого диапазона).
     */
    private void merge(int[] arr, int[] temp, int left, int mid, int right) {
        // 1. Копируем весь диапазон [left..right] из arr в temp
        // Это проще, чем создавать два отдельных маленьких массива на каждом шаге.
        if (right + 1 - left >= 0) {
            System.arraycopy(arr, left, temp, left, right + 1 - left);
        }

        // 2. Инициализируем указатели для слияния
        int i = left;     // Указатель для левой половины в temp (temp[left..mid])
        int j = mid + 1;  // Указатель для правой половины в temp (temp[mid+1..right])
        int k = left;     // Указатель для записи результата обратно в arr (arr[left..right])

        // 3. Слияние: пока есть элементы в обеих половинах
        while (i <= mid && j <= right) {
            // Сравниваем элементы из левой и правой половин во временном массиве
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++]; // Берем из левой
            } else {
                arr[k++] = temp[j++]; // Берем из правой
            }
        }

        // 4. Копируем оставшиеся элементы из левой половины (если есть)
        // Элементы из правой половины копировать не нужно, т.к. если они остались,
        // они уже находятся на своих местах в исходном `arr` (и в `temp`),
        // и указатель `k` уже прошел их позиции.
        while (i <= mid) {
            arr[k++] = temp[i++];
        }
        // while (j <= right) { // Эта часть не нужна при копировании всего диапазона в temp
        //     arr[k++] = temp[j++];
        // }
    }

    /**
     * Вспомогательный метод для тестирования mergeSort.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Массив для сортировки (будет изменен!).
     * @param description Описание теста.
     */
    private static void runMergeSortTest(MergeSort sol, int[] arr, String description) {
        System.out.println("\n--- " + description + " ---");
        String originalString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("Original: " + originalString);
        // Создаем копию для сравнения после сортировки, если нужно
        // int[] originalCopy = (arr == null) ? null : Arrays.copyOf(arr, arr.length);
        try {
            sol.mergeSort(arr); // Сортируем на месте
            String sortedString = (arr == null ? "null" : Arrays.toString(arr));
            System.out.println("Sorted:   " + sortedString);
            // Проверка сортировки (опционально)
            // if (arr != null && !isSorted(arr)) System.err.println("   Array is NOT sorted!");
        } catch (Exception e) {
            System.err.println("Sorted:   Error - " + e.getMessage());
        }
    }

    // Дополнительный метод для проверки отсортированности (для тестов)
    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) return false;
        }
        return true;
    }
}

package com.svedentsov.aqa.tasks.sorting_searching;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Решение задачи №48: Найти K-ый по величине элемент в массиве.
 * <p>
 * Описание: (Проверяет: сортировка, частичная сортировка, PriorityQueue)
 * <p>
 * Задание: Напишите метод `int findKthLargest(int[] nums, int k)`, который находит
 * `k`-ый по величине элемент в массиве `nums`. Обратите внимание, это `k`-ый по величине
 * элемент в отсортированном порядке, а не `k`-ый уникальный элемент.
 * <p>
 * Пример: `findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2)` -> `5`.
 * `findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4)` -> `4`.
 */
public class KthLargestElement {

    /**
     * Находит k-ый по величине элемент в массиве с использованием сортировки.
     * Модифицирует исходный массив {@code nums}.
     * <p>
     * Сложность: O(n log n) по времени, O(1) или O(log n) по памяти (зависит от Arrays.sort).
     *
     * @param nums Массив целых чисел. Не должен быть null или пустым.
     * @param k    Порядковый номер искомого элемента (1-based index from largest),
     *             1 <= k <= nums.length.
     * @return k-ый по величине элемент.
     * @throws IllegalArgumentException если nums null, пуст, или k некорректен.
     */
    public int findKthLargestSorting(int[] nums, int k) {
        validateInput(nums, k); // Выносим проверку в отдельный метод

        // Сортируем массив (модифицирует исходный!)
        Arrays.sort(nums);
        // K-ый по величине элемент находится на индексе n - k
        return nums[nums.length - k];
    }

    /**
     * Находит k-ый по величине элемент в массиве с использованием минимальной кучи (Min-Heap).
     * Не модифицирует исходный массив.
     * <p>
     * Сложность: O(n log k) по времени, O(k) по памяти.
     *
     * @param nums Массив целых чисел. Не должен быть null или пустым.
     * @param k    Порядковый номер искомого элемента (1-based index from largest),
     *             1 <= k <= nums.length.
     * @return k-ый по величине элемент.
     * @throws IllegalArgumentException если nums null, пуст, или k некорректен.
     */
    public int findKthLargestHeap(int[] nums, int k) {
        validateInput(nums, k);

        // PriorityQueue в Java по умолчанию является Min-Heap.
        // Создаем кучу размером k (начальная емкость для оптимизации).
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);

        for (int num : nums) {
            if (minHeap.size() < k) {
                // Пока куча не заполнилась до k элементов, просто добавляем
                minHeap.offer(num);
            } else if (num > minHeap.peek()) { // Сравнение с минимумом в куче
                minHeap.poll(); // Удаляем текущий минимум
                minHeap.offer(num); // Добавляем новый элемент
            }
        }
        // В вершине кучи - k-ый по величине элемент.
        // peek() не может вернуть null, т.к. k >= 1 и массив не пуст
        return minHeap.peek();
    }

    /**
     * Проверяет корректность входных данных для методов поиска k-го элемента.
     * Приватный, т.к. является деталью реализации публичных методов.
     *
     * @param nums Массив чисел.
     * @param k    Порядковый номер k.
     * @throws IllegalArgumentException если входные данные некорректны.
     */
    private void validateInput(int[] nums, int k) throws IllegalArgumentException {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        if (nums.length == 0) {
            throw new IllegalArgumentException("Input array cannot be empty.");
        }
        if (k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid value for k: " + k + ". Must be between 1 and " + nums.length);
        }
    }
}

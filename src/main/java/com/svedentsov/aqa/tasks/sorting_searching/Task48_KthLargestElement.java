package com.svedentsov.aqa.tasks.sorting_searching; // или arrays_lists

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Решение задачи №48: Найти K-ый по величине элемент в массиве.
 */
public class Task48_KthLargestElement {

    /**
     * Находит k-ый по величине элемент в массиве с использованием сортировки.
     * Просто сортирует массив и возвращает элемент по индексу {@code nums.length - k}.
     * Сложность O(n log n) по времени из-за сортировки.
     *
     * @param nums Массив целых чисел. Не должен быть null или пустым.
     * @param k    Порядковый номер искомого элемента (1-based index from largest),
     *             1 <= k <= nums.length.
     * @return k-ый по величине элемент.
     * @throws IllegalArgumentException если nums null, пуст, или k некорректен.
     */
    public int findKthLargestSorting(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input array or k value (k=" + k + ", length=" + (nums == null ? 0 : nums.length) + ")");
        }
        // Сортируем массив по возрастанию
        Arrays.sort(nums);
        // K-ый по величине элемент находится на индексе n - k
        return nums[nums.length - k];
    }

    /**
     * Находит k-ый по величине элемент в массиве с использованием минимальной кучи (Min-Heap).
     * Поддерживает кучу размером k, содержащую k наибольших элементов, встреченных на данный момент.
     * В конце в вершине кучи будет k-ый по величине элемент всего массива.
     * Сложность O(n log k) по времени, O(k) по памяти.
     *
     * @param nums Массив целых чисел. Не должен быть null или пустым.
     * @param k    Порядковый номер искомого элемента (1-based index from largest),
     *             1 <= k <= nums.length.
     * @return k-ый по величине элемент.
     * @throws IllegalArgumentException если nums null, пуст, или k некорректен.
     */
    public int findKthLargestHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input array or k value (k=" + k + ", length=" + (nums == null ? 0 : nums.length) + ")");
        }
        // Создаем минимальную кучу (PriorityQueue по умолчанию min-heap)
        // для хранения k наибольших элементов.
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k); // Начальная емкость k

        for (int num : nums) {
            if (minHeap.size() < k) {
                // Если куча еще не заполнена до размера k, просто добавляем элемент
                minHeap.offer(num);
            } else if (num > minHeap.peek()) {
                // Если куча заполнена и текущий элемент 'num' больше, чем
                // самый маленький элемент в куче (minHeap.peek()), то этот маленький
                // элемент вытесняется, а 'num' добавляется.
                minHeap.poll();      // Удаляем самый маленький (вершину min-heap)
                minHeap.offer(num); // Добавляем текущий (больший) элемент
            }
            // Если num <= minHeap.peek(), то 'num' меньше или равен k-ому по величине
            // элементу в куче, и его можно игнорировать.
        }

        // После прохода по всем элементам, вершина min-heap (minHeap.peek())
        // будет содержать k-ый по величине элемент всего массива.
        return minHeap.peek();
    }

    /**
     * Точка входа для демонстрации работы методов поиска k-го по величине элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task48_KthLargestElement sol = new Task48_KthLargestElement();

        int[][] testArrays = {
                {3, 2, 1, 5, 6, 4},          // k=2 -> 5
                {3, 2, 3, 1, 2, 4, 5, 5, 6} // k=4 -> 4
        };
        int[] ks = {2, 4};
        int[] expected = {5, 4};

        for (int i = 0; i < testArrays.length; i++) {
            int[] nums = testArrays[i];
            int k = ks[i];
            System.out.println("\nArray: " + Arrays.toString(nums) + ", k=" + k);
            try {
                int resultSort = sol.findKthLargestSorting(nums, k);
                System.out.println("  Sorting Method: " + resultSort + " (Expected: " + expected[i] + ")");
                if (resultSort != expected[i]) System.err.println("   Mismatch!");

                int resultHeap = sol.findKthLargestHeap(nums, k);
                System.out.println("  Heap Method:    " + resultHeap + " (Expected: " + expected[i] + ")");
                if (resultHeap != expected[i]) System.err.println("   Mismatch!");

            } catch (IllegalArgumentException e) {
                System.err.println("   Error: " + e.getMessage());
            }
        }

        // Тесты с граничными k
        int[] numsSingle = {5};
        System.out.println("\nArray: " + Arrays.toString(numsSingle) + ", k=1");
        System.out.println("  Heap Method: " + sol.findKthLargestHeap(numsSingle, 1)); // 5

        int[] numsAll = {1, 2, 3, 4};
        System.out.println("\nArray: " + Arrays.toString(numsAll) + ", k=4");
        System.out.println("  Heap Method: " + sol.findKthLargestHeap(numsAll, 4)); // 1 (4-й по величине)

        // Тесты на ошибки
        try {
            sol.findKthLargestHeap(new int[]{1, 2}, 3); // k > length
        } catch (IllegalArgumentException e) {
            System.out.println("\nCaught expected error (k > length): " + e.getMessage());
        }
        try {
            sol.findKthLargestHeap(new int[]{1, 2}, 0); // k <= 0
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error (k <= 0): " + e.getMessage());
        }
        try {
            sol.findKthLargestHeap(null, 1); // null array
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected error (null array): " + e.getMessage());
        }
    }
}

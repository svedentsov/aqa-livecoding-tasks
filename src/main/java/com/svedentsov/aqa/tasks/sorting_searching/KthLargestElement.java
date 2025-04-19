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
     * Просто сортирует массив по возрастанию и возвращает элемент по индексу
     * {@code nums.length - k}.
     * <p>
     * Сложность: O(n log n) по времени из-за сортировки, O(1) или O(log n)
     * по памяти (в зависимости от реализации `Arrays.sort`).
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
     * Поддерживает кучу размером k, содержащую k наибольших элементов, встреченных на данный момент.
     * В конце в вершине кучи (самый маленький элемент в ней) будет k-ый по величине элемент всего массива.
     * <p>
     * Сложность: O(n log k) по времени (n операций добавления/удаления в кучу размером k),
     * O(k) по памяти (для хранения кучи). Эффективнее сортировки, если k << n.
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
            } else {
                // Куча заполнена. Сравниваем текущий элемент с минимальным в куче (вершиной).
                // Если текущий элемент больше минимума в куче, он потенциально
                // входит в k наибольших элементов.
                if (num > minHeap.peek()) {
                    minHeap.poll(); // Удаляем текущий минимум (он больше не входит в k наибольших)
                    minHeap.offer(num); // Добавляем новый, больший элемент
                }
                // Если num <= minHeap.peek(), текущий элемент не входит в k наибольших, игнорируем.
            }
        }

        // После обработки всех элементов, в вершине кучи находится k-ый по величине элемент.
        return minHeap.peek();
    }

    /**
     * Точка входа для демонстрации работы методов поиска k-го по величине элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        KthLargestElement sol = new KthLargestElement();

        System.out.println("--- Finding Kth Largest Element ---");

        runKthLargestTest(sol, new int[]{3, 2, 1, 5, 6, 4}, 2, "Example 1"); // 5
        runKthLargestTest(sol, new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, "Example 2"); // 4
        runKthLargestTest(sol, new int[]{1}, 1, "Single element array"); // 1
        runKthLargestTest(sol, new int[]{2, 1}, 1, "k=1 (Largest)"); // 2
        runKthLargestTest(sol, new int[]{2, 1}, 2, "k=n (Smallest)"); // 1
        runKthLargestTest(sol, new int[]{7, 6, 5, 4, 3, 2, 1}, 3, "Sorted descending, k=3"); // 5
        runKthLargestTest(sol, new int[]{5, 2, 4, 1, 3, 6, 0}, 7, "k=n (Smallest)"); // 0

        // Тесты на ошибки
        runKthLargestTest(sol, new int[]{1, 2}, 3, "Error test: k > length"); // Exception
        runKthLargestTest(sol, new int[]{1, 2}, 0, "Error test: k <= 0"); // Exception
        runKthLargestTest(sol, null, 1, "Error test: null array"); // Exception
        runKthLargestTest(sol, new int[]{}, 1, "Error test: empty array"); // Exception
    }

    /**
     * Проверяет корректность входных данных для методов поиска k-го элемента.
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

    /**
     * Вспомогательный метод для тестирования методов findKthLargest.
     *
     * @param sol         Экземпляр решателя.
     * @param nums        Массив для теста.
     * @param k           Порядковый номер k.
     * @param description Описание теста.
     */
    private static void runKthLargestTest(KthLargestElement sol, int[] nums, int k, String description) {
        System.out.println("\n--- " + description + " ---");
        String arrString = (nums == null ? "null" : Arrays.toString(nums));
        System.out.println("Input array: " + arrString + ", k=" + k);
        try {
            // Тестируем метод с сортировкой (создаем копию, чтобы не портить массив для heap метода)
            int[] numsCopyForSort = (nums == null) ? null : Arrays.copyOf(nums, nums.length);
            int resultSort = sol.findKthLargestSorting(numsCopyForSort, k);
            System.out.println("  Result (Sorting): " + resultSort);
        } catch (IllegalArgumentException e) {
            System.out.println("  Result (Sorting): Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("  Result (Sorting): Unexpected Error - " + e.getMessage());
        }
        try {
            // Тестируем метод с кучей
            int resultHeap = sol.findKthLargestHeap(nums, k);
            System.out.println("  Result (Heap)   : " + resultHeap);
        } catch (IllegalArgumentException e) {
            System.out.println("  Result (Heap)   : Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("  Result (Heap)   : Unexpected Error - " + e.getMessage());
        }
    }
}

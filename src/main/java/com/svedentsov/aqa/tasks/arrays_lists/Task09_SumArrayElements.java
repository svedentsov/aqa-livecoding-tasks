package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №9: Сумма элементов массива/списка.
 */
public class Task09_SumArrayElements {

    /**
     * Вычисляет сумму элементов массива целых чисел.
     * Использует цикл for-each.
     *
     * @param numbers Массив целых чисел. Может быть null.
     * @return Сумма элементов массива. Возвращает 0, если массив null или пуст.
     * Использует тип long для предотвращения возможного переполнения int.
     */
    public long sumElements(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        long sum = 0; // Используем long для суммы
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    /**
     * Вычисляет сумму элементов списка целых чисел с использованием Stream API.
     *
     * @param numbers Список Integer. Может быть null.
     * @return Сумма элементов списка. Возвращает 0, если список null или пуст.
     * Обрабатывает null элементы в списке, игнорируя их.
     */
    public long sumElementsListStream(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0;
        }
        return numbers.stream()
                .filter(Objects::nonNull) // Игнорируем null элементы в списке
                .mapToLong(Integer::longValue) // Преобразуем в LongStream
                .sum(); // Суммируем
    }

    /**
     * Точка входа для демонстрации работы методов суммирования.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task09_SumArrayElements sol = new Task09_SumArrayElements();

        int[] arr1 = {1, 2, 3, 4, 5};
        System.out.println("Sum of " + Arrays.toString(arr1) + ": " + sol.sumElements(arr1)); // 15

        int[] arr2 = {};
        System.out.println("Sum of " + Arrays.toString(arr2) + ": " + sol.sumElements(arr2)); // 0

        int[] arr3 = {-1, 0, 1};
        System.out.println("Sum of " + Arrays.toString(arr3) + ": " + sol.sumElements(arr3)); // 0

        int[] arr4 = {Integer.MAX_VALUE, 1}; // Пример для демонстрации long
        System.out.println("Sum of {Integer.MAX_VALUE, 1}: " + sol.sumElements(arr4)); // 2147483648

        System.out.println("Sum of null array: " + sol.sumElements(null)); // 0


        List<Integer> list1 = Arrays.asList(10, 20, null, 30, -5);
        System.out.println("\nSum of list " + list1 + ": " + sol.sumElementsListStream(list1)); // 55

        List<Integer> list2 = Arrays.asList();
        System.out.println("Sum of list " + list2 + ": " + sol.sumElementsListStream(list2)); // 0

        System.out.println("Sum of null list: " + sol.sumElementsListStream(null)); // 0
    }
}

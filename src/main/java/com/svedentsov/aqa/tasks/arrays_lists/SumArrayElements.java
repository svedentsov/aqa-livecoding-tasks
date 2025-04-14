package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Решение задачи №9: Сумма элементов массива/списка.
 * <p>
 * Описание: Написать функцию для вычисления суммы всех числовых элементов
 * в массиве/списке. (Проверяет: циклы, арифметика)
 * <p>
 * Задание: Напишите метод `long sumElements(int[] numbers)`, который вычисляет
 * и возвращает сумму всех элементов в массиве `numbers`. Используйте `long` для
 * результата, чтобы избежать переполнения.
 * <p>
 * Пример: `sumElements(new int[]{1, 2, 3, 4, 5})` -> `15`. `sumElements(new int[]{})` -> `0`.
 */
public class SumArrayElements {

    /**
     * Вычисляет сумму элементов массива целых чисел итеративно.
     * Использует цикл for-each.
     *
     * @param numbers Массив целых чисел. Может быть null.
     * @return Сумма элементов массива (тип long). Возвращает 0, если массив null или пуст.
     */
    public long sumElements(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0L; // Возвращаем 0 типа long
        }
        long sum = 0L; // Используем long для суммы, инициализируем нулем long
        for (int number : numbers) {
            sum += number; // Сложение int и long дает long
        }
        return sum;
    }

    /**
     * Вычисляет сумму элементов списка целых чисел итеративно.
     * Обрабатывает null элементы в списке, игнорируя их.
     *
     * @param numbers Список Integer. Может быть null.
     * @return Сумма элементов списка (тип long). Возвращает 0, если список null или пуст.
     */
    public long sumElementsListIterative(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0L;
        }
        long sum = 0L;
        for (Integer number : numbers) {
            if (number != null) { // Проверяем на null перед суммированием
                sum += number; // Автоматическое unboxing Integer -> int
            }
        }
        return sum;
    }

    /**
     * Вычисляет сумму элементов списка целых чисел с использованием Stream API.
     * Игнорирует null элементы в списке.
     *
     * @param numbers Список Integer. Может быть null.
     * @return Сумма элементов списка (тип long). Возвращает 0, если список null или пуст.
     */
    public long sumElementsListStream(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0L;
        }
        return numbers.stream()
                .filter(Objects::nonNull) // Игнорируем null элементы в потоке
                .mapToLong(Integer::longValue) // Преобразуем Integer в long (безопаснее чем mapToInt)
                .sum(); // Встроенный метод sum() для LongStream
    }

    /**
     * Точка входа для демонстрации работы методов суммирования.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        SumArrayElements sol = new SumArrayElements();

        // Тесты для массивов (sumElements)
        runSumArrayTest(sol, new int[]{1, 2, 3, 4, 5}, "Сумма массива (положительные)"); // 15
        runSumArrayTest(sol, new int[]{}, "Сумма пустого массива"); // 0
        runSumArrayTest(sol, new int[]{-1, 0, 1}, "Сумма массива (смешанные знаки)"); // 0
        runSumArrayTest(sol, new int[]{-1, -2, -3}, "Сумма массива (отрицательные)"); // -6
        runSumArrayTest(sol, new int[]{Integer.MAX_VALUE, 1}, "Сумма массива (переполнение int)"); // 2147483648L
        runSumArrayTest(sol, new int[]{Integer.MIN_VALUE, -1}, "Сумма массива (отрицательное переполнение)"); // -2147483649L
        runSumArrayTest(sol, new int[]{10}, "Сумма массива (один элемент)"); // 10
        runSumArrayTest(sol, null, "Сумма null массива"); // 0

        // Тесты для списков (sumElementsListIterative / sumElementsListStream)
        List<Integer> listWithNulls = Arrays.asList(10, 20, null, 30, -5, null);
        runSumListTest(sol, listWithNulls, "Сумма списка с null"); // 55

        runSumListTest(sol, Arrays.asList(), "Сумма пустого списка"); // 0
        runSumListTest(sol, null, "Сумма null списка"); // 0
        runSumListTest(sol, Arrays.asList(Integer.MAX_VALUE, 10), "Сумма списка (большие числа)"); // 2147483657L
        runSumListTest(sol, Arrays.asList(5, 5, 5), "Сумма списка (одинаковые числа)"); // 15
        runSumListTest(sol, Arrays.asList(null, null), "Сумма списка только из null"); // 0
    }

    /**
     * Вспомогательный метод для тестирования суммирования массива.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Тестовый массив.
     * @param description Описание теста.
     */
    private static void runSumArrayTest(SumArrayElements sol, int[] arr, String description) {
        System.out.println("\n--- " + description + " ---");
        System.out.println("Input array: " + (arr == null ? "null" : Arrays.toString(arr)));
        try {
            System.out.println("Sum (array): " + sol.sumElements(arr));
        } catch (Exception e) {
            System.out.println("Sum (array): Error - " + e.getMessage());
        }
    }

    /**
     * Вспомогательный метод для тестирования суммирования списка.
     *
     * @param sol         Экземпляр решателя.
     * @param list        Тестовый список.
     * @param description Описание теста.
     */
    private static void runSumListTest(SumArrayElements sol, List<Integer> list, String description) {
        System.out.println("\n--- " + description + " ---");
        System.out.println("Input list: " + (list == null ? "null" : list.toString()));
        try {
            System.out.println("Sum (list, iterative): " + sol.sumElementsListIterative(list == null ? null : new ArrayList<>(list)));
        } catch (Exception e) {
            System.out.println("Sum (list, iterative): Error - " + e.getMessage());
        }
        try {
            System.out.println("Sum (list, stream):    " + sol.sumElementsListStream(list == null ? null : new ArrayList<>(list)));
        } catch (Exception e) {
            System.out.println("Sum (list, stream):    Error - " + e.getMessage());
        }
    }
}

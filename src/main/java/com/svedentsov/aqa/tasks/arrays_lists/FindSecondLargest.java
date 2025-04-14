package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №19: Найти второй по величине элемент в массиве.
 * <p>
 * Описание: Написать функцию для поиска второго максимального элемента.
 * (Проверяет: циклы, сравнения, обработка крайних случаев)
 * <p>
 * Задание: Напишите метод `int findSecondLargest(int[] numbers)`, который
 * находит и возвращает второе по величине число в массиве `numbers`.
 * Если такого элемента нет (например, все элементы одинаковы или массив
 * слишком мал), верните `Integer.MIN_VALUE` или выбросите исключение.
 * (Возвращаем `Integer.MIN_VALUE` согласно примеру).
 * <p>
 * Пример: `findSecondLargest(new int[]{1, 5, 2, 9, 3, 9})` -> `5`.
 * `findSecondLargest(new int[]{3, 3, 3})` -> `Integer.MIN_VALUE`.
 */
public class FindSecondLargest {

    /**
     * Находит второе по величине число в массиве целых чисел за один проход.
     * Отслеживает два наибольших различных элемента: `largest` и `secondLargest`.
     * Сложность O(n) по времени, O(1) по памяти.
     * <p>
     * Алгоритм:
     * 1. Инициализировать `largest` и `secondLargest` значением `Integer.MIN_VALUE`.
     * 2. Пройти по массиву один раз. Для каждого числа `number`:
     * a. Если `number > largest`:
     * - Текущий `largest` становится `secondLargest`.
     * - Новый `number` становится `largest`.
     * b. Иначе, если `number > secondLargest` И `number < largest`:
     * - `number` становится `secondLargest`.
     * 3. После прохода проверить, изменился ли `secondLargest` со своего
     * начального значения `Integer.MIN_VALUE`. Если не изменился, значит,
     * второго по величине элемента не было (все элементы одинаковы или
     * массив содержал только один уникальный элемент).
     *
     * @param numbers Массив целых чисел. Не должен быть null.
     * @return Второе по величине число в массиве. Возвращает {@code Integer.MIN_VALUE},
     * если второго по величине элемента нет (например, массив содержит менее 2
     * уникальных элементов).
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findSecondLargest(int[] numbers) {
        // Проверка на null или пустой массив
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        // Если в массиве только один элемент, второго по величине нет
        if (numbers.length == 1) {
            return Integer.MIN_VALUE;
        }

        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        // Один проход по массиву
        for (int number : numbers) {
            if (number > largest) {
                // Нашли новый максимум. Предыдущий максимум становится вторым.
                secondLargest = largest;
                largest = number;
            } else if (number > secondLargest && number < largest) {
                // Нашли число больше текущего второго, но меньше максимума.
                secondLargest = number;
            }
            // Если number == largest, ничего не делаем (не обновляем secondLargest)
            // Если number <= secondLargest, тоже ничего не делаем.
        }

        // Если secondLargest остался MIN_VALUE, это может означать:
        // 1. Все элементы массива были одинаковы (и равны largest).
        // 2. Все элементы были либо largest, либо <= MIN_VALUE.
        // 3. Массив состоял только из Integer.MIN_VALUE.
        // Во всех этих случаях второго по величине элемента нет (или он MIN_VALUE, если
        // largest > MIN_VALUE и MIN_VALUE был в массиве).
        // Наша логика корректно обрабатывает это: если не нашлось числа > MIN_VALUE и < largest,
        // то secondLargest останется MIN_VALUE, что и требуется вернуть.

        // Единственный пограничный случай: массив состоит только из Integer.MIN_VALUE.
        // В этом случае largest = MIN_VALUE, secondLargest = MIN_VALUE. Возврат MIN_VALUE корректен.
        // Случай: {MAX_VALUE, MIN_VALUE}. largest=MAX_VALUE, secondLargest=MIN_VALUE. Возврат MIN_VALUE корректен.
        // Случай: {MAX_VALUE, MAX_VALUE}. largest=MAX_VALUE, secondLargest=MIN_VALUE. Возврат MIN_VALUE корректен.

        return secondLargest;
    }

    /**
     * Точка входа для демонстрации работы метода поиска второго по величине элемента.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindSecondLargest sol = new FindSecondLargest();

        runSecondLargestTest(sol, new int[]{1, 5, 2, 9, 3, 9}, "Стандартный случай"); // 5
        runSecondLargestTest(sol, new int[]{10, 2, 8, 10, 5}, "Дубликат максимума"); // 8
        runSecondLargestTest(sol, new int[]{3, 3, 3}, "Все одинаковые"); // MIN_VALUE
        runSecondLargestTest(sol, new int[]{5, 1}, "Два разных элемента"); // 1
        runSecondLargestTest(sol, new int[]{1, 5}, "Два разных элемента (другой порядок)"); // 1
        runSecondLargestTest(sol, new int[]{5, 5}, "Два одинаковых элемента"); // MIN_VALUE
        runSecondLargestTest(sol, new int[]{Integer.MAX_VALUE, 1, Integer.MAX_VALUE}, "Граничные значения 1"); // 1
        runSecondLargestTest(sol, new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE}, "Все MIN_VALUE"); // MIN_VALUE
        runSecondLargestTest(sol, new int[]{1, Integer.MIN_VALUE}, "Один элемент и MIN_VALUE"); // MIN_VALUE
        runSecondLargestTest(sol, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE}, "MAX и MIN"); // MIN_VALUE
        runSecondLargestTest(sol, new int[]{1, 2, Integer.MIN_VALUE}, "Три элемента с MIN_VALUE"); // 1
        runSecondLargestTest(sol, new int[]{0, -1, Integer.MIN_VALUE}, "С нулем и MIN_VALUE"); // -1
        runSecondLargestTest(sol, new int[]{-1, -2, -3}, "Все отрицательные"); // -2
        runSecondLargestTest(sol, new int[]{-3, -2, -1}, "Все отрицательные (отсорт.)"); // -2
        runSecondLargestTest(sol, new int[]{5}, "Один элемент"); // MIN_VALUE
        runSecondLargestTest(sol, new int[]{}, "Пустой массив"); // Error
        runSecondLargestTest(sol, null, "Null массив"); // Error
    }

    /**
     * Вспомогательный метод для тестирования поиска второго по величине.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Тестовый массив.
     * @param description Описание теста.
     */
    private static void runSecondLargestTest(FindSecondLargest sol, int[] arr, String description) {
        System.out.println("\n--- " + description + " ---");
        String arrString = (arr == null ? "null" : Arrays.toString(arr));
        System.out.println("Input array: " + arrString);
        try {
            int result = sol.findSecondLargest(arr);
            System.out.println("Second largest: " + result +
                    (result == Integer.MIN_VALUE ? " (Integer.MIN_VALUE)" : ""));
        } catch (IllegalArgumentException e) {
            System.out.println("Second largest: Error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Second largest: Unexpected Error - " + e.getMessage());
        }
    }
}

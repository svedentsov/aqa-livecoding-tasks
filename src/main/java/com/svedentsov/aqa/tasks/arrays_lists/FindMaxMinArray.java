package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №6: Найти максимальный/минимальный элемент в массиве.
 * <p>
 * Описание: Написать функцию для поиска наибольшего или наименьшего числа
 * в массиве/списке без использования встроенных функций max/min коллекций (иногда просят).
 * (Проверяет: циклы, сравнения)
 * <p>
 * Задание: Напишите метод `int findMax(int[] numbers)`, который находит и возвращает
 * максимальное значение в массиве `numbers` без использования `Collections.max()`
 * или `Stream.max()`. Обработайте случай пустого массива (например, выбросив
 * исключение или вернув `Integer.MIN_VALUE`). Аналогично для `findMin`.
 * <p>
 * Пример: `findMax(new int[]{1, 5, 2, 9, 3})` -> `9`.
 * `findMax(new int[]{-1, -5, -2})` -> `-1`.
 */
public class FindMaxMinArray {

    /**
     * Находит максимальный элемент в массиве целых чисел итеративным способом.
     * Проходит по массиву, сравнивая текущий элемент с текущим максимумом.
     * Сложность O(n) по времени, O(1) по памяти.
     *
     * @param numbers Массив целых чисел.
     * @return Максимальное значение в массиве.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findMax(int[] numbers) {
        // Проверка на недопустимые входные данные
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        // Инициализируем максимум первым элементом массива
        int max = numbers[0];
        // Итерируем со второго элемента (индекс 1), т.к. первый уже взят как max
        for (int i = 1; i < numbers.length; i++) {
            // Если текущий элемент больше текущего максимума, обновляем максимум
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }
        return max; // Возвращаем найденный максимум
    }

    /**
     * Находит минимальный элемент в массиве целых чисел итеративным способом.
     * Проходит по массиву, сравнивая текущий элемент с текущим минимумом.
     * Сложность O(n) по времени, O(1) по памяти.
     *
     * @param numbers Массив целых чисел.
     * @return Минимальное значение в массиве.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findMin(int[] numbers) {
        // Проверка на недопустимые входные данные
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        // Инициализируем минимум первым элементом массива
        int min = numbers[0];
        // Итерируем со второго элемента (индекс 1)
        for (int i = 1; i < numbers.length; i++) {
            // Если текущий элемент меньше текущего минимума, обновляем минимум
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }
        return min; // Возвращаем найденный минимум
    }

    /**
     * Точка входа для демонстрации работы методов поиска min/max.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        FindMaxMinArray sol = new FindMaxMinArray();

        runMinMaxTest(sol, new int[]{1, 5, 2, 9, 3}, "Стандартный массив (положительные)");
        // Ожидаемый результат: Max=9, Min=1

        runMinMaxTest(sol, new int[]{-1, -5, -2, -9, -3}, "Стандартный массив (отрицательные)");
        // Ожидаемый результат: Max=-1, Min=-9

        runMinMaxTest(sol, new int[]{5}, "Массив из одного элемента");
        // Ожидаемый результат: Max=5, Min=5

        runMinMaxTest(sol, new int[]{0, -10, 5, 0, -2, 3}, "Массив с нулями и смешанными знаками");
        // Ожидаемый результат: Max=5, Min=-10

        runMinMaxTest(sol, new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE}, "Массив с граничными значениями int");
        // Ожидаемый результат: Max=2147483647, Min=-2147483648

        runMinMaxTest(sol, new int[]{}, "Пустой массив");
        // Ожидаемый результат: Error - Input array cannot be null or empty.

        runMinMaxTest(sol, null, "Null массив");
        // Ожидаемый результат: Error - Input array cannot be null or empty.

        runMinMaxTest(sol, new int[]{5, 5, 5, 5}, "Массив с одинаковыми элементами");
        // Ожидаемый результат: Max=5, Min=5
    }

    /**
     * Вспомогательный метод для тестирования findMax и findMin.
     *
     * @param sol         Экземпляр решателя.
     * @param arr         Тестовый массив.
     * @param description Описание теста.
     */
    private static void runMinMaxTest(FindMaxMinArray sol, int[] arr, String description) {
        System.out.println("\n--- " + description + " ---");
        System.out.println("Input array: " + (arr == null ? "null" : Arrays.toString(arr)));
        try {
            System.out.println("findMax: " + sol.findMax(arr));
        } catch (IllegalArgumentException e) {
            System.out.println("findMax: Error - " + e.getMessage());
        }
        try {
            System.out.println("findMin: " + sol.findMin(arr));
        } catch (IllegalArgumentException e) {
            System.out.println("findMin: Error - " + e.getMessage());
        }
    }
}

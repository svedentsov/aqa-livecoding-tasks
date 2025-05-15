package com.svedentsov.aqa.tasks.sorting_searching;

/**
 * Решение задачи №38: Бинарный поиск в отсортированном массиве.
 * Описание: Реализовать алгоритм бинарного поиска.
 * (Проверяет: алгоритмы, работа с индексами, рекурсия/циклы)
 * Задание: Напишите метод `int binarySearch(int[] sortedArray, int key)`, который
 * ищет индекс элемента `key` в отсортированном по возрастанию массиве `sortedArray`
 * с помощью алгоритма бинарного поиска. Если элемент не найден, верните -1.
 * Пример: `binarySearch(new int[]{1, 3, 5, 7, 9}, 5)` -> `2`.
 * `binarySearch(new int[]{1, 3, 5, 7, 9}, 6)` -> `-1`.
 */
public class BinarySearch {

    /**
     * Выполняет бинарный поиск ключа {@code key} в отсортированном по возрастанию
     * массиве целых чисел {@code sortedArray}.
     * Итеративная реализация.
     * Сложность: O(log n) по времени, O(1) по памяти.
     *
     * @param sortedArray Отсортированный по возрастанию массив, в котором выполняется поиск.
     *                    Может быть null. Массив должен быть действительно отсортирован
     *                    для корректной работы алгоритма.
     * @param key         Значение, которое нужно найти.
     * @return Индекс элемента {@code key} в массиве, если он найден;
     * иначе -1 (также возвращает -1, если массив null или пуст).
     * Если в массиве есть дубликаты ключа, метод может вернуть индекс
     * любого из них.
     */
    public int binarySearch(int[] sortedArray, int key) {
        // Проверка на null или пустой массив
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }

        int left = 0;
        int right = sortedArray.length - 1;

        while (left <= right) {
            // Вычисление среднего индекса безопасно от переполнения
            int mid = left + (right - left) / 2;
            int midValue = sortedArray[mid];

            if (midValue == key) {
                return mid; // Элемент найден
            } else if (midValue < key) {
                left = mid + 1; // Искать справа
            } else { // midValue > key
                right = mid - 1; // Искать слева
            }
        }

        // Элемент не найден
        return -1;
    }
}

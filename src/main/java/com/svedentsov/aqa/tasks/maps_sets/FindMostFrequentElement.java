package com.svedentsov.aqa.tasks.maps_sets;

import java.util.HashMap;
import java.util.Map;

/**
 * Решение задачи №32: Найти самый часто встречающийся элемент в массиве.
 * <p>
 * Описание: Написать функцию для поиска элемента с максимальным числом вхождений.
 * (Проверяет: Map, циклы, сравнения)
 * <p>
 * Задание: Напишите метод `int findMostFrequentElement(int[] numbers)`, который
 * находит и возвращает элемент, который встречается в массиве `numbers` чаще всего.
 * Если таких элементов несколько, верните любой из них. Обработайте случай
 * пустого или null массива (выбросив исключение).
 * <p>
 * Пример: `findMostFrequentElement(new int[]{1, 3, 2, 1, 4, 1, 3})` -> `1`.
 * `findMostFrequentElement(new int[]{1, 2, 3})` -> `1` (или `2`, или `3`).
 */
public class FindMostFrequentElement {

    /**
     * Находит элемент, который встречается в массиве целых чисел чаще всего.
     * Если несколько элементов имеют одинаковую максимальную частоту,
     * возвращается один из них (зависит от порядка итерации по HashMap).
     * Использует HashMap для подсчета частот элементов.
     * <p>
     * Сложность: O(n) по времени, O(k) по памяти (k - уник. элементы).
     *
     * @param numbers Массив целых чисел.
     * @return Элемент с максимальной частотой встречаемости.
     * @throws IllegalArgumentException если массив null или пуст.
     */
    public int findMostFrequentElement(int[] numbers) {
        // Проверка на недопустимый ввод
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }

        // Шаг 1: Подсчет частот элементов
        Map<Integer, Integer> counts = new HashMap<>();
        for (int number : numbers) {
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        // Шаг 2: Поиск элемента с максимальной частотой
        Integer mostFrequentElement = null;
        int maxCount = -1;

        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentElement = entry.getKey();
            }
        }

        // Гарантированно не null, так как массив не пуст
        return mostFrequentElement;
    }
}

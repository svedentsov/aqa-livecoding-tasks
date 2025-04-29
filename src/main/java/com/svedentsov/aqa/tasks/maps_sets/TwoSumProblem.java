package com.svedentsov.aqa.tasks.maps_sets;

import java.util.HashMap;
import java.util.Map;

/**
 * Решение задачи №25: Two Sum Problem (Найти два числа с заданной суммой).
 * <p>
 * Описание: Дан массив чисел и целевое значение. Найти два числа,
 * которые в сумме дают это значение. (Проверяет: циклы, Map/Set, логика)
 * <p>
 * Задание: Напишите метод `int[] findTwoSumIndices(int[] nums, int target)`,
 * который принимает массив целых чисел `nums` и целевое значение `target`.
 * Метод должен вернуть массив из двух индексов элементов, сумма которых
 * равна `target`. Если такой пары нет, верните пустой массив.
 * Предполагается, что решение единственное, и нельзя использовать один и тот же элемент дважды.
 * (Возвращаем пустой массив).
 * <p>
 * Пример: `findTwoSumIndices(new int[]{2, 7, 11, 15}, 9)` -> `[0, 1]`.
 * `findTwoSumIndices(new int[]{3, 2, 4}, 6)` -> `[1, 2]`.
 */
public class TwoSumProblem {

    /**
     * Находит индексы двух чисел в массиве, сумма которых равна заданному целевому значению.
     * Использует HashMap для хранения уже просмотренных чисел и их индексов (`value -> index`),
     * что позволяет найти решение за один проход по массиву.
     * Сложность: O(n) по времени (один проход).
     * Сложность: O(n) по памяти в худшем случае (для хранения карты).
     *
     * @param nums   Массив целых чисел. Не должен быть null.
     * @param target Целевая сумма.
     * @return Массив из двух индексов {@code [index1, index2]}, сумма элементов
     * которых равна {@code target}. Если решение не найдено, возвращает пустой массив `new int[0]`.
     * @throws IllegalArgumentException если входной массив null.
     */
    public int[] findTwoSumIndices(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null.");
        }
        // Карта для хранения: {значение_числа: его_индекс}
        Map<Integer, Integer> numIndexMap = new HashMap<>((int) (nums.length / 0.75f) + 1);

        // Проходим по массиву
        for (int i = 0; i < nums.length; i++) {
            int currentNum = nums[i];
            int complement = target - currentNum;

            // Проверяем, есть ли нужное дополнение в карте
            if (numIndexMap.containsKey(complement)) {
                // Нашли! Возвращаем индекс найденного дополнения и текущий индекс.
                return new int[]{numIndexMap.get(complement), i};
            }
            // Если дополнение не найдено, кладем ТЕКУЩЕЕ число и его индекс в карту
            numIndexMap.put(currentNum, i);
        }

        // Если прошли весь массив и не нашли пару
        return new int[0];
    }
}

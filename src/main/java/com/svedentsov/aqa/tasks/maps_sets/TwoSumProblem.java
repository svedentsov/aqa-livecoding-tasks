package com.svedentsov.aqa.tasks.maps_sets;

import java.util.Arrays;
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
 * равна `target`. Если такой пары нет, верните пустой массив или `null`.
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
     * <p>
     * Алгоритм:
     * 1. Создать пустую HashMap `numIndexMap` для хранения пар {число: индекс}.
     * 2. Итерировать по массиву `nums` с индексом `i` от 0 до `nums.length - 1`.
     * 3. Для каждого числа `currentNum = nums[i]`:
     * a. Вычислить необходимое "дополнение": `complement = target - currentNum`.
     * b. Проверить, содержится ли `complement` как ключ в `numIndexMap`.
     * i. Если да, значит, пара найдена. Вернуть массив из двух индексов:
     * `[индекс_дополнения_из_карты, текущий_индекс_i]`.
     * c. Если `complement` не найден в карте, добавить текущую пару
     * `{currentNum: i}` в `numIndexMap`.
     * 4. Если цикл завершен без нахождения пары, вернуть пустой массив.
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
        // Задаем начальную емкость, чтобы уменьшить рехеширования
        Map<Integer, Integer> numIndexMap = new HashMap<>((int) (nums.length / 0.75f) + 1);

        // Проходим по массиву
        for (int i = 0; i < nums.length; i++) {
            int currentNum = nums[i];
            // Вычисляем, какое число нужно найти (дополнение до target)
            int complement = target - currentNum;

            // Проверяем, видели ли мы уже число, которое дополнит текущее до target
            if (numIndexMap.containsKey(complement)) {
                // Нашли! Возвращаем индекс найденного дополнения и текущий индекс.
                return new int[]{numIndexMap.get(complement), i};
            }
            // Если дополнение не найдено, кладем ТЕКУЩЕЕ число и его индекс в карту
            // для будущих проверок.
            numIndexMap.put(currentNum, i);
        }

        // Если прошли весь массив и не нашли пару
        return new int[0];
    }

    /**
     * Точка входа для демонстрации работы метода поиска двух чисел с заданной суммой.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        TwoSumProblem sol = new TwoSumProblem();

        runTwoSumTest(sol, new int[]{2, 7, 11, 15}, 9, "Пример 1"); // [0, 1]
        runTwoSumTest(sol, new int[]{3, 2, 4}, 6, "Пример 2"); // [1, 2]
        runTwoSumTest(sol, new int[]{3, 3}, 6, "Пример 3 (одинаковые числа)"); // [0, 1]
        runTwoSumTest(sol, new int[]{0, 4, 3, 0}, 0, "С нулями"); // [0, 3]
        runTwoSumTest(sol, new int[]{-1, -3, 5, 9}, 4, "С отрицательными"); // [0, 2]
        runTwoSumTest(sol, new int[]{-1, -3, -5, -9}, -8, "Сумма двух отрицательных"); // [1, 2]
        runTwoSumTest(sol, new int[]{1, 2, 3, 4, 5}, 10, "Нет решения"); // []
        runTwoSumTest(sol, new int[]{}, 5, "Пустой массив"); // []
        runTwoSumTest(sol, new int[]{5}, 10, "Массив из одного элемента"); // []
        runTwoSumTest(sol, new int[]{2, 5, 5, 11}, 10, "Дубликаты, не подходящие для суммы"); // [1, 2]
        runTwoSumTest(sol, null, 10, "Null массив"); // Error
    }

    /**
     * Вспомогательный метод для тестирования Two Sum.
     *
     * @param sol         Экземпляр решателя.
     * @param nums        Массив чисел.
     * @param target      Целевая сумма.
     * @param description Описание теста.
     */
    private static void runTwoSumTest(TwoSumProblem sol, int[] nums, int target, String description) {
        System.out.println("\n--- " + description + " ---");
        String numsString = (nums == null ? "null" : Arrays.toString(nums));
        System.out.println("Input nums: " + numsString + ", Target: " + target);
        try {
            int[] result = sol.findTwoSumIndices(nums, target);
            System.out.println("Result indices: " + Arrays.toString(result));
        } catch (IllegalArgumentException e) {
            System.out.println("Result indices: Error - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Result indices: Unexpected Error - " + e.getMessage());
        }
    }
}

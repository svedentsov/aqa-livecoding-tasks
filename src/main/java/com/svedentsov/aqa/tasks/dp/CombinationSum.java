package com.svedentsov.aqa.tasks.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Решение задачи №63: Найти все комбинации элементов списка, сумма которых равна X (Combination Sum).
 * Описание: Найти все уникальные комбинации чисел из массива `candidates`,
 * сумма которых равна `target`. Одно и то же число может быть использовано
 * неограниченное количество раз. (Проверяет: рекурсия, backtracking)
 * Задание: Напишите метод `List<List<Integer>> combinationSum(int[] candidates, int target)`,
 * который находит все уникальные комбинации чисел из массива `candidates`,
 * сумма которых равна `target`. Одно и то же число может быть использовано
 * неограниченное количество раз.
 * Пример: `candidates = [2, 3, 6, 7], target = 7`. Результат: `[[2, 2, 3], [7]]`.
 */
public class CombinationSum {

    /**
     * Находит все уникальные комбинации чисел из массива {@code candidates},
     * сумма которых равна {@code target}. Числа из {@code candidates} могут использоваться
     * неограниченное количество раз в одной комбинации.
     * Использует рекурсивный метод с возвратом (backtracking).
     *
     * @param candidates Массив уникальных положительных чисел-кандидатов.
     *                   Метод сортирует массив для оптимизации.
     * @param target     Целевая сумма (положительное целое число).
     * @return Список списков, где каждый внутренний список представляет уникальную комбинацию.
     * Если комбинаций нет, возвращает пустой список.
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        // Обработка невалидного входа
        if (candidates == null || candidates.length == 0 || target <= 0) {
            return result;
        }

        // Сортировка для оптимизации (ранний выход в backtrack)
        Arrays.sort(candidates);

        // Запуск рекурсивного поиска
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    /**
     * Точка входа для демонстрации работы метода поиска комбинаций суммы.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        CombinationSum sol = new CombinationSum();

        System.out.println("--- Finding Combination Sum ---");

        runCombinationSumTest(sol, new int[]{2, 3, 6, 7}, 7, "Example 1");
        // Expected: [[2, 2, 3], [7]]

        runCombinationSumTest(sol, new int[]{2, 3, 5}, 8, "Example 2");
        // Expected: [[2, 2, 2, 2], [2, 3, 3], [3, 5]]

        runCombinationSumTest(sol, new int[]{2}, 1, "No solution 1");
        // Expected: []

        runCombinationSumTest(sol, new int[]{1}, 3, "Only ones");
        // Expected: [[1, 1, 1]]

        runCombinationSumTest(sol, new int[]{7, 3, 2}, 7, "Unsorted candidates");
        // Expected: [[2, 2, 3], [7]] (порядок комбинаций может отличаться)

        runCombinationSumTest(sol, new int[]{8, 7, 4, 3}, 11, "Another example");
        // Expected: [[3, 4, 4], [3, 8], [4, 7]]

        runCombinationSumTest(sol, null, 5, "Null candidates");
        // Expected: []

        runCombinationSumTest(sol, new int[]{1, 2}, 0, "Zero target");
        // Expected: []
    }

    /**
     * Вспомогательный рекурсивный метод (backtracking) для поиска комбинаций.
     *
     * @param candidates         Отсортированный массив кандидатов.
     * @param remainingTarget    Оставшаяся сумма, которую нужно набрать.
     * @param startIndex         Индекс в массиве candidates, с которого можно начинать
     *                           выбирать кандидатов на текущем уровне рекурсии.
     * @param currentCombination Текущая собираемая комбинация (список чисел).
     * @param result             Общий список для сбора всех найденных валидных комбинаций.
     */
    private void backtrack(int[] candidates, int remainingTarget, int startIndex,
                           List<Integer> currentCombination, List<List<Integer>> result) {

        // Успешное завершение: нашли комбинацию с нужной суммой
        if (remainingTarget == 0) {
            result.add(new ArrayList<>(currentCombination)); // Добавляем копию
            return;
        }

        // Перебор закончился неудачно (сумма стала отрицательной)
        // Эта проверка не строго обязательна, т.к. следующая проверка в цикле ее покрывает
        if (remainingTarget < 0) {
            return;
        }

        // Пробуем добавить кандидатов, начиная с startIndex
        for (int i = startIndex; i < candidates.length; i++) {
            int currentCandidate = candidates[i];

            // Оптимизация: если текущий кандидат уже больше остатка,
            // то и все последующие (т.к. массив отсортирован) не подойдут.
            if (currentCandidate > remainingTarget) {
                break;
            }

            // Выбираем кандидата
            currentCombination.add(currentCandidate);

            // Рекурсивный вызов для следующего уровня.
            // Передаем 'i', а не 'i+1', т.к. можно использовать тот же кандидат повторно.
            backtrack(candidates, remainingTarget - currentCandidate, i, currentCombination, result);

            // Отменяем выбор (backtrack)
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    /**
     * Вспомогательный метод для тестирования combinationSum.
     *
     * @param sol         Экземпляр решателя.
     * @param candidates  Массив кандидатов.
     * @param target      Целевая сумма.
     * @param description Описание теста.
     */
    private static void runCombinationSumTest(CombinationSum sol, int[] candidates, int target, String description) {
        System.out.println("\n--- " + description + " ---");
        String candidatesStr = (candidates == null ? "null" : Arrays.toString(candidates));
        System.out.println("Input candidates: " + candidatesStr + ", Target: " + target);
        try {
            List<List<Integer>> result = sol.combinationSum(candidates, target);
            // Сортируем комбинации и элементы внутри для консистентного вывода/сравнения
            result.forEach(Collections::sort);
            result.sort((list1, list2) -> {
                int n = Math.min(list1.size(), list2.size());
                for (int i = 0; i < n; i++) {
                    int cmp = Integer.compare(list1.get(i), list2.get(i));
                    if (cmp != 0) return cmp;
                }
                return Integer.compare(list1.size(), list2.size());
            });
            System.out.println("Result Combinations: " + result);
        } catch (Exception e) {
            System.err.println("Result Combinations: Error - " + e.getMessage());
        }
    }
}

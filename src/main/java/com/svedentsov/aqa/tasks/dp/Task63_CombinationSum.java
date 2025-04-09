package com.svedentsov.aqa.tasks.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Решение задачи №63: Найти все комбинации элементов списка, сумма которых равна X (Combination Sum).
 * Позволяет использовать числа-кандидаты неограниченное количество раз.
 */
public class Task63_CombinationSum {

    /**
     * Находит все уникальные комбинации чисел из массива {@code candidates},
     * сумма которых равна {@code target}. Числа из {@code candidates} могут использоваться
     * неограниченное количество раз в одной комбинации.
     * Использует рекурсивный метод с возвратом (backtracking).
     *
     * @param candidates Массив уникальных положительных чисел-кандидатов.
     *                   (Предполагается уникальность и положительность по условиям LeetCode,
     *                   хотя код может работать и с дубликатами, если они допустимы).
     * @param target     Целевая сумма (положительное целое число).
     * @return Список (List) списков (List<Integer>), где каждый внутренний список
     * представляет уникальную комбинацию чисел из {@code candidates},
     * сумма которых равна {@code target}. Если комбинаций нет, возвращает пустой список.
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        // Проверка базовых случаев
        if (candidates == null || candidates.length == 0 || target <= 0) {
            return result;
        }

        // Сортировка массива кандидатов важна для оптимизации:
        // она позволяет прерывать рекурсию раньше, если текущий кандидат
        // уже больше оставшейся цели. Также помогает при генерации уникальных комбинаций
        // в вариантах задачи, где кандидаты нельзя использовать повторно или есть дубликаты в candidates.
        Arrays.sort(candidates);

        // Запускаем рекурсивный поиск комбинаций
        // Начинаем с индекса 0, передаем пустой список для текущей комбинации
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
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
        // Базовый случай 1: Цель достигнута (оставшаяся сумма равна 0)
        if (remainingTarget == 0) {
            // Нашли валидную комбинацию. Добавляем ее КОПИЮ в результирующий список.
            // Важно добавлять копию, т.к. currentCombination будет изменяться дальше при backtracking.
            result.add(new ArrayList<>(currentCombination));
            return; // Завершаем эту ветку рекурсии
        }

        // Базовый случай 2 (можно убрать, т.к. проверяется в цикле):
        // Оставшаяся сумма стала отрицательной - перебор.
        // if (remainingTarget < 0) {
        //     return;
        // }

        // Рекурсивно пробуем добавить кандидатов, начиная с startIndex
        for (int i = startIndex; i < candidates.length; i++) {
            int currentCandidate = candidates[i];

            // Оптимизация (благодаря сортировке):
            // Если текущий кандидат больше оставшейся цели, то и все последующие
            // кандидаты (т.к. массив отсортирован) будут больше. Нет смысла их пробовать.
            if (currentCandidate > remainingTarget) {
                break; // Прерываем цикл для текущего уровня рекурсии
            }

            // --- Шаг выбора ---
            currentCombination.add(currentCandidate); // Добавляем кандидата в текущую комбинацию

            // --- Рекурсивный вызов ---
            // Вызываем backtrack для оставшейся цели (remainingTarget - currentCandidate).
            // ВАЖНО: передаем тот же самый startIndex 'i'. Это позволяет использовать
            // ОДНО И ТО ЖЕ ЧИСЛО (candidates[i]) несколько раз в комбинации.
            // Если бы нужно было использовать каждое число только один раз, передавали бы 'i + 1'.
            backtrack(candidates, remainingTarget - currentCandidate, i, currentCombination, result);

            // --- Шаг отмены выбора (Backtrack) ---
            // Удаляем последнего добавленного кандидата из текущей комбинации,
            // чтобы можно было попробовать другие варианты на этом уровне рекурсии.
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    /**
     * Точка входа для демонстрации работы метода поиска комбинаций суммы.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task63_CombinationSum sol = new Task63_CombinationSum();

        int[][] candidatesList = {
                {2, 3, 6, 7},
                {2, 3, 5},
                {2},
                {1},
                {7, 3, 2} // Неотсортированный для проверки сортировки
        };
        int[] targets = {7, 8, 1, 3, 7};
        List<List<List<Integer>>> expectedResults = List.of(
                List.of(List.of(2, 2, 3), List.of(7)),
                List.of(List.of(2, 2, 2, 2), List.of(2, 3, 3), List.of(3, 5)),
                List.of(),
                List.of(List.of(1, 1, 1)),
                List.of(List.of(2, 2, 3), List.of(7)) // Ожидаем тот же результат, что и для {2, 3, 6, 7}
        );

        for (int i = 0; i < candidatesList.length; i++) {
            int[] candidates = candidatesList[i];
            int target = targets[i];
            System.out.println("\nCandidates: " + Arrays.toString(candidates) + ", Target: " + target);
            List<List<Integer>> result = sol.combinationSum(candidates, target);
            System.out.println("Combinations: " + result);

            // Сравнение результатов (с учетом возможного разного порядка комбинаций и элементов внутри)
            // Для простоты выведем ожидаемый результат. В тестах нужно более сложное сравнение.
            System.out.println("Expected:     " + expectedResults.get(i));
        }
    }
}

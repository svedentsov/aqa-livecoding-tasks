package com.svedentsov.aqa.tasks.dp;

import java.util.ArrayList;
import java.util.Arrays;
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
     *                   Метод сортирует массив для оптимизации и предсказуемости порядка комбинаций.
     * @param target     Целевая сумма (положительное целое число).
     * @return Список списков, где каждый внутренний список представляет уникальную комбинацию.
     * Если комбинаций нет или входные данные невалидны, возвращает пустой список.
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        // Обработка невалидного входа
        if (candidates == null || candidates.length == 0 || target <= 0) {
            return result;
        }

        // Сортировка для оптимизации (ранний выход в backtrack) и для того,
        // чтобы комбинации генерировались в лексикографическом порядке (если это важно)
        Arrays.sort(candidates);

        // Запуск рекурсивного поиска
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

        // Успешное завершение: нашли комбинацию с нужной суммой
        if (remainingTarget == 0) {
            result.add(new ArrayList<>(currentCombination)); // Добавляем копию
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
}

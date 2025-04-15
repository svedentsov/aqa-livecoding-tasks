package com.svedentsov.aqa.tasks.dp;

import java.util.Arrays;

/**
 * Решение задачи №65: Задача о рюкзаке 0/1 (Knapsack Problem 0/1).
 * <p>
 * Описание: Даны веса и стоимости `n` предметов и вместимость рюкзака `W`.
 * Найдите максимальную суммарную стоимость предметов, которые можно поместить
 * в рюкзак, не превысив его вместимость. Каждый предмет можно взять только один раз.
 * (Чаще просят обсудить подход - динамическое программирование).
 * (Проверяет: динамическое программирование/рекурсия)
 * <p>
 * Задание: Реализуйте метод `int knapsack01(int[] weights, int[] values, int capacity)`,
 * который решает задачу о рюкзаке 0/1.
 * <p>
 * Пример: Веса: `[10, 20, 30]`, Стоимости: `[60, 100, 120]`, Вместимость: `50`.
 * Решение: взять предметы 2 и 3 (вес 20+30=50), стоимость 100+120=220.
 */
public class Knapsack01 {

    /**
     * Решает задачу о рюкзаке 0/1 с использованием динамического программирования (табличный метод).
     * Находит максимальную суммарную стоимость предметов, которые можно поместить в рюкзак
     * с заданной максимальной вместимостью {@code capacity}. Каждый предмет можно взять
     * не более одного раза.
     * <p>
     * Сложность: O(n * capacity) по времени и O(n * capacity) по памяти.
     *
     * @param weights  Массив весов предметов. weights[i] - вес i-го предмета.
     * @param values   Массив стоимостей предметов. values[i] - стоимость i-го предмета.
     * @param capacity Максимальная вместимость рюкзака.
     * @return Максимальная суммарная стоимость предметов.
     * @throws IllegalArgumentException если входные данные некорректны.
     */
    public int knapsack01DPTable(int[] weights, int[] values, int capacity) {
        validateInput(weights, values, capacity);
        int n = weights.length;

        // dp[i][w] = макс. стоимость с первыми i предметами при вместимости w
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) { // Идем по предметам (1-based index)
            int currentWeight = weights[i - 1];
            int currentValue = values[i - 1];
            for (int w = 0; w <= capacity; w++) { // Идем по вместимости
                // Если текущий предмет не помещается
                if (currentWeight > w) {
                    dp[i][w] = dp[i - 1][w]; // Берем решение без него
                } else {
                    // Выбираем: либо не брать текущий предмет (dp[i-1][w]),
                    // либо взять (currentValue + решение для оставшегося веса без этого предмета dp[i-1][w-currentWeight])
                    dp[i][w] = Math.max(dp[i - 1][w], currentValue + dp[i - 1][w - currentWeight]);
                }
            }
        }
        return dp[n][capacity];
    }

    /**
     * Решает задачу о рюкзаке 0/1 с использованием оптимизации памяти (1D массив DP).
     * <p>
     * Сложность: O(n * capacity) по времени и O(capacity) по памяти.
     *
     * @param weights  Массив весов.
     * @param values   Массив стоимостей.
     * @param capacity Вместимость рюкзака.
     * @return Максимальная стоимость.
     * @throws IllegalArgumentException если входные данные некорректны.
     */
    public int knapsack01DPOptimized(int[] weights, int[] values, int capacity) {
        validateInput(weights, values, capacity);
        int n = weights.length;

        // dp[w] = макс. стоимость для вместимости w
        int[] dp = new int[capacity + 1];

        // Идем по предметам
        for (int i = 0; i < n; i++) {
            int currentWeight = weights[i];
            int currentValue = values[i];
            // Идем по вместимости СПРАВА НАЛЕВО!
            for (int w = capacity; w >= currentWeight; w--) {
                dp[w] = Math.max(dp[w], currentValue + dp[w - currentWeight]);
            }
        }
        return dp[capacity];
    }

    /**
     * Точка входа для демонстрации работы методов решения задачи о рюкзаке.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Knapsack01 sol = new Knapsack01();

        System.out.println("--- Knapsack 0/1 Problem ---");

        runKnapsackTest(sol,
                new int[]{10, 20, 30}, new int[]{60, 100, 120}, 50,
                "Example 1 (W=50)"
        ); // Expected: 220 (items 2+3)

        runKnapsackTest(sol,
                new int[]{1, 2, 3, 4, 5}, new int[]{10, 20, 30, 40, 50}, 7,
                "Example 2 (W=7)"
        ); // Expected: 70 (items 3+4 or 2+5)

        runKnapsackTest(sol,
                new int[]{5, 4, 6, 3}, new int[]{10, 40, 30, 50}, 10,
                "Example 3 (W=10)"
        ); // Expected: 90 (items 2+4)

        runKnapsackTest(sol,
                new int[]{10, 20, 30}, new int[]{60, 100, 120}, 0,
                "Capacity 0"
        ); // Expected: 0

        runKnapsackTest(sol,
                new int[]{10}, new int[]{60}, 5,
                "Item heavier than capacity"
        ); // Expected: 0

        runKnapsackTest(sol,
                new int[]{}, new int[]{}, 10,
                "Empty items"
        ); // Expected: 0

        // Тесты на исключения
        runKnapsackTest(sol, null, new int[]{1}, 10, "Null weights"); // Exception
        runKnapsackTest(sol, new int[]{1}, null, 10, "Null values"); // Exception
        runKnapsackTest(sol, new int[]{1, 2}, new int[]{1}, 10, "Different lengths"); // Exception
        runKnapsackTest(sol, new int[]{1}, new int[]{1}, -1, "Negative capacity"); // Exception
    }

    /**
     * Валидация входных данных для задачи о рюкзаке.
     */
    private void validateInput(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null) {
            throw new IllegalArgumentException("Input arrays (weights, values) cannot be null.");
        }
        if (weights.length != values.length) {
            throw new IllegalArgumentException("Weights and values arrays must have the same length.");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        // Опциональная проверка на отрицательные веса/стоимости
        // for (int w : weights) if (w < 0) throw new IllegalArgumentException("Weights must be non-negative.");
        // for (int v : values) if (v < 0) throw new IllegalArgumentException("Values must be non-negative.");
    }

    /**
     * Вспомогательный метод для тестирования методов Knapsack.
     *
     * @param sol         Экземпляр решателя.
     * @param weights     Массив весов.
     * @param values      Массив стоимостей.
     * @param capacity    Вместимость рюкзака.
     * @param description Описание теста.
     */
    private static void runKnapsackTest(Knapsack01 sol, int[] weights, int[] values, int capacity, String description) {
        System.out.println("\n--- " + description + " ---");
        String wStr = (weights == null ? "null" : Arrays.toString(weights));
        String vStr = (values == null ? "null" : Arrays.toString(values));
        System.out.println("Input Weights: " + wStr);
        System.out.println("Input Values : " + vStr);
        System.out.println("Input Capacity: " + capacity);
        try {
            int resultTable = sol.knapsack01DPTable(weights, values, capacity);
            System.out.println("  Max Value (DP Table)   : " + resultTable);
        } catch (IllegalArgumentException e) {
            System.out.println("  Max Value (DP Table)   : Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("  Max Value (DP Table)   : Unexpected Error - " + e.getMessage());
        }
        try {
            int resultOptimized = sol.knapsack01DPOptimized(weights, values, capacity);
            System.out.println("  Max Value (DP Optimized): " + resultOptimized);
        } catch (IllegalArgumentException e) {
            System.out.println("  Max Value (DP Optimized): Error - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("  Max Value (DP Optimized): Unexpected Error - " + e.getMessage());
        }
    }
}

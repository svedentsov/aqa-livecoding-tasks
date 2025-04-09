package com.svedentsov.aqa.tasks.dp;

import java.util.Arrays;

/**
 * Решение задачи №65: Задача о рюкзаке 0/1 (Knapsack Problem 0/1).
 */
public class Task65_Knapsack01 {

    /**
     * Решает задачу о рюкзаке 0/1 с использованием динамического программирования (табличный метод).
     * Находит максимальную суммарную стоимость предметов, которые можно поместить в рюкзак
     * с заданной максимальной вместимостью {@code capacity}. Каждый предмет можно взять
     * не более одного раза (либо 0, либо 1 раз).
     * Сложность O(n * capacity) по времени и O(n * capacity) по памяти,
     * где n - количество предметов.
     *
     * @param weights  Массив весов предметов. weights[i] - вес i-го предмета (индекс 0..n-1).
     * @param values   Массив стоимостей предметов. values[i] - стоимость i-го предмета.
     * @param capacity Максимальная вместимость рюкзака.
     * @return Максимальная суммарная стоимость предметов, которые можно поместить в рюкзак.
     * @throws IllegalArgumentException если входные массивы некорректны (null, разная длина,
     *                                  отрицательные веса/стоимости, если они не допускаются) или capacity < 0.
     */
    public int knapsack01DPTable(int[] weights, int[] values, int capacity) {
        // Проверка входных данных
        if (weights == null || values == null || weights.length != values.length || capacity < 0) {
            throw new IllegalArgumentException("Invalid input: weights, values arrays (null or different lengths?), or negative capacity.");
        }
        // Доп. проверки (опционально): веса и стоимости должны быть неотрицательными?
        // for (int w : weights) if (w < 0) throw new IllegalArgumentException("Weights must be non-negative.");
        // for (int v : values) if (v < 0) throw new IllegalArgumentException("Values must be non-negative.");

        int n = weights.length; // Количество предметов

        // Таблица DP: dp[i][w] - максимальная стоимость, достижимая с использованием
        //             первых i предметов при вместимости рюкзака w.
        // Размер (n + 1) x (capacity + 1) для удобства индексации с 1 и обработки 0 предметов/0 вместимости.
        int[][] dp = new int[n + 1][capacity + 1];

        // Заполнение таблицы dp
        // i - индекс предмета + 1 (от 0 до n)
        // w - текущая вместимость (от 0 до capacity)
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // Базовый случай: если нет предметов (i=0) или вместимость 0 (w=0), стоимость 0.
                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                } else {
                    // Вес и стоимость текущего i-го предмета (индекс в массивах i-1)
                    int currentWeight = weights[i - 1];
                    int currentValue = values[i - 1];

                    // Если вес текущего предмета БОЛЬШЕ текущей вместимости рюкзака w
                    if (currentWeight > w) {
                        // Мы не можем взять этот предмет. Максимальная стоимость равна
                        // стоимости, достижимой без него (с первыми i-1 предметами).
                        dp[i][w] = dp[i - 1][w];
                    } else {
                        // Если текущий предмет ПОМЕЩАЕТСЯ в рюкзак вместимости w.
                        // У нас есть два варианта:
                        // 1. НЕ БРАТЬ текущий предмет:
                        //    Макс. стоимость = стоимость без него = dp[i-1][w].
                        int valueWithoutCurrent = dp[i - 1][w];

                        // 2. БРАТЬ текущий предмет:
                        //    Макс. стоимость = его стоимость (currentValue) + макс. стоимость,
                        //    которую можно было получить с предыдущими (i-1) предметами
                        //    при оставшейся вместимости (w - currentWeight).
                        int valueWithCurrent = currentValue + dp[i - 1][w - currentWeight];

                        // Выбираем вариант, дающий максимальную стоимость.
                        dp[i][w] = Math.max(valueWithoutCurrent, valueWithCurrent);
                    }
                }
            }
        }

        // Результат находится в правом нижнем углу таблицы:
        // максимальная стоимость для всех n предметов при полной вместимости capacity.
        return dp[n][capacity];
    }

    /**
     * Решает задачу о рюкзаке 0/1 с использованием оптимизации памяти (1D массив DP).
     * Вместо таблицы n x capacity используется массив размера capacity.
     * Сложность O(n * capacity) по времени и O(capacity) по памяти.
     *
     * @param weights  Массив весов.
     * @param values   Массив стоимостей.
     * @param capacity Вместимость рюкзака.
     * @return Максимальная стоимость.
     */
    public int knapsack01DPOptimized(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length || capacity < 0) {
            throw new IllegalArgumentException("Invalid input.");
        }
        int n = weights.length;
        // dp[w] - максимальная стоимость для рюкзака вместимости w.
        // Этот массив будет переиспользоваться для каждого предмета.
        int[] dp = new int[capacity + 1];
        // Инициализация нулями (базовый случай: 0 предметов -> стоимость 0).

        // Идем по предметам (от 0 до n-1)
        for (int i = 0; i < n; i++) {
            int currentWeight = weights[i];
            int currentValue = values[i];

            // Идем по вместимости СПРАВА НАЛЕВО (от capacity до currentWeight).
            // Это КЛЮЧЕВОЙ момент для 0/1 рюкзака с 1D DP.
            // Обход справа налево гарантирует, что при вычислении dp[w] мы используем
            // значение dp[w - currentWeight] из ПРЕДЫДУЩЕЙ итерации по предметам (i-1),
            // а не уже обновленное значение для ТЕКУЩЕГО предмета i.
            // Это предотвращает многократное взятие одного и того же предмета.
            for (int w = capacity; w >= currentWeight; w--) {
                // Для текущей вместимости w, выбираем максимум из:
                // 1. dp[w] (не брать текущий предмет i)
                // 2. currentValue + dp[w - currentWeight] (взять текущий предмет i)
                dp[w] = Math.max(dp[w], currentValue + dp[w - currentWeight]);
            }
            // Для вместимостей w < currentWeight значение dp[w] не меняется на этой итерации.
        }
        // Результат для полной вместимости рюкзака хранится в dp[capacity].
        return dp[capacity];
    }

    /**
     * Точка входа для демонстрации работы методов решения задачи о рюкзаке.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task65_Knapsack01 sol = new Task65_Knapsack01();

        int[] weights1 = {10, 20, 30};
        int[] values1 = {60, 100, 120};
        int capacity1 = 50;
        System.out.println("Weights: " + Arrays.toString(weights1));
        System.out.println("Values: " + Arrays.toString(values1));
        System.out.println("Capacity: " + capacity1);
        System.out.println("Max value (DP Table): " + sol.knapsack01DPTable(weights1, values1, capacity1)); // 220
        System.out.println("Max value (Optimized): " + sol.knapsack01DPOptimized(weights1, values1, capacity1)); // 220

        int[] weights2 = {1, 2, 3, 4, 5};
        int[] values2 = {10, 20, 30, 40, 50};
        int capacity2 = 7;
        System.out.println("\nWeights: " + Arrays.toString(weights2));
        System.out.println("Values: " + Arrays.toString(values2));
        System.out.println("Capacity: " + capacity2);
        System.out.println("Max value (DP Table): " + sol.knapsack01DPTable(weights2, values2, capacity2)); // 70
        System.out.println("Max value (Optimized): " + sol.knapsack01DPOptimized(weights2, values2, capacity2)); // 70

        int[] weights3 = {5, 4, 6, 3};
        int[] values3 = {10, 40, 30, 50};
        int capacity3 = 10;
        System.out.println("\nWeights: " + Arrays.toString(weights3));
        System.out.println("Values: " + Arrays.toString(values3));
        System.out.println("Capacity: " + capacity3);
        System.out.println("Max value (DP Table): " + sol.knapsack01DPTable(weights3, values3, capacity3)); // 90
        System.out.println("Max value (Optimized): " + sol.knapsack01DPOptimized(weights3, values3, capacity3)); // 90

        // Тест с нулевой вместимостью
        System.out.println("\nCapacity 0:");
        System.out.println("Max value (Optimized): " + sol.knapsack01DPOptimized(weights1, values1, 0)); // 0

        // Тест с пустыми предметами
        System.out.println("\nEmpty items:");
        System.out.println("Max value (Optimized): " + sol.knapsack01DPOptimized(new int[0], new int[0], 50)); // 0
    }
}

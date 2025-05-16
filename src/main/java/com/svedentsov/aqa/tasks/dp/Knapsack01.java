package com.svedentsov.aqa.tasks.dp;

/**
 * Решение задачи №65: Задача о рюкзаке 0/1 (Knapsack Problem 0/1).
 * Описание: Даны веса и стоимости `n` предметов и вместимость рюкзака `W`.
 * Найдите максимальную суммарную стоимость предметов, которые можно поместить
 * в рюкзак, не превысив его вместимость. Каждый предмет можно взять только один раз.
 * (Чаще просят обсудить подход - динамическое программирование).
 * (Проверяет: динамическое программирование/рекурсия)
 * Задание: Реализуйте метод `int knapsack01(int[] weights, int[] values, int capacity)`,
 * который решает задачу о рюкзаке 0/1.
 * Пример: Веса: `[10, 20, 30]`, Стоимости: `[60, 100, 120]`, Вместимость: `50`.
 * Решение: взять предметы 2 и 3 (вес 20+30=50), стоимость 100+120=220.
 */
public class Knapsack01 {

    /**
     * Решает задачу о рюкзаке 0/1 с использованием динамического программирования (табличный метод, 2D DP).
     * Сложность: O(n * capacity) по времени и O(n * capacity) по памяти.
     *
     * @param weights  Массив весов предметов.
     * @param values   Массив стоимостей предметов.
     * @param capacity Максимальная вместимость рюкзака.
     * @return Максимальная суммарная стоимость предметов.
     * @throws IllegalArgumentException если входные данные некорректны.
     */
    public int knapsack01DPTable(int[] weights, int[] values, int capacity) {
        validateInput(weights, values, capacity);
        int n = weights.length;

        if (n == 0 || capacity == 0) { // Если нет предметов или вместимость 0
            return 0;
        }

        // dp[i][w] = макс. стоимость с первыми i предметами при вместимости w
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) { // Идем по предметам (1-based index для dp)
            int currentWeight = weights[i - 1]; // 0-based index для исходных массивов
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

        if (n == 0 || capacity == 0) {
            return 0;
        }

        // dp[w] = макс. стоимость для вместимости w
        int[] dp = new int[capacity + 1];

        // Идем по предметам
        for (int i = 0; i < n; i++) {
            int currentWeight = weights[i];
            int currentValue = values[i];
            // Идем по вместимости СПРАВА НАЛЕВО!
            // Это важно, чтобы для текущего предмета i использовать значения dp
            // рассчитанные на предыдущей итерации (для предмета i-1).
            for (int w = capacity; w >= currentWeight; w--) {
                dp[w] = Math.max(dp[w], currentValue + dp[w - currentWeight]);
            }
        }
        return dp[capacity];
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
        // Опциональная проверка на отрицательные веса/стоимости,
        // если по условию задачи они должны быть положительными.
        // Алгоритм ДП обычно предполагает неотрицательные веса.
        for (int w : weights) {
            if (w < 0) throw new IllegalArgumentException("Weights must be non-negative.");
        }
        for (int v : values) {
            if (v < 0) throw new IllegalArgumentException("Values must be non-negative.");
        }
    }
}

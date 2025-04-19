package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №91: Подсчет дождевой воды (Trapping Rain Water).
 * <p>
 * Описание: Найти количество воды между "столбиками" гистограммы.
 * (Проверяет: алгоритмы, два указателя/динамика)
 * <p>
 * Задание: Дан массив неотрицательных чисел `int[] height`, представляющий
 * высоты столбцов гистограммы (ширина каждого столбика 1). Напишите метод
 * `int trapRainWater(int[] height)`, который вычисляет, сколько единиц дождевой
 * воды может быть удержано между столбцами после дождя.
 * <p>
 * Пример: `trapRainWater(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})` -> `6`.
 */
public class TrappingRainWater {

    /**
     * Вычисляет количество удержанной дождевой воды, используя метод двух указателей.
     * <p>
     * Сложность: O(n) по времени, O(1) по дополнительной памяти.
     * <p>
     * Алгоритм:
     * 1. Инициализировать `left = 0`, `right = n-1`, `leftMax = 0`, `rightMax = 0`, `water = 0`.
     * 2. `while (left < right)`:
     * a. Если `height[left] < height[right]`:
     * i. Если `height[left] >= leftMax`, обновить `leftMax = height[left]`.
     * ii. Иначе, `water += leftMax - height[left]`.
     * iii. `left++`.
     * b. Иначе (`height[right] <= height[left]`):
     * i. Если `height[right] >= rightMax`, обновить `rightMax = height[right]`.
     * ii. Иначе, `water += rightMax - height[right]`.
     * iii. `right--`.
     * 3. Вернуть `water`.
     *
     * @param height Массив неотрицательных чисел, представляющих высоты столбцов.
     * @return Общее количество единиц воды, удерживаемой гистограммой.
     */
    public int trapRainWaterTwoPointers(int[] height) {
        if (height == null || height.length < 3) {
            return 0; // Невозможно удержать воду
        }

        int n = height.length;
        int left = 0;
        int right = n - 1;
        int leftMax = 0;  // Макс. высота слева от 'left' (включая)
        int rightMax = 0; // Макс. высота справа от 'right' (включая)
        int totalWater = 0;

        while (left < right) {
            // Определяем, какая граница ниже - она будет ограничивать уровень воды
            if (height[left] < height[right]) {
                // Работаем с левым указателем
                // Если текущая высота больше или равна макс. левой - вода не удержится здесь, обновляем макс.
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    // Текущая высота ниже макс. левой - вода удерживается
                    totalWater += leftMax - height[left];
                }
                left++; // Сдвигаем левый указатель
            } else {
                // Работаем с правым указателем
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    totalWater += rightMax - height[right];
                }
                right--; // Сдвигаем правый указатель
            }
        }
        return totalWater;
    }

    /**
     * Вычисляет количество дождевой воды с использованием двух доп. массивов (DP подход).
     * <p>
     * Сложность: O(n) по времени, O(n) по дополнительной памяти.
     * <p>
     * Алгоритм:
     * 1. Создать массив `maxLeft[n]`, хранящий макс. высоту слева до индекса `i` включительно.
     * 2. Создать массив `maxRight[n]`, хранящий макс. высоту справа от индекса `i` включительно.
     * 3. Заполнить `maxLeft`: `maxLeft[i] = max(maxLeft[i-1], height[i])`.
     * 4. Заполнить `maxRight` (справа налево): `maxRight[i] = max(maxRight[i+1], height[i])`.
     * 5. Итерировать по массиву `height` от 0 до `n-1`:
     * a. Уровень воды над столбиком `i`: `waterLevel = min(maxLeft[i], maxRight[i])`.
     * b. Вода над столбиком `i`: `trapped = waterLevel - height[i]`.
     * c. Если `trapped > 0`, добавить `trapped` к `totalWater`.
     * 6. Вернуть `totalWater`.
     *
     * @param height Массив высот столбцов.
     * @return Общее количество удержанной воды.
     */
    public int trapRainWaterExtraSpace(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        int n = height.length;

        // 1 & 3: Вычисляем maxLeft
        int[] maxLeft = new int[n];
        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        }

        // 2 & 4: Вычисляем maxRight
        int[] maxRight = new int[n];
        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }

        // 5: Вычисляем удержанную воду
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            // Уровень воды над столбиком i ограничен МЕНЬШЕЙ из границ maxLeft[i] и maxRight[i]
            int waterLevel = Math.min(maxLeft[i], maxRight[i]);
            // Количество воды над столбиком = уровень воды - высота столбика (если > 0)
            if (waterLevel > height[i]) {
                totalWater += waterLevel - height[i];
            }
        }
        return totalWater;
    }

    /**
     * Точка входа для демонстрации работы методов подсчета дождевой воды.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        TrappingRainWater sol = new TrappingRainWater();
        int[][] testCases = {
                {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, // 6
                {4, 2, 0, 3, 2, 5},                   // 9
                {4, 2, 3},                            // 1
                {1, 0, 1},                            // 1
                {3, 0, 0, 2, 0, 4},                   // 10
                {1, 2, 3, 4, 5},                      // 0 (возрастающая)
                {5, 4, 3, 2, 1},                      // 0 (убывающая)
                {2, 0, 2},                            // 2 (простой случай)
                {},                                   // 0 (пустой)
                {5},                                  // 0 (1 элемент)
                {5, 5},                               // 0 (2 элемента)
                {5, 5, 1, 7, 1, 1, 5, 2, 7, 6}        // 23
        };

        System.out.println("--- Trapping Rain Water ---");
        for (int[] height : testCases) {
            runTrapWaterTest(sol, height);
        }

        // Тест с null
        runTrapWaterTest(sol, null);
    }

    /**
     * Вспомогательный метод для тестирования методов trapRainWater.
     *
     * @param sol    Экземпляр решателя.
     * @param height Массив высот.
     */
    private static void runTrapWaterTest(TrappingRainWater sol, int[] height) {
        String input = (height == null ? "null" : Arrays.toString(height));
        System.out.println("\nInput Height: " + input);
        try {
            int resultTP = sol.trapRainWaterTwoPointers(height);
            System.out.println("  Result (Two Pointers): " + resultTP);
        } catch (Exception e) {
            System.err.println("  Result (Two Pointers): Error - " + e.getMessage());
        }
        try {
            int resultES = sol.trapRainWaterExtraSpace(height);
            System.out.println("  Result (Extra Space) : " + resultES);
        } catch (Exception e) {
            System.err.println("  Result (Extra Space) : Error - " + e.getMessage());
        }
    }
}

package com.svedentsov.aqa.tasks.arrays_lists; // или algorithms

import java.util.Arrays;

/**
 * Решение задачи №91: Подсчет дождевой воды (Trapping Rain Water).
 */
public class Task91_TrappingRainWater {

    /**
     * Вычисляет количество дождевой воды, которое может быть удержано гистограммой,
     * представленной массивом высот {@code height}.
     * Использует метод двух указателей (left, right), двигая их навстречу друг другу.
     * На каждом шаге определяется минимальная из максимальных высот слева (leftMax)
     * и справа (rightMax), и вычисляется вода над текущим столбиком.
     * Сложность O(n) по времени, O(1) по дополнительной памяти.
     *
     * @param height Массив неотрицательных чисел, представляющих высоты столбцов.
     * @return Общее количество единиц воды, удерживаемой гистограммой.
     * Возвращает 0, если массив null или содержит менее 3 элементов.
     */
    public int trapRainWaterTwoPointers(int[] height) {
        // Воду можно удержать только если есть хотя бы 3 столбика.
        if (height == null || height.length < 3) {
            return 0;
        }

        int n = height.length;
        int left = 0;          // Левый указатель, начинает с 0
        int right = n - 1;     // Правый указатель, начинает с конца
        int leftMax = 0;       // Максимальная высота, встреченная слева от `left` (включая `left`)
        int rightMax = 0;      // Максимальная высота, встреченная справа от `right` (включая `right`)
        int totalWater = 0;    // Общее количество удержанной воды

        // Двигаем указатели навстречу друг другу, пока они не встретятся
        while (left < right) {
            // Определяем, какой из указателей двигать:
            // Двигаем тот указатель, чья текущая высота столбика МЕНЬШЕ.
            // Это гарантирует, что уровень воды будет определяться МЕНЬШЕЙ из границ (leftMax или rightMax),
            // так как мы знаем, что с другой стороны точно есть граница не ниже текущей `height[left]` или `height[right]`.
            if (height[left] < height[right]) {
                // Обновляем максимальную высоту слева
                // Если текущая высота больше или равна `leftMax`, то над этим столбиком
                // вода удержаться не может, просто обновляем `leftMax`.
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    // Если текущая высота меньше `leftMax`, то этот столбик удерживает воду.
                    // Количество воды = `leftMax` (уровень воды) - `height[left]` (высота столбика).
                    totalWater += leftMax - height[left];
                }
                // Сдвигаем левый указатель вправо
                left++;
            } else { // height[right] <= height[left]
                // Обновляем максимальную высоту справа
                // Если текущая высота больше или равна `rightMax`, обновляем `rightMax`.
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    // Если текущая высота меньше `rightMax`, удерживается вода.
                    // Количество воды = `rightMax` - `height[right]`.
                    totalWater += rightMax - height[right];
                }
                // Сдвигаем правый указатель влево
                right--;
            }
        }
        return totalWater;
    }

    /**
     * Вычисляет количество дождевой воды с использованием двух дополнительных массивов
     * для хранения максимальных высот слева и справа от каждого столбика.
     * Сложность O(n) по времени и O(n) по дополнительной памяти.
     *
     * @param height Массив высот столбцов.
     * @return Общее количество удержанной воды.
     */
    public int trapRainWaterExtraSpace(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        int n = height.length;

        // 1. Вычисляем максимальную высоту слева для каждого столбика
        int[] maxLeft = new int[n];
        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        }

        // 2. Вычисляем максимальную высоту справа для каждого столбика
        int[] maxRight = new int[n];
        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }

        // 3. Вычисляем удержанную воду над каждым столбиком
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
        Task91_TrappingRainWater sol = new Task91_TrappingRainWater();
        int[][] testCases = {
                {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, // 6
                {4, 2, 0, 3, 2, 5},                   // 9
                {4, 2, 3},                            // 1
                {1, 0, 1},                            // 1
                {3, 0, 0, 2, 0, 4},                   // 10
                {1, 2, 3, 4, 5},                      // 0
                {5, 4, 3, 2, 1},                      // 0
                {2, 0, 2},                            // 2
                {},                                   // 0
                {5},                                  // 0
                {5, 5, 1, 7, 1, 1, 5, 2, 7, 6}        // 23
        };
        int[] expectedResults = {6, 9, 1, 1, 10, 0, 0, 2, 0, 0, 23};

        System.out.println("--- Two Pointers Method (O(1) space) ---");
        for (int i = 0; i < testCases.length; i++) {
            int result = sol.trapRainWaterTwoPointers(testCases[i]);
            System.out.println(Arrays.toString(testCases[i]) + " -> " + result + " (Expected: " + expectedResults[i] + ")");
            if (result != expectedResults[i]) System.err.println("   Mismatch!");
        }

        System.out.println("\n--- Extra Space Method (O(n) space) ---");
        for (int i = 0; i < testCases.length; i++) {
            int result = sol.trapRainWaterExtraSpace(testCases[i]);
            System.out.println(Arrays.toString(testCases[i]) + " -> " + result + " (Expected: " + expectedResults[i] + ")");
            if (result != expectedResults[i]) System.err.println("   Mismatch!");
        }
    }
}

package com.svedentsov.aqa.tasks.arrays_lists;

/**
 * Решение задачи №91: Подсчет дождевой воды (Trapping Rain Water).
 * Описание: Найти количество воды между "столбиками" гистограммы.
 * (Проверяет: алгоритмы, два указателя/динамика)
 * Задание: Дан массив неотрицательных чисел `int[] height`, представляющий
 * высоты столбцов гистограммы (ширина каждого столбика 1). Напишите метод
 * `int trapRainWater(int[] height)`, который вычисляет, сколько единиц дождевой
 * воды может быть удержано между столбцами после дождя.
 * Пример: `trapRainWater(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})` -> `6`.
 */
public class TrappingRainWater {

    /**
     * Вычисляет количество удержанной дождевой воды, используя метод двух указателей.
     * Сложность: O(n) по времени, O(1) по дополнительной памяти.
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
     * Сложность: O(n) по времени, O(n) по дополнительной памяти.
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
}

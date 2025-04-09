package com.svedentsov.aqa.tasks.arrays_lists;

import java.util.Arrays;

/**
 * Решение задачи №45: Переместить все нули в конец массива.
 */
public class Task45_MoveZeroesEnd {

    /**
     * Перемещает все нули в конец массива {@code nums}, сохраняя относительный
     * порядок ненулевых элементов. Модификация происходит "на месте" (in-place).
     * Использует подход с одним указателем (`writePointer` или `nonZeroIndex`),
     * который отслеживает позицию для следующего ненулевого элемента.
     * Сложность O(n) по времени (один проход по массиву), O(1) по памяти.
     *
     * @param nums Массив для модификации. Может быть null.
     */
    public void moveZeroes(int[] nums) {
        // Если массив null или содержит 0/1 элемент, перемещение не требуется
        if (nums == null || nums.length <= 1) {
            return;
        }

        int n = nums.length;
        // Указатель `nonZeroIndex` отслеживает позицию,
        // куда нужно записать следующий найденный ненулевой элемент.
        int nonZeroIndex = 0;

        // Первый проход: Перемещаем все ненулевые элементы в начало массива,
        // сохраняя их относительный порядок.
        for (int i = 0; i < n; i++) {
            // Если текущий элемент nums[i] не ноль
            if (nums[i] != 0) {
                // Помещаем его на позицию nonZeroIndex
                // Если i == nonZeroIndex (т.е. нулей еще не встречали или они идут подряд в конце),
                // то элемент просто записывается сам на себя.
                // Если i > nonZeroIndex, значит, мы пропустили нули, и нужно
                // скопировать ненулевой элемент на место nonZeroIndex.
                // (Оптимизация с обменом `swap(nums, i, nonZeroIndex)` тоже возможна, но
                // может делать лишние записи нулей).
                nums[nonZeroIndex] = nums[i];
                // Сдвигаем указатель для следующего ненулевого элемента
                nonZeroIndex++;
            }
            // Если nums[i] == 0, мы просто пропускаем его, nonZeroIndex не двигается,
            // ожидая следующего ненулевого элемента, который запишется поверх этого нуля
            // (или следующего нуля).
        }

        // Второй проход: Заполняем оставшуюся часть массива (с индекса nonZeroIndex до конца) нулями.
        // Все ненулевые элементы уже находятся в начале массива до индекса nonZeroIndex.
        for (int i = nonZeroIndex; i < n; i++) {
            nums[i] = 0;
        }
    }

    /**
     * Точка входа для демонстрации работы метода перемещения нулей.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Task45_MoveZeroesEnd sol = new Task45_MoveZeroesEnd();

        int[][] testArrays = {
                {0, 1, 0, 3, 12},    // [1, 3, 12, 0, 0]
                {0, 0, 1},          // [1, 0, 0]
                {1, 2, 3},          // [1, 2, 3]
                {0},                // [0]
                {1, 0, 2, 0, 0, 3}, // [1, 2, 3, 0, 0, 0]
                {0, 0, 0, 0},       // [0, 0, 0, 0]
                {1, 0, 1, 0, 1},    // [1, 1, 1, 0, 0]
                {}                  // []
        };

        for (int[] nums : testArrays) {
            System.out.println("Original: " + Arrays.toString(nums));
            sol.moveZeroes(nums); // Модифицируем массив на месте
            System.out.println("Moved:    " + Arrays.toString(nums));
            System.out.println("---------");
        }

        // Тест с null
        System.out.println("Original: null");
        sol.moveZeroes(null);
        System.out.println("Moved:    null");
    }
}
